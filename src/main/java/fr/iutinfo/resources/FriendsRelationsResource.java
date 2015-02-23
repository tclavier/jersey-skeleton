package fr.iutinfo.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.User;
import fr.iutinfo.dao.FriendsRelationsDao;


@Path("/friends")
@Produces(MediaType.APPLICATION_JSON)
public class FriendsRelationsResource {
	
	private static FriendsRelationsDao dao = App.dbi.open(FriendsRelationsDao.class);
	
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
		List<User> out = dao.findFriendsOf(idUser);
		if (out == null) {
			throw new WebApplicationException(404);
		}
		return out;
	}
	
}
