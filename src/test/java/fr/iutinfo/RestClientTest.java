package fr.iutinfo;

import org.glassfish.jersey.test.JerseyTest;

public class RestClientTest extends JerseyTest {
	/*@Override
    protected Application configure() {
        return new App();
    }

	@Test
	public void getUsers () {
		int port = this.getPort();
		String baseUrl = "http://localhost:"+port+"/user/";
		RestClient client = new RestClient();
		client.addUser(new User(0, "Thomas"),  baseUrl );
		client.addUser(new User(0, "Yann"),  baseUrl );
		List<User> users = client.getUrlAsUser(baseUrl );
		assertEquals(2, users.size());
	}*/
}
