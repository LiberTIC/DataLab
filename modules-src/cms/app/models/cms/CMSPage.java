package models.cms;

import play.Logger;
import play.data.validation.Required;
import play.db.jpa.GenericModel;
import play.templates.Template;
import play.templates.TemplateLoader;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class CMSPage extends GenericModel {

    @Id
    @Required
    public String name;

    @Required
    public String title;

    @Lob
    @Required
    public String description;

    @Lob
    @Required
    public String body;

    @Required
    public String template;

    public Date created = new Date();

    public Date updated = new Date();

    /**
     * Find all cms template available (list all file into the cms views directory).
     *
     * @return
     */
    public static List<String> getAllTemplate() {
        List<String> res = new ArrayList<String>();
        List<Template> templates = TemplateLoader.getAllTemplate();
        for (Template template : templates) {
            if (template.getName().contains("/app/views/cms/") && !template.getName().contains("{module:cms}")) {
                Logger.debug("Find CMS template :" + template.getName());
                String name = template.getName().split("/")[template.getName().split("/").length -1].replace(".html", "");
                Logger.debug("CMS Template name is " + name);
                res.add(name);
            }
        }
        return res;
    }

    /**
     * Find all page by template.
     *
     * @param tempate
     * @return
     */
    public static List<CMSPage> getAllByTemplate(String tempate){
       return CMSPage.find("template = ?1", tempate).fetch();
    }

    /**
     * Get previous item by date by template.
     *
     * @param template
     * @return
     */
    public CMSPage previous(String template) {
        return CMSPage.find("template = ?1 AND created < ?2 order by created desc", template, created).first();
    }

    /**
     * Get previous item by date by template.
     *
     * @param template
     * @return
     */
    public CMSPage next() {
        return CMSPage.find("template = ?1 AND created > ?2 order by created desc", template, created).first();
    }

}
