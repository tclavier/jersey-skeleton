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
import fr.iutinfo.beans.Feedback;
import fr.iutinfo.beans.LevelList;
import fr.iutinfo.dao.LevelListDao;
import fr.iutinfo.utils.Session;



@Path("/levelList")
@Produces(MediaType.APPLICATION_JSON)
public class LevelListResource {
	private static LevelListDao levelListDao = App.dbi.open(LevelListDao.class);

	@GET
	@Path("{id}")
	public LevelList getLevelList(@PathParam("id") int id) {
		LevelList list = levelListDao.findById(id);

		if(list == null)
			throw new WebApplicationException(404);

		list.setLevelsAssociation(levelListDao.getAssociationsOf(id));

		if(list.getLevelsAssociation() == null)
			throw new WebApplicationException(404);

		return list;
	}

	@GET
	public List<LevelList> getLevelLists() {
		List<LevelList> list = levelListDao.getAllLevelListWithCount();

		if(list == null)
			throw new WebApplicationException(404);

		return list;
	}
	
	@GET
	@Path("/me/{cookie}")
	public List<LevelList> getLevelListsOf(@PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie)) {
			List<LevelList> list = levelListDao.findByIdAuthor(Session.getUser(cookie).getId());
			if(list == null)
				throw new WebApplicationException(404);
			
			return list;
		}
		throw new WebApplicationException(404);		
	}


	/**
	 * crée une liste de niveaux vide
	 * @param levelList
	 * @return
	 */
	@POST
	@Path("/create/{cookie}")
	public Feedback createList(LevelList levelList, @PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie)) {
			levelListDao.createList(levelList.getName(), Session.getUser(cookie).getId());

			return new Feedback(true, "La liste a bien été créée !");
		}

		return new Feedback(false, "Vous n'êtes pas enregistré !");
	}

}
