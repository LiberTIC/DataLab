package controllers;

import models.Organisme;
import models.OrganismeMaster;
import play.data.validation.Valid;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Controller to manage organisme pages.
 */
public class Organismes extends AbstractController {

    /**
     * Search organismes
     */
    public static void search() {
        render();
    }

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

        // render
        render(id, organisme);
    }

    /**
     * Save the organisme.
     *
     * @param id
     * @param organisme
     */
    public static void save(Long id, @Valid Organisme organisme) {
        // only authenticated user can save
        isValidUser();

        // is it valid ?
        if (validation.hasErrors()) {
            render("@edit", id, organisme);
        }
        organisme.save();

        // retrieve organisme master or create it
        OrganismeMaster master = new OrganismeMaster();
        if (id != null) {
            master = OrganismeMaster.findById(id);
        }
        master.save();
        organisme.master = master;
        organisme.save();

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
        OrganismeMaster master = new OrganismeMaster();
        notFoundIfNull(master);

        Organisme organisme = master.getLastVersion();
        notFoundIfNull(organisme.logo);

        response.setContentTypeIfNotSet(organisme.logo.type());
        renderBinary(organisme.logo.get());
    }
}
