package fr.iutinfo.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserDBTest extends JerseyTest {
	private static UserDao dao;
	
	@Override
    protected Application configure() {
		dao = BDDFactory.getDbi().open(UserDao.class);
        return new Api();
    }
	
	@Before
	public void init () {
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
		List<User> users = target("/userdb/").request().get(new GenericType<List<User>>(){});
		assertEquals(2, users.size());
	}

    @Test
    public void testListAllUserMustBeOrdered() {
        createUser("foo");
        createUser("bar");
        List<User> users = target("/userdb/").request().get(new GenericType<List<User>>(){});
        assertEquals("foo", users.get(0).getName());
    }

	private User createUser(String name) {
		User user = new User(0, name);
	    Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
		User savedUser = target("/userdb").request().post(userEntity).readEntity(User.class);
		return savedUser;
	}
	
	
}
