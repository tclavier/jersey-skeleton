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
import fr.iutinfo.beans.LeaderboardRow;
import fr.iutinfo.beans.LevelInfo;
import fr.iutinfo.beans.LevelProgress;
import fr.iutinfo.dao.LevelProgressDao;
import fr.iutinfo.utils.Session;


@Path("/levelProgress")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")

public class LevelProgressRessource {
	private static LevelProgressDao dao = App.dbi.open(LevelProgressDao.class);
	
	@POST
	@Path("/putProgress/{cookie}/{idLevel}")
	public Feedback putProgress(@PathParam("cookie") String cookie, @PathParam("idLevel") int idLevel) {
		
		if (!Session.isLogged(cookie))
			return new Feedback(false, "Vous devez être connecte !");
		
		int idUser = Session.getUser(cookie).getId();
		LevelProgress tmp = dao.getLevel(idUser, idLevel);
		if (tmp != null)
			return new Feedback(false, "Niveau deja validé !");
		
		dao.insert(idUser, idLevel);
		
		return new Feedback(true, "OK");
	}
	
	@GET
	@Path("/me/{cookie}")
	public List<LevelInfo> getLevelsDone(@PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie))
			return dao.getFinishedLevels(Session.getUser(cookie).getId());
		throw new WebApplicationException(404);
	}
	
	@GET
	@Path("/{idUser}")
	public List<LevelInfo> getLevelsDone(@PathParam("idUser") int idUser) {
		return dao.getFinishedLevels(idUser);
	}
	
	@GET
	@Path("/rankings")
	public List<LeaderboardRow> getLeaderboard() {
		return dao.getLevelsCount();
	}
}
