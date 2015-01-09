package fr.iutinfo;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import javax.validation.constraints.AssertTrue;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;


public class UserTest extends JerseyTest {
	@Override
    protected Application configure() {
        return new App();
    }

	@Test
	public void testReadUserWithNameFooAsJsonString() {
		String json = target("/user/foo").request().get(String.class);
		assertEquals("{\"id\":0,\"name\":\"foo\"}", json);
	}
	
	@Test
	public void testReadUserWithNameFooAsObject() {
		User utilisateur = target("/user/foo").request().get(User.class);
		assertEquals("foo", utilisateur.getName());
	}
	
	@Test
	public void testCreateUserMustReturnUserWithId() {
		User user = new User();
		user.setName("thomas");
		user.setId(0);
	    Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
		User savedUser = target("/user").request().post(userEntity).readEntity(User.class);
		System.out.println(savedUser);
		assertTrue(savedUser.getId() > 0);
	}
	
	@Test
	public void testGetingSameUserTwice() {
		User user1 = target("/user/foo").request().get(User.class);
		User user2 = target("/user/foo").request().get(User.class);
		assertEquals(user1, user2);
	}
	
	
	public void tesListAllUsers() {
		User user1 = target("/user/foo1").request().get(User.class);
		User user2 = target("/user/foo2").request().get(User.class);
		//List<User> users = target("/user/").request().get(List.class);
		String userS = target("/user/").request().get(String.class);
		System.out.println(userS);
		//assertTrue(users.size() == 2);
	}
}