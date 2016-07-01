package fr.iutinfo.skeleton.web;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;
import static org.junit.Assert.assertEquals;

public class UserViewDetail extends JerseyTest {

    @Override
    protected Application configure() {
        return new WebConfig();
    }

    @Test
    @Ignore // missing MVC template injection
    public void should_return_ok_when_anonymous_id() {
        Response response = target("/user/-1").request().get();
        int status = response.getStatus();
        assertEquals(Status.OK.getStatusCode(), status);
    }

    @Test
    public void should_return_notfound_when_id_correspond_to_nothing() {
        Response response = target("/user/-8").request().get();
        int status = response.getStatus();
        assertEquals(Status.NOT_FOUND.getStatusCode(), status);
    }
}
