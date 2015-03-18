package fr.iutinfo.resources;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.Feedback;
import fr.iutinfo.beans.FriendRelation;
import fr.iutinfo.beans.User;
import fr.iutinfo.dao.FriendsRelationsDao;
import fr.iutinfo.dao.UserDao;
import fr.iutinfo.utils.Session;
import fr.iutinfo.utils.Utils;


@Path("/friends")
@Produces(MediaType.APPLICATION_JSON)
public class FriendsRelationsResource {

	private static FriendsRelationsDao friendDao = App.dbi.open(FriendsRelationsDao.class);
	private static UserDao userDao = App.dbi.open(UserDao.class);

	public FriendsRelationsResource() {
		/*try {
			dao.createFriendsRelationsTable();
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}*/
	}

	@GET
	@Path("/{idUser}")
	public List<User> getFriendsOf(@PathParam("idUser") int idUser) {
		List<User> out = friendDao.findFriendsOf(idUser);
		if (out == null) {
			throw new WebApplicationException(404);
		}
		return out;
	}


	@GET
	@Path("/me/{cookie}")
	public List<User> getMyFriends(@PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie))
			return friendDao.findFriendsOf(Session.getUser(cookie).getId());

		throw new WebApplicationException(404);
	}


	@GET
	@Path("/addFriend/{idFriend}/{cookie}")
	public Feedback addFriend(@PathParam("idFriend") int idFriend, @PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie)) {
			int idUser = Session.getUser(cookie).getId();
			if(userDao.findById(idFriend) != null) {
				if(friendDao.isRelationExist(idUser, idFriend) == null) {
					if(idUser != idFriend) {
						friendDao.createRelation(idUser, idFriend);
						return new Feedback(true, "Vous avez un(e) nouvel ami(e) !");
					}
					return new Feedback(false, "Vous ne pouvez pas vous ajouter vous-même !");
				}
				return new Feedback(false, "Vous êtes déjà ami(e) !");
			}
			return new Feedback(false, "L'utilisateur que vous essayez d'ajouter n'existe pas !");
		}
		return new Feedback(false, "Vous n'êtes pas enregistré !");
	}

}
