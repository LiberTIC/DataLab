package models;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;

import java.util.Date;
import java.util.List;

public class OrganismeVersion extends Model {

    @Required
    public String nom;

    @Required
    public String type;

    public Blob logo;

    public String siret;

    public String nbSalarié;

    public String telephone;

    public String email;

    public String adresse;

    @Required
    public String ville;

    public String codePostal;

    public List<ThemeActivite> themes;

    public String produit;

    public String description;

    public Date created;

    public Double wsg_x;
    public Double wsg_y;

}
