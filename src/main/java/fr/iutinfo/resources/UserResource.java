package fr.iutinfo.resources;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.Feedback;
import fr.iutinfo.beans.User;
import fr.iutinfo.dao.UserDao;
import fr.iutinfo.utils.Session;
import fr.iutinfo.utils.Utils;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserResource {
	private static UserDao dao = App.dbi.open(UserDao.class);

	public UserResource() {}

	/**
	 * Insert l'utilisateur si celui ci est valide.
	 * @param user
	 * @return
	 */
	@POST
	@Path("/register")
	public Feedback createUser(User user) {
		Feedback fb = null;
		try {
			fb = isValidUser(user);
			if(fb.isSuccess()) {

				// on hashe le mdp pour le protéger
				String hashedPassword = Utils.hashMD5(user.getPassword());
				if(hashedPassword == null)
					return new Feedback(false, "An error occurs during hashing");
				user.setPassword(hashedPassword);

				// on insert l'utilisateur dans la bdd.
				dao.insert(user.getName(), user.getPassword(), user.getEmail());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Feedback(false, "An error occurs during insertion to database");
		}
		return fb;
	}

	/**
	 * Vérifie si l'utilisateur existe déjà (pseudo/email), si il est valide (email correcte, pseudo correct, mdp correct...)
	 * @param u
	 * @return
	 */
	private Feedback isValidUser(User user) {

		// On test si le pseudo est correcte et disponible
		Feedback f = isNameValid(user.getName());
		if (!f.isSuccess()) 
			return f;

		// test si le mail est correct 
		f = isMailValid(user.getEmail());
		if (!f.isSuccess())
			return f;

		// test si le mdp est correct
		f = isPasswordValid(user.getPassword());
		if (!f.isSuccess())
			return f;


		return new Feedback(true, "Register OK");
	}

	/**
	 * Fonction testant si le pseudo est disponible et valide
	 * @param pseudo
	 * @return
	 */
	private Feedback isNameValid(String pseudo) {
		if(!pseudo.matches("^[a-zA-Z0-9À-ÿ-]{3,20}$")) {
			return new Feedback(false, "Le pseudo est invalide !");
		}

		// Test si le pseudo existe déjà ou non
		User u = dao.isNameExist(pseudo);
		if(u != null)
			return new Feedback(false, "Le pseudo est déjà utilisé");

		return new Feedback(true, "Pseudo valide et disponible");
	}

	/**
	 * Fonction testant si l'adresse mail est valide et disponible
	 * @param mail
	 * @return
	 */
	private Feedback isMailValid(String mail) {
		// test si l'email est correcte
		if(!mail.matches("^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$")) {
			return new Feedback(false, "L'adresse mail est incorrecte");
		}

		// Test si l'email a déjà été utilisée
		User u = dao.isEmailExist(mail);
		if(u != null)
			return new Feedback(false, "L'adresse email est déjà utilisée");

		return new Feedback(true, "Mail valide et disponible");
	}

	private Feedback isPasswordValid(String password) {

		if(password.length() < 6) {
			return new Feedback(false, "Le mot de passe doit faire au moins 6 caractères !");
		}

		return new Feedback(true, "Mot de passe correct");
	}


	@POST
	@Path("/login")
	public Feedback logUser(User user) {
		String hashedPassword = Utils.hashMD5(user.getPassword());
		if(hashedPassword == null)
			return new Feedback(false, "An error occurs during hashing");
		user.setPassword(hashedPassword);
		User u = null;
		try {
			u = dao.userIsCorrect(user.getName(), user.getPassword());
			if(u == null) 
				return new Feedback(false, "Mauvais pseudo/mot de passe !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Feedback(false, "An error occurs during query to database");
		}

		if(Session.isLogged(user))
			return new Feedback(false, "Vous êtes déjà connecté !");
		// User logged
		// générate uniq id
		UUID id = UUID.randomUUID();
		// add to logged users
		Session.addUser(id.toString(), u);

		return new Feedback(true, id.toString());
	}

	@GET
	@Path("/isLogged/{cookie}")
	public Feedback isLogged(@PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie))
			return new Feedback(true, "connected");
		return new Feedback(false, "not connected");
	}

	@GET
	@Path("/logout/{cookie}")
	public Feedback logout(@PathParam("cookie") String cookie) {
		Session.removeUser(cookie);
		return new Feedback(true, "Vous avez bien été déconnecté");
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
	@Path("/me/{cookie}")
	public User getUser(@PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie))
			return Session.getUser(cookie);

		throw new WebApplicationException(404);
	}


	@GET
	@Path("/search")
	public List<User> searchUsers(@QueryParam("term") String term) {
		List<User> out = dao.searchUsers(term + "%");
		if (out == null) {
			throw new WebApplicationException(404);
		}
		return out;
	}

	@GET
	@Path("/getId/{name}")
	public User getId(@PathParam("name") String name) {
		User out = dao.findByName(name);
		if (out == null) {
			throw new WebApplicationException(404);
		}
		return out;
	}

	@PUT
	@Path("/updateNotifDate/{cookie}")
	public Feedback updateNotifDate(@PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie)) {
			dao.updateNotifDate(Session.getUser(cookie).getId());
			return new Feedback(true, "update done");
		}
		return new Feedback(false, "Vous n'êtes pas enregistré !");
	}

	@PUT
	@Path("/updateName/{cookie}/{pseudo}")
	public Feedback updatePseudo(@PathParam("cookie") String cookie, @PathParam("pseudo") String pseudo) {

		if(Session.isLogged(cookie)) {

			// Si le pseudo est valide on le met à jour
			Feedback f = isNameValid(pseudo);
			if (!f.isSuccess())
				return f;

			dao.updateName(Session.getUser(cookie).getId(), pseudo);

			return new Feedback(true, "Nom change !");
		}

		return new Feedback(false, "Vous n'êtes pas enregistre");
	}

	@PUT
	@Path("/updateEmail/{cookie}/{email}")
	public Feedback updateEmail(@PathParam("cookie") String cookie, @PathParam("email") String email) {


		if (Session.isLogged(cookie)) {
			Feedback f = isMailValid(email);

			if (!f.isSuccess())
				return f;

			dao.updateEmail(Session.getUser(cookie).getId(), email);
			return new Feedback(true, "Email change !");

		}

		return new Feedback(false, "Vous n'êtes pas enregistre");
	}

	@PUT
	@Path("/updatePassword/{cookie}/{oldPassword}/{newPassword}")
	public Feedback updatePassword(@PathParam("cookie") String cookie, @PathParam("oldPassword") String oldPassword, @PathParam("newPassword") String password) {

		if(Session.isLogged(cookie)) {
			User u;
			u = dao.userIsCorrect(Session.getUser(cookie).getName(), Utils.hashMD5(oldPassword));
			
			if ( u == null ) {
				return new Feedback(false, "L'ancien mot de passe est invalide !");
			}
			
			String hashedPassword = Utils.hashMD5(password);
			Feedback f = isPasswordValid(hashedPassword);
			if (!f.isSuccess())
				return f;

			dao.updatePassword(Session.getUser(cookie).getId(), hashedPassword);
			return new Feedback(true, "Mot de passe change !");


		}

		return new Feedback(false, "Vous n'êtes pas enregistre");
	}


	@GET
	public List<User> getUsers() {
		List<User> out = dao.getAll();
		if (out == null) {
			throw new WebApplicationException(404);
		}
		return out;
	}

	@GET
	@Path("/sortByName")
	public List<User> getSortUsers() {
		return dao.getAllNameSort();
	}

}
