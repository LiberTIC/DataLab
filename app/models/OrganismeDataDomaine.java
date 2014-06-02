package models;


import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "organismedatadomain")
public class OrganismeDataDomaine extends Model {

    @Required
    public String libelle;

    public String toString(){
        return libelle;
    }
}
