package models;


import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class OrganismeActivite extends Model {

    @Required
    public String libelle;
}