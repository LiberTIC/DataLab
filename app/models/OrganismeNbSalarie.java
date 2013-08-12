package models;


import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class OrganismeNbSalarie extends Model {

    @Required
    public String libelle;

    public String toString(){
        return libelle;
    }
}
