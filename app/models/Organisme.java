package models;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
public class Organisme extends Model {

    @ManyToOne
    public OrganismeMaster master;

    @Required
    public String nom;

    public Blob logo;

    public String siret;

    public String nbSalarie;

    public String telephone;

    public String email;

    public String adresse;

    @Required
    public String ville;

    public String codePostal;

    public String produit;

    public String description;

    public Date created = new Date();

    public Double wsg_x;

    public Double wsg_y;

    @ManyToOne
    public User user;

    @Required
    @ManyToOne
    public OrganismeType type;

    @OneToMany
    public List<OrganismeActivite> activites;

}
