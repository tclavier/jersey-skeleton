package fr.iutinfo.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.Feedback;
import fr.iutinfo.beans.Level;
import fr.iutinfo.beans.LevelList;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.LevelListDao;
import fr.iutinfo.utils.Session;



@Path("/levelLists")
@Produces(MediaType.APPLICATION_JSON)
public class LevelListResource {
	private static LevelListDao levelListDao = App.dbi.open(LevelListDao.class);
	private static LevelDao levelDao = App.dbi.open(LevelDao.class);

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
	
	@GET
	@Path("/me/full/{cookie}")
	public List<LevelList> getLevelListsWithContentOf(@PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie)) {
			List<LevelList> list = levelListDao.findByIdAuthor(Session.getUser(cookie).getId());
			
			if(list == null)
				throw new WebApplicationException(404);
			
			for(LevelList levelList : list) {
				levelList.setLevels(levelDao.getLevelsOnList(levelList.getId()));
			}
			
			
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
	
	
	/**
	 * crée une liste de niveaux vide
	 * @param levelList
	 * @return
	 */
	@PUT
	@Path("/me/{cookie}")
	public Feedback changeList(List<LevelList> levelLists, @PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie)) {
			for(LevelList list : levelLists) {
				levelListDao.deleteAssociationsOf(list.getId());
				for(int i = 0 ; i < list.getLevelsAssociation().size() ; i++) {
					levelListDao.insertAssociation(list.getId(), list.getLevelsAssociation().get(i).getIdLevel(), i);
				}
			}

			return new Feedback(true, "Les listes ont bien été modifiés !");
		}

		return new Feedback(false, "Vous n'êtes pas enregistré !");
	}
}
