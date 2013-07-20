package controllers.cms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import models.cms.CMSImage;
import models.cms.CMSPage;

import org.apache.commons.lang.StringUtils;

import play.data.validation.Valid;
import play.db.jpa.Blob;
import play.libs.MimeTypes;
import play.mvc.Controller;
import play.mvc.With;
import controllers.Check;
import controllers.Secure;

/**
 * Admin controller for CMS module.
 */
@With(Secure.class)
@Check("admin")
public class Admin extends Controller {

    /**
     * Display all CMS pages.
     */
    public static void index() {
		List<CMSPage> pages = CMSPage.all().fetch();
		render(pages);
	}

    /**
     * Display edit form for a CMS page.
     *
     * @param tmpl
     * @param pageName
     */
	public static void editPage(String tmpl, String pageName) {
		CMSPage page = CMSPage.findById(pageName);
		renderTemplate("@edit", page, tmpl);
	}

    /**
     * Display form to add a CMS page.
     */
	public static void addPage() {
		CMSPage page = new CMSPage();
		renderTemplate("@edit", page);
	}

    /**
     * SAving a CMS page.
     *
     * @param page
     * @param tmpl
     */
	public static void savePage(@Valid CMSPage page, String tmpl) {
		if (request.params.get("delete") != null) {
			page.delete();
			index();
		}
		page.save();
		if (request.params.get("savePage") != null)
			Frontend.show(tmpl, page.name);
		index();
	}

    /**
     * Upload an image for tiny mce.
     *
     * @param data
     * @param title
     */
	public static void upload(File data, String title) {
		CMSImage image = new CMSImage();
		image.name = data.getName();
		if (StringUtils.isEmpty(title))
			image.title = data.getName();
		else
			image.title = title;
		String mimeType = MimeTypes.getContentType(data.getName());
		image.data = new Blob();
		try {
			image.data.set(new FileInputStream(data), mimeType);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		image.save();
		redirectToStatic("/public/tiny_mce/plugins/advimage/image.htm");
	}

    /**
     * Display all image.
     */
    public static void imagelist() {
		List<CMSImage> images = CMSImage.findAll();
		render(images);
	}
}
