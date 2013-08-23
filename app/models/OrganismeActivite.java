package models;


import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "organismeactivite")
public class OrganismeActivite extends Model {

    @Required
    public String libelle;

    public String toString(){
        return libelle;
    }
}
