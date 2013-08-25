package models;

import play.data.validation.InFuture;
import play.data.validation.Required;
import play.data.validation.URL;
import play.db.jpa.Blob;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Model Agenda
 */
@Entity
@Table(name = "agenda")
public class Agenda extends Model {

    @Required
    public String name;

    @Required
    @InFuture
    public Date date;

    public String description;

    @Required
    public Blob image;

    public String link;

}
