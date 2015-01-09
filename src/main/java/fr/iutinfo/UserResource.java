package fr.iutinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserResource {
	private static ArrayList<User> users = new ArrayList<>();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public User createUser(User user) {
		int id = users.size();
		user.setId(id+1);
		users.add(user);
		return user;
	}
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("name") String name ) {
		User user = new User();
		user.setName(name);
		return user;
	}
	
	@GET
	//@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(
			@DefaultValue("10") @QueryParam("limit") int limit) {
		return users;
	}

}
