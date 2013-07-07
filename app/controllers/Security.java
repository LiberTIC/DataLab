package controllers;

import controllers.securesocial.SecureSocial;
import securesocial.provider.SocialUser;

public class Security extends Secure.Security {

    static boolean check(String profile) {
        return true;
    }

    static String connected() {
        SocialUser user = SecureSocial.getCurrentUser();
        return user.id.id;
    }

    static boolean isConnected() {
        SocialUser user = SecureSocial.getCurrentUser();
        if(user != null) {
            return true;
        }
        return false;
    }

}
