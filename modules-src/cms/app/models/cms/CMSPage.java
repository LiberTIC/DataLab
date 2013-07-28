package models.cms;

import play.data.validation.Required;
import play.db.jpa.GenericModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

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
    public String type; // could be fragment, page, blog

}
