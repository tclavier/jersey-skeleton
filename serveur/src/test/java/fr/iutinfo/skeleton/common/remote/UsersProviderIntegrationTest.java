package fr.iutinfo.skeleton.common.remote;

import fr.iutinfo.skeleton.api.Api;
import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.User;
import fr.iutinfo.skeleton.api.UserDao;
import fr.iutinfo.skeleton.common.dto.UserDto;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UsersProviderIntegrationTest extends JerseyTest {

    private UserDao userDao = BDDFactory.getDbi().open(UserDao.class);
    private UsersProvider usersProvider = new UsersProvider(getBaseUri().toString());

    @Override
    protected Application configure() {
        return new Api();
    }

    @Test
    public void should_read_remote_user() {
        initDatabase();
        createUser("Thomas");

        UserDto user = usersProvider.readUser("Thomas");
        Assert.assertEquals("Thomas", user.getName());
    }

    @Test
    public void should_read_all_remote_user() {
        initDatabase();
        createUser("Thomas");
        createUser("Olivier");

        List<UserDto> users = usersProvider.readAllUsers();
        Assert.assertEquals(2, users.size());
    }

    @Test
    public void should_add_remote_user() {
        initDatabase();
        UserDto olivier = new UserDto();
        olivier.setName("Olivier");

        UserDto remoteUser = usersProvider.addUser(olivier);
        User bddUser = userDao.findById(remoteUser.getId());

        Assert.assertEquals("Olivier", bddUser.getName());
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
