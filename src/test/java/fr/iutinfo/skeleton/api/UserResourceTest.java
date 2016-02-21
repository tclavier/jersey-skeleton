package fr.iutinfo.skeleton.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;


public class UserResourceTest extends HelperTest {
	@Override
    protected Application configure() {
        return new Api();
    }

	@Test
	public void testReadUserWithNameFooAsJsonString() {
		createUserWithName("foo");
		String json = target("/user/foo").request().get(String.class);
		assertTrue(json.contains("\"name\":\"foo\""));
	}

	@Test
	public void testReadUserWithNameFooAsObject() {
		User utilisateur = target("/user/foo").request().get(User.class);
		assertEquals("foo", utilisateur.getName());
	}
	
	@Test
	public void testCreateUserMustReturnUserWithId() {
		User savedUser = createUserWithName("thomas");
		assertTrue(savedUser.getId() > 0);
	}

	@Test
	public void testUpdateUserName() {
		User u = createUserWithName("thomas");
		u.setName("yann");
		Response rep = target("/user").path("" + u.getId()).request()
				.put(Entity.entity(u, MediaType.APPLICATION_JSON));
		User updatedUser = rep.readEntity(User.class);
		assertEquals("yann", updatedUser.getName());
	}
	
	@Test
	public void testGetingSameUserTwice() {
		User user1 = target("/user/foo").request().get(User.class);
		User user2 = target("/user/foo").request().get(User.class);
		assertEquals(user1.toString(), user2.toString());
	}
	
	@Test
	public void testReadUnavailableUser () {
		int status = target("/user/bar").request().get().getStatus();
		assertEquals(404, status);
	}
	
	@Test
	public void tesListAllUsers() {
		createUserWithName("toto");
		createUserWithName("titi");
		List<User> users = target("/user/").request().get(new GenericType<List<User>>() {
		});
		assertTrue(users.size() >= 2);
	}

	@Test
	public void after_delete_read_user_sould_return_202() {
		User u = createUserWithName("toto");
		int status = target("/user/"+u.getId()).request().delete().getStatus();
        assertEquals(202, status);
        
	}

    @Test
    public void read_user_richard_should_return_good_alias() {
        createUserWithAlias("richard stallman", "rms");
        User user = target("/user/richard stallman").request().get(User.class);
        assertEquals("rms", user.getAlias());
    }

	@Override
	String getCreateUserResouceUrl() {
		return "/user";
	}
}