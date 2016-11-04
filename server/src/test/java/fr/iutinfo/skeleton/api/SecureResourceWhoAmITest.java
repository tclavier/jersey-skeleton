package fr.iutinfo.skeleton.api;

import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static org.junit.Assert.assertEquals;

public class SecureResourceWhoAmITest extends JerseyTest {
    private String url = "/secure/who";
    private Helper h;

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
    public void should_return_current_user_with_authorization_header() {
        h.createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:motdepasse");
        User utilisateur = target(url).request().header(AUTHORIZATION, authorization).get(User.class);
        assertEquals("tclavier", utilisateur.getName());
    }

    @Test
    public void should_return_anonymous_user_without_authorization_header() {
        User utilisateur = target(url).request().get(User.class);
        assertEquals("Anonymous", utilisateur.getName());
    }

    @Test
    public void should_return_anonymous_user_for_bad_user() {
        h.createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:pasdemotdepasse");
        User utilisateur = target(url).request().header(AUTHORIZATION, authorization).get(User.class);
        assertEquals("Anonymous", utilisateur.getName());
    }

}
