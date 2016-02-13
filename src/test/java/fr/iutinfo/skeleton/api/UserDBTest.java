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
    public void read_should_return_a_user_as_object() {
        createUserWithAlias("foo");
        User utilisateur = target("/userdb/foo").request().get(User.class);
        assertEquals("foo", utilisateur.getName());
    }

    @Test
    public void read_user_should_return_good_alias() {
        createUserWithAlias("richard stallman", "rms");
        User user = target("/userdb/richard stallman").request().get(User.class);
        assertEquals("rms", user.getAlias());
    }

    @Test
    public void read_user_should_return_good_email() {
        createUserWithEmail("Ian Murdock", "ian@debian.org");
        User user = target("/userdb/Ian Murdock").request().get(User.class);
        assertEquals("ian@debian.org", user.getEmail());
    }

    @Test
    public void create_should_return_the_user_with_valid_id() {
        User user = new User(0, "thomas");
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
        String json = target("/userdb").request().post(userEntity).readEntity(String.class);
        assertEquals("{\"id\":1,\"name\":\"thomas\"}", json);
    }

    @Test
    public void list_should_return_all_users() {
        createUserWithAlias("foo");
        createUserWithAlias("bar");
        List<User> users = target("/userdb/").request().get(new GenericType<List<User>>() {
        });
        assertEquals(2, users.size());
    }

    @Test
    public void list_all_must_be_ordered() {
        createUserWithAlias("foo");
        createUserWithAlias("bar");
        List<User> users = target("/userdb/").request().get(new GenericType<List<User>>() {
        });
        assertEquals("foo", users.get(0).getName());
    }

    @Override
    String getResouceUrl() {
        return "/userdb";
    }
}
