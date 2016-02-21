package fr.iutinfo.skeleton.api;

import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;

public class SecureResourceTest extends HelperTest {
    @Override
    protected Application configure() {
        return new Api();
    }

    @Test
    public void should_return_current_user_with_authorization_header () {
        createUserWithPassword("tclavier", "motdepasse", "graindesel");
        String authorization = "Basic " + Base64.encodeAsString("tclavier:motdepasse");
        User utilisateur = target("/secure").request().header(HttpHeaders.AUTHORIZATION, authorization).get(User.class);
        Assert.assertEquals("tclavier", utilisateur.getName());
    }

    @Override
    String getCreateUserResouceUrl() {
        return "/userdb";
    }
}
