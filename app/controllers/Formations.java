package controllers;

import models.Agenda;
import models.Formation;
import play.data.validation.Valid;

import java.util.List;

/**
 * Formation controller.
 */
public class Formations extends AbstractController {

    /**
     * Formation page.
     */
    public static void list(){
        // only avaible for admin user
        isAdminUser();

        // list all formation
        List<Formation> formations = Formation.findAll();

        String template = "formation";

        render(formations, template);
    }

    /**
     * List all formation item for administration.
     */
    public static void admin(){
        // only avaible for admin user
        isAdminUser();

        // list all formation
        List<Formation> formations = Formation.findAll();

        String template = "formation";

        render(formations, template);
    }

    /**
     * Edit (or create) a formation.
     *
     * @param id
     */
    public static void edit(Long id){
        // only avaible for admin user
        isAdminUser();

        Formation formation = null;
        if(id != null){
            formation = Formation.findById(id);
            notFoundIfNull(formation);
        }

        String template = "formation";

        render(formation, template);
    }

    /**
     * Save a formation.
     *
     * @param formation
     */
    public static void save(@Valid Formation formation){
        // only admin user can save
        isAdminUser();

        // is it valid ?
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            if(formation.id != null)
                edit(formation.id);
            else{
                edit(null);
            }
        }

        // save it
        formation.save();

        // redirect to admin list view
        admin();
    }

    /**
     * Action to delete a formation.
     *
     * @param id
     */
    public static void delete(Long id) {
        // only avaible for admin user
        isAdminUser();

        Formation formation = Formation.findById(id);
        notFoundIfNull(formation);

        formation.delete();

        admin();
    }

    /**
     * Render the file of formation.
     *
     * @param id
     */
    public static void file(Long id) {
        Formation formation = Formation.findById(id);
        notFoundIfNull(formation);

        if(formation.file == null || formation.file.type() == null){
            notFound();
        }

        response.setContentTypeIfNotSet(formation.file.type());
        renderBinary(formation.file.get());
    }

    /**
     * Render the image of formation.
     *
     * @param id
     */
    public static void image(Long id) {
        Formation formation = Formation.findById(id);
        notFoundIfNull(formation);

        if(formation.image == null || formation.image.type() == null){
            notFound();
        }

        response.setContentTypeIfNotSet(formation.image.type());
        renderBinary(formation.image.get());
    }

}
