package fr.iutinfo.skeleton.web;

import fr.iutinfo.skeleton.api.Helper;
import fr.iutinfo.skeleton.api.User;
import fr.iutinfo.skeleton.api.UserDao;
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

public class LoginJspTest extends JerseyTest {
    private Helper h;
    private UserDao dao;
    private String path = "/login";

    @Override
    protected Application configure() {
        return new WebConfig();
    }


    @Before
    public void init() {
        h = new Helper(target("/userdb"));
        h.initDb();
    }

    @Test
    public void should_return_unauthorized_headers_with_good_authorization_header() {
        h.createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:motdepasse");
        Response response = target(path).request().header(AUTHORIZATION, authorization).get();
        int status = response.getStatus();
        String wwwHeader = response.getHeaderString(HttpHeaders.WWW_AUTHENTICATE);
        assertEquals(UNAUTHORIZED.getStatusCode(), status);
        assertEquals("Basic realm=\"Mon application\"", wwwHeader);
    }

        @Test
        public void should_return_unauthorized_headers_without_authorization_header() {
            Response response = target(path).request().get();
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
