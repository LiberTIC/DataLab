package models;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.data.validation.URL;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import play.modules.search.Field;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Formation.
 */
@Entity
@Table(name = "formation")
public class Formation extends Model {

    @Required
    public String title;

    @Required
    @Lob
    public String description;

    public Boolean difCompliant;

    @Field
    @Required
    @URL
    @MaxSize(255)
    public String inscription;

    public Blob image;

    public Blob file;


}
