package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class OrganismeType extends Model {

    @Required
    public String libelle;
}
