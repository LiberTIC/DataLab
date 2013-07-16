package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class OrganismeMaster extends Model {

    @OneToMany(mappedBy="master", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Organisme> versions;

    /**
     * Override the getter for versions to ensure to have version in the proper order.
     * @return
     */
    public List<Organisme> getVersions() {
        return Organisme.find("SELECT o FROM OrganismeMaster m JOIN m.versions o WHERE m.id=? ORDER BY o.created DESC", id).fetch();
    }

    /**
     * Retrieve the lastest organisme version, or null.
     *
     * @return
     */
    public Organisme getLastVersion() {
        List<Organisme> orgas = getVersions();
        if(orgas.size() > 0) {
            return orgas.get(0);
        }
        else{
            return null;
        }
    }

}
