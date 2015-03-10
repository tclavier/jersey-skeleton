package fr.iutinfo;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

import fr.iutinfo.beans.User;
import fr.iutinfo.dao.UserDao;

public class UserDBTest extends JerseyTest {
	private static UserDao dao;
	
	@Override
    protected Application configure() {
		App app = new App();
		DBI dbi = app.dbi;
		dao = dbi.open(UserDao.class);
        return new App();
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

//	@Test
//	public void testCreateUserMustReturnUserWithId() {
//		User user = new User(0, "thomas");
//	    Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
//		String json = target("/userdb").request().post(userEntity).readEntity(String.class);
//		assertEquals("{\"id\":2,\"name\":\"thomas\"}", json);
//		
//	}

	private User createUser(String name) {
		User user = new User(0, name);
	    Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
		User savedUser = target("/userdb").request().post(userEntity).readEntity(User.class);
		return savedUser;
	}
	
	
}
