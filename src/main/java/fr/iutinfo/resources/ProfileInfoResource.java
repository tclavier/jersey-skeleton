package fr.iutinfo.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.ProfileInfo;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.UserDao;
import fr.iutinfo.utils.Session;


@Path("/profile")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ProfileInfoResource {
	private static UserDao userDao = App.dbi.open(UserDao.class);
	private static LevelDao levelDao = App.dbi.open(LevelDao.class);

	
	@GET
	@Path("{idUser}")
	public ProfileInfo getProfileInfo(@PathParam("idUser") int idUser) {
		ProfileInfo profileInfo = new ProfileInfo();
		profileInfo.setUser(userDao.findById(idUser));
		profileInfo.setLevelsInfo(levelDao.getLevelInfoByAuthor(idUser));
		
		return profileInfo;
	}
	
	@GET
	@Path("me/{cookie}")
	public ProfileInfo getProfileInfo(@PathParam("cookie") String cookie) {
		ProfileInfo profileInfo = new ProfileInfo();
		if(Session.isLogged(cookie)) {
			int idUser = Session.getUser(cookie).getId();
			profileInfo.setUser(userDao.findById(idUser));
			profileInfo.setLevelsInfo(levelDao.getLevelInfoByAuthor(idUser));
			return profileInfo;
		}
		
		throw new WebApplicationException(404);
	}

}
