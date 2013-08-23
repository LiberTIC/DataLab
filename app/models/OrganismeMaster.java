package models;

import play.Logger;
import play.db.jpa.Model;
import play.modules.search.Indexed;
import play.modules.search.ModelVersioned;
import play.modules.search.Query;
import play.modules.search.Search;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Indexed

public class OrganismeMaster extends Model implements ModelVersioned {

    private final static int ITEM_PER_PAGE = 5;
    public static final String MAP_RESULT_LIST = "result";
    public static final String MAP_RESULT_NB = "nb";

    /**
     * Boolean field to know if the organisme is a partenaire.
     */
    public Boolean isPartenaire = false;

    @OneToMany(mappedBy="master", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Organisme> versions;

    /**
     * Return all partenaire.
     * @return
     */
    public static List<OrganismeMaster> getAllPartenaires(){
        return find("isPartenaire IS TRUE").fetch();
    }

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
                data += "\"" + organisme.nbSalarie.libelle.replaceAll("\"", "\\\"") + "\";";
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


    /**
     * Search an organisme with lucene.
     *
     * @param query
     * @param typologies
     * @param deps
     * @return
     */
    public static Map<String, Object> search(String query, List<Long> typologies, List<String> deps, Integer page){
        if (page == null) {
            page =1;
        }
        // default search
        String search = "*:*";
        if(query != null && query.trim().length() >0) {
            search = "(";
            search += "nom:" + query + "*";
            search += " OR description:" + query + "~";
            search += " OR produit:" + query + "~";
            search += " OR tags:" + query + "~";
            search += " OR activites:" + query + "~";
            search += " OR ville:" + query + "~";
            search += ")";
        }

        // query for typologie
        if(typologies != null) {
            String typeQuery = "";
            for(Long id:typologies) {
                OrganismeType type = OrganismeType.findById(id);
                if(type != null) {
                    if(typeQuery.length() > 0) {
                        typeQuery += " OR ";
                    }
                    typeQuery += "type:\"" + type.libelle + "\"";
                }
            }
            search +=  " AND (" + typeQuery + ")";
        }
        if(deps != null) {
            String depsQuery = "";
            for(String dep:deps) {
                if(depsQuery.length() > 1) {
                    depsQuery += " OR ";
                }
                depsQuery += "codePostal:" + dep + "*";
            }
            search +=  " AND (" + depsQuery + ")";
        }

        Logger.debug("Search query is " + search);
        Query q = Search.search(search, OrganismeMaster.class);
        if(query == null || query.trim().length() ==0) {
            q.orderBy("created");
            q.reverse();
        }
        List<OrganismeMaster> organismes = q.page((page-1) * ITEM_PER_PAGE, ITEM_PER_PAGE).fetch();
        Integer nb = q.count();

        // create the result map
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put(MAP_RESULT_NB, nb);
        result.put(MAP_RESULT_LIST, organismes);

        return result;
    }
}
