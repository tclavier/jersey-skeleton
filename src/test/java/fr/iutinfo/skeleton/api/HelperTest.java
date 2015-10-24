package fr.iutinfo.skeleton.api;

import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class HelperTest extends JerseyTest {
    private User createUser(String name) {
        User user = new User(0, name);
        return postUser(user);
    }

    private User createUser(String name, String alias) {
        User user = new User(0, name, alias);
        return postUser(user);
    }

    private User postUser(User user) {
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
        return target("/user").request().post(userEntity).readEntity(User.class);
    }
}
