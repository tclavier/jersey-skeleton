package fr.iutinfo;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	private static ArrayList<User> users = new ArrayList<>();
	
	@POST
	public User createUser(User user) {
		int id = users.size();
		user.setId(id+1);
		users.add(user);
		return user;
	}
	
	@GET
	@Path("/{name}")
	public User getUser(@PathParam("name") String name ) {
		User user = new User();
		user.setName(name);
		return user;
	}
	
	@GET
	public List<User> getUsers(@DefaultValue("10") @QueryParam("limit") int limit) {
		return users;
	}

}
