package fr.iutinfo;

import java.util.List;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;

import static org.junit.Assert.*;

import org.junit.Test;


public class UserTest extends JerseyTest {
	@Override
    protected Application configure() {
        return new App();
    }

	@Test
	public void testUserWithNameFooAsJsonString() {
		String json = target("/user/foo").request().get(String.class);
		assertEquals("{\"name\":\"foo\"}", json);
	}
	
	@Test
	public void testUserWithNameFooAsObject() {
		User utilisateur = target("/user/foo").request().get(User.class);
		assertEquals("foo", utilisateur.getName());
	}
	
	@Test
	public void testCreatingSameUserTwice() {
		User user1 = target("/user/foo").request().get(User.class);
		User user2 = target("/user/foo").request().get(User.class);
		assertEquals(user1, user2);
	}
	
	@Test
	public void tesListAllUsers() {
		User user1 = target("/user/foo1").request().get(User.class);
		User user2 = target("/user/foo2").request().get(User.class);
		//List<User> users = target("/user/").request().get(List.class);
		String userS = target("/user/").request().get(String.class);
		System.out.println(userS);
		//assertTrue(users.size() == 2);
	}
}