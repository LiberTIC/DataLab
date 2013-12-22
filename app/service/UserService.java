package service;

import models.User;
import models.UserAccount;
import play.Logger;
import play.cache.Cache;
import play.libs.Codec;
import play.mvc.Scope.Session;
import securesocial.provider.SocialUser;
import securesocial.provider.UserId;

import java.util.List;

/**
 * UserService class for SecureSocial plugin.
 */
public class UserService implements securesocial.provider.UserServiceDelegate {

    /**
     * Find a user by its social Id.
     *
     * @param id
     * @return
     */
    public static User findUser(UserId id) {
        List<models.User> users = User.find("SELECT u FROM User u JOIN u.accounts a WHERE a.userId = ? AND a.provider = ?",
                id.id, id.provider.toString()).fetch(1);
        if (users.size() > 0) {
            models.User user = users.get(0);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public SocialUser find(UserId id) {
        User user = this.findUser(id);
        if (user != null) {
            return user.toUserSocial();
        } else {
            return null;
        }
    }

    @Override
    public void save(SocialUser user) {
        User userDb = findUser(user.id);
        if (userDb == null) {
            if (Session.current().get("member") != null) {
                Long id = Long.valueOf(Session.current().get("member"));
                userDb = User.findById(id);
                UserAccount account = new UserAccount();
                account.userId = user.id.id;
                account.provider = user.id.provider.toString();
                account.save();
                userDb.accounts.add(account);
            } else {
                userDb = User.fromUserSocial(user);
                for (UserAccount account : userDb.accounts) {
                    account.save();
                }
            }
            userDb.save();
        }
        else {
            userDb.password = user.password;
            userDb.save();
        }
    }

    @Override
    public String createActivation(SocialUser user) {
        final String uuid = Codec.UUID();
        Cache.add(uuid, user, "24h");
        return uuid;
    }

    @Override
    public boolean activate(String uuid) {
        SocialUser socialUser = (SocialUser) Cache.get(uuid);
        if (socialUser != null) {
            User user = this.findUser(socialUser.id);
            user.isEmailVerified = true;
            user.save();
            Cache.delete(uuid);
        }
        return true;
    }

    @Override
    public void deletePendingActivations() {
        List<User> users = User.find("SELECT user FROM User u WHERE u.isEmailVerified = true").fetch();
        for (User user : users) {
            user.delete();
        }
    }

    @Override
    public SocialUser find(String email) {
        Logger.debug("Trying to find social user with email : " + email);
        User user = User.find("email = ?", email).first();
        if(user != null) {
            Logger.debug("Find user " + user.displayName);
            return user.toUserSocial();
        }
        return null;
    }

    @Override
    public String createPasswordReset(SocialUser user) {
        final String uuid = Codec.UUID();
        Cache.add(uuid, user, "24h");
        return uuid;
    }

    @Override
    public SocialUser fetchForPasswordReset(String username, String uuid) {
        if (Cache.get(uuid) != null) {
            SocialUser socialUser = (SocialUser) Cache.get(uuid);
            if (socialUser.id.id.equals(username)) {
                return socialUser;
            }
        }
        return null;
    }

    @Override
    public void disableResetCode(String username, String uuid) {
        SocialUser socialUser = fetchForPasswordReset(username, uuid);
        if (socialUser != null) {
            Cache.delete(uuid);
        }
    }

}
