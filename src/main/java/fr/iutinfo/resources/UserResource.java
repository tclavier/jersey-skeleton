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
import fr.iutinfo.bins.Feedback;
import fr.iutinfo.bins.User;
import fr.iutinfo.dao.UserDao;
import fr.iutinfo.utils.Utils;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	private static UserDao dao = App.dbi.open(UserDao.class);

	public UserResource() {
		try {
			dao.createUserTable();
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
	}
	
	@POST
	@Path("/register")
	public Feedback createUser(User user) {
		try {
			User u = dao.isNameExist(user.getName());
			if(u != null)
				return new Feedback(false, "Le pseudo est déjà utilisé");
			
			u = dao.isEmailExist(user.getEmail());
			if(u != null)
				return new Feedback(false, "L'adresse email est déjà utilisée");
			
			
			String hashedPassword = Utils.hashMD5(user.getPassword());
			if(hashedPassword == null)
				return new Feedback(false, "An error occurs during hashing");
			user.setPassword(hashedPassword);
			
			dao.insert(user.getName(), user.getPassword(), user.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
			return new Feedback(false, "An error occurs during insertion to database");
		}
		return new Feedback(true, "Register OK");
	}
	
	
	@POST
	@Path("/login")
	public Feedback logUser(User user) {
		String hashedPassword = Utils.hashMD5(user.getPassword());
		if(hashedPassword == null)
			return new Feedback(false, "An error occurs during hashing");
		user.setPassword(hashedPassword);
		
		try {
			User u = dao.userIsCorrect(user.getName(), user.getPassword());
			if(u == null) 
				return new Feedback(false, "user doesn't exist");
		} catch (Exception e) {
			e.printStackTrace();
			return new Feedback(false, "An error occurs during query to database");
		}
		return new Feedback(true, "Login OK");
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
