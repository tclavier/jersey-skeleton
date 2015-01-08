package fr.iutinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class User {
	
	private static Map<String, User> users = new HashMap<>();
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object u) {
		return name.equals(((User) u).name);
	}
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("name") String name ) {
		if (!users.containsKey(name)) {
			User user = new User();
			user.setName(name);
			users.put(user.getName(), user);
			return user;
		}
		return users.get(name);
	}
	
	@GET
	//@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(
			@DefaultValue("10") @QueryParam("limit") int limit) {
		return new ArrayList<User>(users.values());
	}
}
