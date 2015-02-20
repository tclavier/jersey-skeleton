package fr.iutinfo.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.bins.User;
import fr.iutinfo.dao.UserDao;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	private static UserDao dao = App.dbi.open(UserDao.class);

	public UserResource() {
		try {
			dao.dropUserTable();
			dao.createUserTable();
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
		dao.insert("toto");
		dao.insert("titi");
	}
	
	@POST
	public User createUser(User user) {
		int id = dao.insert(user.getName());
		user.setId(id);
		return user;
	}

	@GET
	@Path("/{id}")
	public User getUser(@PathParam("id") int id) {
		User out = dao.findById(id);
		if (out == null) {
			throw new WebApplicationException(404);
		}
		return out;
	}
	
	
	@GET
	public List<User> getUsers() {
		List<User> out = dao.getAll();
		if (out == null) {
			throw new WebApplicationException(404);
		}
		return out;
	}

}
