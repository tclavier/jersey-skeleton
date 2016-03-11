package fr.iutinfo.skeleton.api;

import org.glassfish.jersey.internal.util.Base64;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

public class SecureResourceTest extends HelperTest {
    @Override
    protected Application configure() {
        return new Api();
    }

    @Test
    public void should_return_current_user_with_authorization_header() {
        createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:motdepasse");
        User utilisateur = target("/secure/forall").request().header(AUTHORIZATION, authorization).get(User.class);
        assertEquals("tclavier", utilisateur.getName());
    }

    @Test
    public void should_return_current_user_with_authorization_header_for_only_logged_ressource() {
        createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:motdepasse");
        User utilisateur = target("/secure/onlylogged").request().header(AUTHORIZATION, authorization).get(User.class);
        assertEquals("tclavier", utilisateur.getName());
    }

    @Test
    public void should_return_annonymous_user_without_header() {
        User utilisateur = target("/secure/forall").request().get(User.class);
        assertEquals("Anonymous", utilisateur.getName());
    }

    @Test
    public void should_return_unauthorized_headers_without_header() {
        Response response = target("/secure/onlylogged").request().get();
        int status = response.getStatus();
        String wwwHeader = response.getHeaderString(HttpHeaders.WWW_AUTHENTICATE);
        assertEquals(UNAUTHORIZED.getStatusCode(), status);
        assertEquals("Basic realm=\"Mon application\"", wwwHeader);
    }

    @Override
    String getCreateUserResouceUrl() {
        return "/userdb";
    }
}
