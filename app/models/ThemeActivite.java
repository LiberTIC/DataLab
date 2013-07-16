package models;


import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class ThemeActivite extends Model {

    public String libelle;
}
