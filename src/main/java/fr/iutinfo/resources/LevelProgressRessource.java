package fr.iutinfo.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.Feedback;
import fr.iutinfo.beans.LevelProgress;
import fr.iutinfo.dao.LevelProgressDAO;
import fr.iutinfo.utils.Session;


@Path("/LevelProgress")
@Produces(MediaType.APPLICATION_JSON)

public class LevelProgressRessource {
	private static LevelProgressDAO dao = App.dbi.open(LevelProgressDAO.class);
	
	@POST
	@Path("/putProgress/{cookie}/{idLevel}")
	public Feedback putProgress(@PathParam("cookie") String cookie, @PathParam("idLevel") int idLevel) {
		
		int idUser = Session.getUser(cookie).getId();
		LevelProgress tmp = dao.getLevel(idUser, idLevel);
		if (tmp != null)
			return new Feedback(false, "Niveau deja validé !");
		
		dao.insert(idUser, idLevel);
		
		return new Feedback(true, "OK");
	}
	
	@GET
	@Path("/getLevels/{cookie}")
	public List<Integer> getLevelsDone(@PathParam("cookie") String cookie) {
		return dao.getLevelsFromUser(Session.getUser(cookie).getId());
	}
}
