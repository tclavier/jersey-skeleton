package fr.iutinfo.skeleton.api;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class userDBResourceTest extends JerseyTest {
    private Helper h;

    @Override
    protected Application configure() {
        return new Api();
    }

    @Before
    public void init(){
        h = new Helper(target("/userdb"));
        h.initDb();
    }

    @Test
    public void read_should_return_a_user_as_object() {
        h.createUserWithName("foo");
        User utilisateur = target("/userdb/foo").request().get(User.class);
        assertEquals("foo", utilisateur.getName());
    }

    @Test
    public void read_user_should_return_good_alias() {
        h.createUserWithAlias("richard stallman", "rms");
        User user = target("/userdb/richard stallman").request().get(User.class);
        assertEquals("rms", user.getAlias());
    }

    @Test
    public void read_user_should_return_good_email() {
        h.createUserWithEmail("Ian Murdock", "ian@debian.org");
        User user = target("/userdb/Ian Murdock").request().get(User.class);
        assertEquals("ian@debian.org", user.getEmail());
    }

    @Test
    public void read_user_should_return_user_with_same_salt() {
        String expectedSalt = "graindesel";
        h.createUserWithPassword("Mark Shuttleworth", "motdepasse", expectedSalt);
        User user = target("/userdb/Mark Shuttleworth").request().get(User.class);
        assertEquals(expectedSalt, user.getSalt());
    }

    @Test
    public void read_user_should_return_hashed_password() throws NoSuchAlgorithmException {
        h.createUserWithPassword("Loïc Dachary", "motdepasse", "grain de sable");
        User user = target("/userdb/Loïc Dachary").request().get(User.class);
        assertEquals("5f8619bc1f0e23ef5851cf7070732089", user.getPasswdHash());
    }

    @Test
    public void create_should_return_the_user_with_valid_id() {
        User user = new User(0, "thomas");
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
        String json = target("/userdb").request().post(userEntity).readEntity(String.class);
        assertEquals("{\"id\":1,\"name\":\"thomas\"", json.substring(0, 23));
    }

    @Test
    public void list_should_return_all_users() {
        h.createUserWithName("foo");
        h.createUserWithName("bar");
        List<User> users = target("/userdb/").request().get(new GenericType<List<User>>() {
        });
        assertEquals(2, users.size());
    }

    @Test
    public void list_all_must_be_ordered() {
        h.createUserWithName("foo");
        h.createUserWithName("bar");
        List<User> users = target("/userdb/").request().get(new GenericType<List<User>>() {
        });
        assertEquals("foo", users.get(0).getName());
    }

}
