package fr.iutinfo.skeleton.api;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static fr.iutinfo.skeleton.api.Helper.*;
import static org.junit.Assert.assertEquals;

public class UserResourceTest extends JerseyTest {
    private static final String PATH = "/user";
    private Helper h;

    @Override
    protected Application configure() {
        return new Api();
    }

    @Before
    public void init() {
        h = new Helper();
        Helper.initDb();
    }

    @Test
    public void read_should_return_a_user_as_object() {
        createUserWithName("foo");
        User utilisateur = target(PATH + "/foo").request().get(User.class);
        assertEquals("foo", utilisateur.getName());
    }

    @Test
    public void read_user_should_return_good_alias() {
        createUserWithAlias("richard stallman", "rms");
        User user = target(PATH + "/richard stallman").request().get(User.class);
        assertEquals("rms", user.getAlias());
    }

    @Test
    public void read_user_should_return_good_email() {
        createUserWithEmail("Ian Murdock", "ian@debian.org");
        User user = target(PATH + "/Ian Murdock").request().get(User.class);
        assertEquals("ian@debian.org", user.getEmail());
    }

    @Test
    public void read_user_should_return_user_with_same_salt() {
        String expectedSalt = "graindesel";
        createUserWithPassword("Mark Shuttleworth", "motdepasse", expectedSalt);
        User user = target(PATH + "/Mark Shuttleworth").request().get(User.class);
        assertEquals(expectedSalt, user.getSalt());
    }

    @Test
    public void read_user_should_return_hashed_password() throws NoSuchAlgorithmException {
        createUserWithPassword("Loïc Dachary", "motdepasse", "grain de sable");
        User user = target(PATH + "/Loïc Dachary").request().get(User.class);
        assertEquals("5f8619bc1f0e23ef5851cf7070732089", user.getPasswdHash());
    }

    @Test
    public void create_should_return_the_user_with_valid_id() {
        User user = new User(0, "thomas");
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
        String json = target(PATH).request().post(userEntity).readEntity(String.class);
        assertEquals("{\"id\":1,\"name\":\"thomas\"", json.substring(0, 23));
    }

    @Test
    public void list_should_return_all_users() {
        createUserWithName("foo");
        createUserWithName("bar");
        List<User> users = target(PATH + "/").request().get(new GenericType<List<User>>() {
        });
        assertEquals(2, users.size());
    }

    @Test
    public void list_all_must_be_ordered() {
        createUserWithName("foo");
        createUserWithName("bar");
        List<User> users = target(PATH + "/").request().get(new GenericType<List<User>>() {
        });
        assertEquals("foo", users.get(0).getName());
    }

    @Test
    @Ignore
    public void after_delete_read_user_sould_return_202() {
        User u = createUserWithName("toto");
        int status = target(PATH + "/" + u.getId()).request().delete().getStatus();
        assertEquals(202, status);
    }

    @Test
    public void list_should_search_in_name_field() {
        createUserWithName("foo");
        createUserWithName("bar");
        List<User> users = target(PATH + "/").queryParam("q","ba").request().get(new GenericType<List<User>>() {
        });
        assertEquals("bar", users.get(0).getName());
    }

    @Test
    public void list_should_search_in_alias_field() {
        createFullUSer("Richard Stallman", "RMS", "rms@fsf.org", "gnuPaswword");
        createFullUSer("Linus Torvalds", "linus", "linus@linux.org", "paswword");
        createFullUSer("Robert Capillo", "rob", "rob@fsf.org", "paswword");

        List<User> users = target(PATH + "/").queryParam("q","RMS").request().get(new GenericType<List<User>>() {
        });
        assertEquals("Richard Stallman", users.get(0).getName());
    }


    @Test
    public void list_should_search_in_email_field() {
        createFullUSer("Richard Stallman", "RMS", "rms@fsf.org", "gnuPaswword");
        createFullUSer("Linus Torvalds", "linus", "linus@linux.org", "paswword");
        createFullUSer("Robert Capillo", "rob", "rob@fsf.org", "paswword");

        List<User> users = target(PATH + "/").queryParam("q","fsf").request().get(new GenericType<List<User>>() {
        });
        assertEquals(2, users.size());
    }
}
