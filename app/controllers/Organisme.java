package controllers;

import play.data.validation.Valid;

public class Organisme extends AbstractController {

    public static void show(Long id){
        render();
    }

    public static void edit(Long id){
        render();
    }

    public static void save(Long id, @Valid Organisme organisme){
        render();
    }

    public static void history(Long id){
        render();
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
        render();
    }
}
