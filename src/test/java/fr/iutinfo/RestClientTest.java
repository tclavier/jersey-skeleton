package fr.iutinfo;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import fr.iutinfo.bins.User;

public class RestClientTest extends JerseyTest {
	@Override
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
	}
}
