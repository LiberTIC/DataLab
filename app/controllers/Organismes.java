package controllers;

import models.Organisme;
import models.OrganismeMaster;
import play.data.validation.Valid;

import java.util.List;

/**
 * Controller to manage organisme pages.
 */
public class Organismes extends AbstractController {

    /**
     * Display the last version of an organisme.
     * @param id
     */
    public static void show(Long id){
        OrganismeMaster master = OrganismeMaster.findById(id);
        if (master == null) {
            notFound();
        }
        render(master);
    }

    /**
     * Display the organisme form for edition (or creation).
     * @param id
     */
    public static void edit(Long id){
        // only authenticated user can edit
        isValidUser();

        // retrieve object
        OrganismeMaster master = OrganismeMaster.findById(id);
        Organisme organisme = master.getLastVersion();
        if (master == null) {
            notFound();
        }

        // render
        render(id, organisme);
    }

    /**
     * Save the organisme.
     *
     * @param id
     * @param organisme
     */
    public static void save(Long id, @Valid Organisme organisme){
        // only authenticated user can save
        isValidUser();

        // is it valid ?
        if(validation.hasErrors()) {
            render("@edit", id, organisme);
        }
        organisme.save();

        // retrieve organisme master or create it
        OrganismeMaster master = new OrganismeMaster();
        if (id != null ) {
            master = OrganismeMaster.findById(id);
        }
        master.versions.add(organisme);
        master.save();

        // redirect user to show
        show(id);
    }

    /**
     * Display the history of the organisme.
     *
     * @param id
     */
    public static void history(Long id){
        // retrieve organisme master or create it
        OrganismeMaster master = new OrganismeMaster();
        if (master == null) {
            notFound();
        }
        render(master);
    }

    /**
     * Produce a RSS of last ten updated/created organisation.
     */
    public static void rss(){
        render();
    }

    /**
     * Produce a CSV of all organisation items.
     */
    public static void csv(){
        List<OrganismeMaster> masters = OrganismeMaster.findAll();
        render(masters);
    }
}
