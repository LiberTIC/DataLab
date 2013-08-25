package controllers;

import models.Agenda;
import play.data.validation.Valid;

import java.util.List;

/**
 * Agenda controller
 */
public class Agendas extends AbstractController {

    /**
     * List all agenda item.
     */
    public static void admin(){
        // only avaible for admin user
        isAdminUser();

        // list all agenda
        List<Agenda> agendas = Agenda.findAll();

        String template = "agenda";

        render(agendas, template);
    }

    /**
     * Edit (or create) an agenda.
     *
     * @param id
     */
    public static void edit(Long id){
        // only avaible for admin user
        isAdminUser();

        Agenda agenda = null;
        if(id != null){
            agenda = Agenda.findById(id);
            notFoundIfNull(agenda);
        }

        String template = "agenda";

        render(agenda, template);

    }

    /**
     * Save an agenda.
     *
     * @param agenda
     */
    public static void save(@Valid Agenda agenda){
        // only admin user can save
        isAdminUser();

        // is it valid ?
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            if(agenda.id != null)
                edit(agenda.id);
            else{
                edit(null);
            }
        }

        // save it
        agenda.save();

        // redirect to admin list view
        admin();
    }

    /**
     * Action to delete an agenda.
     *
     * @param id
     */
    public static void delete(Long id) {
        // only avaible for admin user
        isAdminUser();

        Agenda agenda = Agenda.findById(id);
        notFoundIfNull(agenda);

        agenda.delete();

        admin();
    }

    /**
     * Render the image of agenda.
     *
     * @param id
     */
    public static void image(Long id) {
        Agenda agenda = Agenda.findById(id);
        notFoundIfNull(agenda);

        if(agenda.image == null || agenda.image.type() == null){
            notFound();
        }

        response.setContentTypeIfNotSet(agenda.image.type());
        renderBinary(agenda.image.get());
    }

}
