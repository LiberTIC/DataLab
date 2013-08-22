package models;

import org.junit.Test;
import play.test.UnitTest;

import java.util.Date;
import java.util.List;

/**
 * Test case for organismemaster & organisme model.
 */
public class OrganismeMasterTest extends UnitTest {

    /**
     * Testing getLast organismemaster method.
     */
    @Test
    public void getLastTest() {
        OrganismeMaster master = OrganismeMaster.findById(Long.valueOf(1));
        Organisme orga = master.getLastVersion();
        assertNotNull(orga);
        assertEquals(Long.valueOf(3), orga.id);
        assertEquals("LogiSima", orga.nom);
        assertEquals("21 rue d'escalibur", orga.adresse);
        assertEquals("Nantes", orga.ville);
        assertEquals("44000", orga.codePostal);
        assertEquals("bsimard@yopmail.com", orga.email);
        assertEquals("Site web", orga.produit);
        assertEquals("+33000000000", orga.telephone);
        assertEquals("Association", orga.type.libelle);
        assertNotNull(orga.wsg_x);
        assertNotNull(orga.wsg_y);
    }

    /**
     * testing getter for version.
     */
    @Test
    public void getOrderVersionTest(){
        OrganismeMaster master = OrganismeMaster.findById(Long.valueOf(1));
        List<Organisme> organismes = master.versions;
        Date lastdate = new Date();
        for (Organisme orga : organismes){
            assertTrue(lastdate.compareTo(orga.created) >0);
            lastdate = orga.created;
        }
    }

}
