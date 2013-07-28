package controllers.cms;

import models.cms.CMSImage;
import models.cms.CMSPage;
import play.mvc.Controller;

/**
 * Controller to managed frontend CMS module.
 */
public class Frontend extends Controller {

    /**
     * Dsiplay a CMS page.
     *
     * @param pageName
     */
	public static void show(String pageName) {
		CMSPage page = CMSPage.findById(pageName);
		notFoundIfNull(page);
		renderTemplate("/cms/" + page.template + ".html", page);
	}

    /**
     * Render an image.
     *
     * @param name
     */
	public static void image(String name) {
		CMSImage image = CMSImage.findById(name);
		renderBinary(image.data.get());
	}
}
