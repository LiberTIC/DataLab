package models;

import play.db.jpa.Model;
import play.modules.search.Indexed;
import play.modules.search.ModelVersioned;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Indexed
public class OrganismeMaster extends Model implements ModelVersioned {

    /**
     * Boolean field to know if the organisme is a partenaire.
     */
    public Boolean isPartenaire = false;

    @OneToMany(mappedBy="master", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Organisme> versions;

    /**
     * Override the getter for versions to ensure to have version in the proper order.
     * @return
     */
    public List<Organisme> getVersions() {
        return Organisme.find("SELECT o FROM OrganismeMaster m JOIN m.versions o WHERE m.id=? ORDER BY o.created DESC", id).fetch();
    }

    /**
     * Retrieve the lastest organisme version, or null.
     *
     * @return
     */
    public Organisme getLastVersion() {
        List<Organisme> orgas = getVersions();
        if(orgas.size() > 0) {
            return orgas.get(0);
        }
        else{
            return null;
        }
    }

    /**
     * Generate a csv of all (last) organismes.
     *
     * @return
     */
    public static String toCsv(){
        String data = "NOM;SIRET;NB_SALARIE;TEL;EMAIL;ADRESSE;VILLE;CODE_POSTAL;PRODUIT;DESCRIPTION;TYPE\n";
        List<OrganismeMaster> organismeMasters = findAll();
        for (OrganismeMaster master : organismeMasters){
            Organisme organisme = master.getLastVersion();

            if(organisme.nbSalarie != null)
                data += "\"" + organisme.nom.replaceAll("\"", "\\\"") + "\";";
            else
                data += ";";

            if (organisme.nbSalarie != null)
                data += "\"" + organisme.nbSalarie.replaceAll("\"", "\\\"") + "\";";
            else
                data += ";";

            if (organisme.telephone != null)
                data += "\"" + organisme.telephone.replaceAll("\"", "\\\"") + "\";";
            else
                data += ";";

            if (organisme.email != null)
                data += "\"" + organisme.email.replaceAll("\"", "\\\"") + "\";";
            else
                data += ";";

            if(organisme.adresse != null)
                data += "\"" + organisme.adresse.replaceAll("\"", "\\\"") + "\";";
            else
                data += ";";

            if (organisme.ville != null)
                data += "\"" + organisme.ville.replaceAll("\"", "\\\"") + "\";";
            else
                data += ";";

            if(organisme.codePostal != null)
                data += "\"" + organisme.codePostal.replaceAll("\"", "\\\"") + "\";";
            else
                data += ";";

            if(organisme.produit != null)
                data += "\"" + organisme.produit.replaceAll("\"", "\\\"") + "\";";
            else
                data += ";";

            if(organisme.description != null)
                data += "\"" + organisme.description.replaceAll("\"", "\\\"") + "\";";
            else
                data += ";";
            if(organisme.type != null)
                data += "\"" + organisme.type.libelle.replaceAll("\"", "\\\"") + "\"\n";
            else
                data += ";";
        }
        return data;
    }

}
