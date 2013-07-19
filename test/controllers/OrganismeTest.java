package controllers;

import org.junit.Test;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

/**
 * Functional test for organismes controller.
 */
public class OrganismeTest extends FunctionalTest {

    /**
     * Testing RSS.
     */
    @Test
    public void testRss() {
        Response response = GET("/organisme/rss");
        assertIsOk(response);
        assertContentType("application/rss+xml", response);
    }

    /**
     * Testing CSV.
     */
    @Test
    public void testCsv() {
        Response response = GET("/organisme/csv");
        assertIsOk(response);
        assertContentType("text/csv", response);
    }

    /**
     * Testing CSV.
     */
    @Test
    public void testLogo() {
        Response response = GET("/organisme/logo/1");
        assertIsOk(response);
        assertContentType("image/png", response);
    }
    
}