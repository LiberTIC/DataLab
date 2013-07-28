package controllers;

public class Application extends AbstractController {

    public static void index() {
        render();
    }

    public static void admin(){
        // check if it's an admin user
        isAdminUser();

        render();
    }

}