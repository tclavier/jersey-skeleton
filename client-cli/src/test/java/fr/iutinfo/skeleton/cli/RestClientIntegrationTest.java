package fr.iutinfo.skeleton.cli;

import fr.iutinfo.skeleton.api.Api;
import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.User;
import fr.iutinfo.skeleton.api.UserDao;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RestClientIntegrationTest extends JerseyTest {

    private UserDao userDao = BDDFactory.getDbi().open(UserDao.class);
    private RemoteUsersProvider remoteUsersProvider = new RemoteUsersProvider(getBaseUri().toString());

    @Override
    protected Application configure() {
        return new Api();
    }

    @Test
    public void should_read_remote_user() {
        initDatabase();
        createUser("Thomas");

        User user = remoteUsersProvider.readUser("Thomas");
        assertEquals("Thomas", user.getName());
    }

    @Test
    public void should_read_all_remote_user() {
        initDatabase();
        createUser("Thomas");
        createUser("Olivier");

        List<User> users = remoteUsersProvider.readAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void should_add_remote_user() {
        initDatabase();
        User olivier = new User();
        olivier.setName("Olivier");

        User remoteUser = remoteUsersProvider.addUser(olivier);
        User bddUser = userDao.findById(remoteUser.getId());

        assertEquals("Olivier", bddUser.getName());
    }


    private void createUser(String name) {
        User thomas = new User();
        thomas.setName(name);
        userDao.insert(thomas);
    }

    private void initDatabase() {
        userDao.dropUserTable();
        userDao.createUserTable();
    }
}
