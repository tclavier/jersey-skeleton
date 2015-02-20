package fr.iutinfo.resources;

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

@Path("/userdb")
@Produces(MediaType.APPLICATION_JSON)
public class UserDBResource {
	private static UserDao dao = App.dbi.open(UserDao.class);

	public UserDBResource() {
		try {
			dao.createUserTable();
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
		dao.insert("foo");
	}
	
	@POST
	public User createUser(User user) {
		int id = dao.insert(user.getName());
		user.setId(id);
		return user;
	}

	@GET
	@Path("/{name}")
	public User getUser(@PathParam("name") String name) {
		User out = dao.findByName(name);
		if (out == null) {
			throw new WebApplicationException(404);
		}
		return out;
	}

}
