package fr.iutinfo;

import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.beans.User;

public class RestClient {
	
	public String getUrlAsString (String url) {
		return ClientBuilder.newClient()//
        .target(url)
        .request()
        .get(String.class);
	}
	
	public List<User> getUrlAsUser (String url) {
		return ClientBuilder.newClient()//
        .target(url)
        .request()
        .get(new GenericType<List<User>>(){});
	}
	
	public User addUser (User user, String url) {
		Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
		
		return ClientBuilder.newClient()
				.target(url)
				.request()
				.post(userEntity)
				.readEntity(User.class);
	}
}
