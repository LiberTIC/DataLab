package controllers;

import models.*;
import play.Logger;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.modules.search.Query;
import play.modules.search.Search;
import play.mvc.Scope;

import java.util.List;

/**
 * Controller to manage organisme pages.
 */
public class Organismes extends AbstractController {

    /**
     * Display the last version of an organisme.
     *
     * @param id
     */
    public static void show(Long id) {
        OrganismeMaster master = OrganismeMaster.findById(id);
        notFoundIfNull(master);
        Organisme organisme = master.getLastVersion();
        render(id, organisme);
    }

    /**
     * Display the organisme form for edition (or creation).
     *
     * @param id
     */
    public static void edit(Long id) {
        // only authenticated user can edit
        isValidUser();

        // retrieve object
        Organisme organisme = null;
        if (id != null) {
            OrganismeMaster master = OrganismeMaster.findById(id);
            notFoundIfNull(master);
            organisme = master.getLastVersion();
        }

        // depends objects
        List<OrganismeType> types = OrganismeType.findAll();
        List<OrganismeActivite> activites = OrganismeActivite.findAll();
        List<OrganismeNbSalarie> nbSalaries = OrganismeNbSalarie.findAll();

        // render
        render(id, organisme, types, activites, nbSalaries);
    }

    /**
     * Save the organisme.
     *
     * @param id
     * @param organisme
     */
    public static void save(Long id, @Valid Organisme organisme, @Required Boolean cgu) {
        // only authenticated user can save
        isValidUser();

        // is it valid ?
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            edit(id);
        }

        // retrieve organisme master or create it
        OrganismeMaster master = new OrganismeMaster();
        if (id != null) {
            master = OrganismeMaster.findById(id);
        }
        organisme.master = master;
        organisme.save();
        master.save();

        // redirect user to show
        show(master.id);
    }

    /**
     * Display the history of the organisme.
     *
     * @param id
     */
    public static void history(Long id) {
        // retrieve organisme master or create it
        OrganismeMaster master = new OrganismeMaster();
        notFoundIfNull(master);
        render(master);
    }

    /**
     * Produce a RSS of last ten updated/created organisation.
     */
    public static void rss() {
        List<OrganismeMaster> masters = OrganismeMaster.findAll();
        response.contentType = "application/rss+xml";
        render(masters);
    }

    /**
     * Produce a CSV of all organisation items.
     */
    public static void csv() {
        response.contentType = "text/csv";
        response.setHeader("Content-Disposition", "attachment;filename=organismes.csv");
        renderText(OrganismeMaster.toCsv());
    }

    /**
     * Render the logo of an organisme.
     *
     * @param id
     */
    public static void logo(Long id) {
        // retrieve organisme master or create it
        OrganismeMaster master = OrganismeMaster.findById(id);
        notFoundIfNull(master);

        Organisme organisme = master.getLastVersion();
        notFoundIfNull(organisme.logo);

        response.setContentTypeIfNotSet(organisme.logo.type());
        renderBinary(organisme.logo.get());
    }

    public static void search(String query, List<Long> typologies, List<String> deps) {
        // default search
        String search = "*:*";
        if(query != null && query.trim().length() >0) {
            search = "nom:" + query + "*";
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
        List<OrganismeMaster> organismes = q.fetch();

        // populate render
        List<OrganismeType> types = OrganismeType.findAll();
        render(query, typologies, deps, types, organismes);
    }

    /**
     * Render the admin interface for organisme (list all or search)
     */
    public static void admin(String search) {
        isAdminUser();

        String query = "*:*";

        // only if there is no search params, we take the session search.
        if (search == null && Scope.Session.current().contains("admin.query")) {
            query = Scope.Session.current().get("admin.query");
        }

        if(search != null && search.trim().length() > 0) {
            query =  "nom:" + search + "*";
        }

        Logger.debug("Search query is " + query);
        Query q = Search.search(query, OrganismeMaster.class);

        List<OrganismeMaster> organismes = q.fetch();
        Scope.Session.current().put("admin.query", query);
        render(organismes, search);
    }

    /**
     * Admin action to delete on organisme.
     *
     * @param id
     */
    public static void delete(Long id) {
        isAdminUser();

        OrganismeMaster organisme = OrganismeMaster.findById(id);
        notFoundIfNull(organisme);
        organisme.delete();

        admin(null);
    }

    /**
     * Admin action to set partenaire.
     *
     * @param partenaires
     */
    public static void setPartenaires(List<Long> partenaires) {
        isAdminUser();

        for (Long id : partenaires) {
            OrganismeMaster organisme = OrganismeMaster.findById(id);
            if (organisme.isPartenaire == null | !organisme.isPartenaire) {
                organisme.isPartenaire = Boolean.TRUE;
            } else {
                organisme.isPartenaire = Boolean.FALSE;
            }
            organisme.save();
        }
        admin(null);
    }

    /**
     * View to list all partenaires.
     */
    public static void partenaires() {
        List<OrganismeMaster> partenaires = OrganismeMaster.getAllPartenaires();

        render(partenaires);
    }
}
