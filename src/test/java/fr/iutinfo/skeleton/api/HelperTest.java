package fr.iutinfo.skeleton.api;

import org.glassfish.jersey.test.JerseyTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public abstract class HelperTest extends JerseyTest {
    final static Logger logger = LoggerFactory.getLogger(HelperTest.class);

    protected User createUserWithName(String name) {
        User user = new User(0, name);
        return doPost(user);
    }

    protected User createUserWithAlias(String name, String alias) {
        User user = new User(0, name, alias);
        return doPost(user);
    }

    protected User createUserWithEmail(String name, String email) {
        User user = new User(0, name);
        user.setEmail(email);
        return doPost(user);
    }

    protected User createUserWithPassword(String name, String passwd, String salt) {
        User user = new User(0, name);
        user.setSalt(salt);
        user.setPassword(passwd);
        logger.debug("createUserWithPassword Hash : " + user.getPasswdHash());
        return doPost(user);
    }

    protected User doPost(User user) {
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
        return target(getCreateUserResouceUrl()).request().post(userEntity).readEntity(User.class);
    }

    abstract String getCreateUserResouceUrl();
}
