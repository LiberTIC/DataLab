import models.OrganismeMaster;
import org.junit.Test;
import play.modules.search.Query;
import play.modules.search.Search;
import play.test.UnitTest;

import java.util.List;

/**
 */
public class SearchTest extends UnitTest {

    /**
     * Testing getLast organismemaster method.
     */
    @Test
    public void testSearch() {
        Query q = Search.search("produit:\"Site Web\"", OrganismeMaster.class);
        List<OrganismeMaster> list = q.fetch();
        assertEquals(1, list.size());
        assertEquals("Site web", list.get(0).getLastVersion().produit);
        assertEquals("LogiSima", list.get(0).getLastVersion().nom);

        q = Search.search("produit:IT", OrganismeMaster.class);
        list = q.fetch();
        assertEquals(0, list.size());
    }
}
