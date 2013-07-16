package models;

import com.google.gson.Gson;
import org.junit.Test;
import play.Logger;
import play.test.UnitTest;

public class OrganismeMasterTest extends UnitTest {

    @Test
    public void getLastTest() {
        OrganismeMaster master = OrganismeMaster.findById(Long.valueOf(1));
        Organisme orga = master.getLastVersion();
        assertNotNull(orga);
        assertEquals(Long.valueOf(2), orga.id);
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
