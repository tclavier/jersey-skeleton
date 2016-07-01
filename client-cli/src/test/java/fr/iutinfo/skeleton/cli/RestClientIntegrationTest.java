package fr.iutinfo.skeleton.cli;

import fr.iutinfo.skeleton.api.Api;
import fr.iutinfo.skeleton.api.User;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Application;

public class RestClientIntegrationTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new Api();
    }


    @Test
    @Ignore
    public void should_create_remote_user() {
        RestClient restClient = new RestClient();
        User thomas = new User();
        thomas.setName("Thomas");
        String url = this.getBaseUri().toString() + "user/";

        restClient.addUser(thomas, url);
    }
}
