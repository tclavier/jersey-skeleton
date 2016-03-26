package fr.iutinfo.skeleton.api;

import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

public class SecureResourceOnlyLoggedTest extends JerseyTest {
    private Helper h;
    private String path = "/secure/onlylogged";

    @Override
    protected Application configure() {
        return new Api();
    }


    @Before
    public void init() {
        h = new Helper(target("/userdb"));
        h.initDb();
    }

    @Test
    public void should_return_current_user_with_authorization_header() {
        h.createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:motdepasse");
        User utilisateur = target(path).request().header(AUTHORIZATION, authorization).get(User.class);
        assertEquals("tclavier", utilisateur.getName());
    }

    @Test
    public void should_return_unauthorized_headers_without_authorization_header() {
        Response response = target("/secure/onlylogged").request().get();
        int status = response.getStatus();
        String wwwHeader = response.getHeaderString(HttpHeaders.WWW_AUTHENTICATE);
        assertEquals(UNAUTHORIZED.getStatusCode(), status);
        assertEquals("Basic realm=\"Mon application\"", wwwHeader);
    }

    @Test
    public void should_return_unauthorized_status_for_bad_user() {
        h.createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:pasdemotdepasse");
        int utilisateur = target(path).request().header(AUTHORIZATION, authorization).get().getStatus();
        assertEquals(UNAUTHORIZED.getStatusCode(), utilisateur);
    }
}
