package fr.iutinfo.skeleton.api;

import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public abstract class HelperTest extends JerseyTest {
    protected User createUser(String name) {
        User user = new User(0, name);
        return doPost(user);
    }

    protected User createUser(String name, String alias) {
        User user = new User(0, name, alias);
        return doPost(user);
    }

    private User doPost(User user) {
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
        return target(getResouceUrl()).request().post(userEntity).readEntity(User.class);
    }

    abstract String getResouceUrl() ;
}
