package models;

import org.junit.Test;
import play.test.UnitTest;

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
        assertEquals("Site Web", orga.produit);
        assertEquals("+33000000000", orga.telephone);
        assertEquals("Independant", orga.type);
        assertNotNull(orga.wsg_x);
        assertNotNull(orga.wsg_y);
    }

}
