package fr.iutinfo;

import javax.ws.rs.core.Application;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.*;
import org.junit.Test;


public class TestUser extends JerseyTest {
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
}