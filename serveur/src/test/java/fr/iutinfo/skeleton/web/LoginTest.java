package fr.iutinfo.skeleton.web;

import fr.iutinfo.skeleton.api.Helper;
import fr.iutinfo.skeleton.api.UserDao;
import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.TEMPORARY_REDIRECT;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

public class LoginTest extends JerseyTest {
    final static Logger logger = LoggerFactory.getLogger(LoginTest.class);
    private Helper h;
    private UserDao dao;
    private String path = "/login";

    @Override
    protected Application configure() {
        return new WebConfig();
    }


    @Before
    public void init() {
        h = new Helper();
        h.initDb();
    }

    @Test
    @Ignore // missing MVC template injection
    public void should_redirect_to_user_detail_with_good_authorization_header() {
        h.createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:motdepasse");
        Response response = target(path).request().header(AUTHORIZATION, authorization).get();
        int status = response.getStatus();
        assertEquals(TEMPORARY_REDIRECT.getStatusCode(), status);
    }

    @Test
    @Ignore // missing MVC template injection
    public void should_set_cookie_with_user_with_good_authorization_header() {
        h.createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:motdepasse");
        Response response = target(path).request().header(AUTHORIZATION, authorization).get();
        assertEquals(1, response.getCookies().size());
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
    public void should_return_unauthorized_headers_with_authorization_header_on_same_user() {
        h.createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:motdepasse");
        Response response = target(path).queryParam("user", "tclavier").request().header(AUTHORIZATION, authorization).get();
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
