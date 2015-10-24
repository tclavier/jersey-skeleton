package fr.iutinfo.skeleton.api;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDBTest extends HelperTest {
    private static UserDao dao;

    @Override
    protected Application configure() {
        dao = BDDFactory.getDbi().open(UserDao.class);
        return new Api();
    }

    @Before
    public void init() {
        dao.dropUserTable();
        dao.createUserTable();
    }

    @Test
    public void testReadUserWithNameFooAsObject() {
        createUser("foo");
        User utilisateur = target("/userdb/foo").request().get(User.class);
        assertEquals("foo", utilisateur.getName());
    }

    @Test
    public void testCreateUserMustReturnUserWithId() {
        User user = new User(0, "thomas");
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
        String json = target("/userdb").request().post(userEntity).readEntity(String.class);
        assertEquals("{\"id\":1,\"name\":\"thomas\"}", json);

    }

    @Test
    public void testListAllUser() {
        createUser("foo");
        createUser("bar");
        List<User> users = target("/userdb/").request().get(new GenericType<List<User>>() {
        });
        assertEquals(2, users.size());
    }

    @Test
    public void list_all_mst_be_ordered() {
        createUser("foo");
        createUser("bar");
        List<User> users = target("/userdb/").request().get(new GenericType<List<User>>() {
        });
        assertEquals("foo", users.get(0).getName());
    }

    @Test
    public void read_user_shoul_return_good_alias() {
        createUser("richard stallman", "rms");
        User user = target("/userdb/richard stallman").request().get(User.class);
        assertEquals("rms", user.getAlias());
    }

    @Override
    String getResouceUrl() {
        return "/userdb";
    }
}
