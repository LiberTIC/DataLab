package controllers;

import controllers.securesocial.SecureSocial;
import models.OrganismeType;
import notifier.Mails;
import play.Play;
import play.cache.Cache;
import play.data.validation.Email;
import play.data.validation.Required;
import play.libs.Codec;
import play.libs.Images;
import securesocial.provider.SocialUser;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Controller that managed contact forms.
 */
public class Contact extends AbstractController {

    /**
     * Display the contact form.
     */
    public static void index() {
        String randomID = Codec.UUID();
        SocialUser user = SecureSocial.getCurrentUser();
        if (user != null) {
            params.put("author", user.displayName);
            params.put("email", user.email);
        }
        render(randomID);
    }

    /**
     * Display the participez form.
     */
    public static void participez() {
        String randomID = Codec.UUID();
        SocialUser user = SecureSocial.getCurrentUser();
        if (user != null) {
            params.put("author", user.displayName);
            params.put("email", user.email);
        }

        List<OrganismeType> types = OrganismeType.findAll();
        render(randomID, types);
    }

    /**
     * Generate a captcha & store captcha text in cache.
     *
     * @param id
     */
    public static void captcha(String id) {
        Images.Captcha captcha = Images.captcha();
        // we set the text color of the captcha
        String code = captcha.getText("#000000");
        // we set the life time of the captcha
        Cache.set(id, code, "30mn");
        renderBinary(captcha);
    }

    /**
     * Send the contact mail.
     *
     * @param author
     * @param message
     * @param email
     * @param structure
     * @param telephone
     * @param code
     * @param randomID
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void send(String mode, Long type, String codePostal, boolean online, @Required String author, @Required String message, @Required @Email String email, String structure, String telephone,  @Required String code, String randomID) throws InterruptedException, ExecutionException {
        if (!Play.id.equals("test")) {
            validation.equals(code, Cache.get(randomID)).message("Invalid code. Please type it again");
        }
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            randomID = Codec.UUID();
            if(type.equals("contact")) {
            render("@index", randomID);
            }
            else{
                List<OrganismeType> types = OrganismeType.findAll();
                render("@participez", randomID, types);
            }
        }
        OrganismeType organismeType = null;
        if(type != null){
            organismeType = OrganismeType.findById(type);
        }
        Mails.contact(mode, organismeType, codePostal, online, author, message, email, structure, telephone);
        flash.success("Merci pour votre intéret %s", author);

        if(type.equals("contact")) {
            index();
        }
        else {
            participez();
        }
    }

}
