package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class UserAccount extends Model {

    /**
     * The user id of external service.
     */
    public String userId;

    /**
     * The provider this user belongs to.
     */
    public String provider;

}
