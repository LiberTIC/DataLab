package models.cms;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.GenericModel;

import java.util.Date;

@Entity
public class CMSImage extends GenericModel {

    @Id
    @Required
	public String name;

	@Required
	public String title;

	@Required
	public Blob data;

    public Date created = new Date();

}
