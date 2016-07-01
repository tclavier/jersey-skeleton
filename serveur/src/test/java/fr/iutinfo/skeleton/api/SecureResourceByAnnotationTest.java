package fr.iutinfo.skeleton.api;

import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static org.junit.Assert.assertEquals;

public class SecureResourceByAnnotationTest extends JerseyTest {
    private Helper h;
    private String path = "/secure/byannotation";

    @Override
    protected Application configure() {
        return new Api();
    }


    @Before
    public void init() {
        h = new Helper();
        h.initDb();
    }

    @Test
    public void should_return_forbiden_headers_without_authorization_header() {
        Response response = target(path).request().get();
        int status = response.getStatus();
        assertEquals(FORBIDDEN.getStatusCode(), status);
    }

    @Test
    public void should_return_forbiden_status_for_bad_user() {
        h.createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:pasdemotdepasse");
        int status = target(path).request().header(AUTHORIZATION, authorization).get().getStatus();
        assertEquals(FORBIDDEN.getStatusCode(), status);
    }
}
