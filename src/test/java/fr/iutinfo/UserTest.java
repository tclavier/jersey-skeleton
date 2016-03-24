package fr.iutinfo;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;


public class UserTest extends JerseyTest {
	/*@Override
    protected Application configure() {
        return new App();
    }

	@Test
	public void testALaCon() {
		assertEquals(true, true);
	}*/
	
	/*
	@Test
	public void testReadUserWithNameFooAsJsonString() {
		createUser("foo");
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
		User savedUser = createUser("thomas");
		assertTrue(savedUser.getId() > 0);
	}

	@Test
	public void testUpdateUserName() {
		User u = createUser("thomas");
		u.setName("yann");
		Response rep = target("/user").path(""+u.getId()).request()
				.put(Entity.entity(u,MediaType.APPLICATION_JSON));;
		User updatedUser = rep.readEntity(User.class);
		assertEquals("yann", updatedUser.getName());
	}
	
	@Test
	public void testGetingSameUserTwice() {
		User user1 = target("/user/foo").request().get(User.class);
		User user2 = target("/user/foo").request().get(User.class);
		assertEquals(user1, user2);
	}
	
	@Test
	public void testReadUnavailableUser () {
		int status = target("/user/bar").request().get().getStatus();
		assertEquals(404, status);
	}
	
	@Test
	public void tesListAllUsers() {
		createUser("toto");
		createUser("titi");
		List<User> users = target("/user/").request().get(new GenericType<List<User>>(){});
		assertTrue(users.size() >= 2);
	}

	@Test
	public void testDeleteUser() {
		User u = createUser("toto");
		int status = target("/user/"+u.getId()).request().delete().getStatus();
        assertEquals(202, status);
        
	}
	
	private User createUser(String name) {
		User user = new User(0, name);
	    Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
		User savedUser = target("/user").request().post(userEntity).readEntity(User.class);
		return savedUser;
	}
	*/
}