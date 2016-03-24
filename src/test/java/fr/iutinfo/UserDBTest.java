package fr.iutinfo;

import org.glassfish.jersey.test.JerseyTest;

import fr.iutinfo.dao.UserDao;

public class UserDBTest extends JerseyTest {
	private static UserDao dao;
	
	/*@Override
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
	public void testALaCon() {
		assertEquals(true, true);
	}*/
	
	/*@Test
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
	}*/
	
	
}
