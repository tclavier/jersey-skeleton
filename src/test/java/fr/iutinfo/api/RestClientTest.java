package fr.iutinfo.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RestClientTest extends JerseyTest {
    private static UserDao userDao;

    @Override
    protected Application configure() {
        userDao = BDDFactory.getDbi().open(UserDao.class);
        //return new Api();
        return new ResourceConfig(UserDBResource.class);
    }

    @Before
    public void cleanupDb() {
        userDao.dropUserTable();
        userDao.createUserTable();
    }

    @Test
    public void should_return_2_clients() {
        String baseUrl = this.getBaseUri() + "/userdb/";
        RestClient client = new RestClient();
        client.addUser(new User(0, "Thomas"), baseUrl);
        client.addUser(new User(0, "Yann"), baseUrl);
        List<User> users = client.getUrlAsUser(baseUrl);
        assertEquals(2, users.size());
    }
}
