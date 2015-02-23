package fr.iutinfo.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.Level;
import fr.iutinfo.dao.FriendsRelationsDao;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.UserDao;


@Path("/resetDb")
@Produces(MediaType.APPLICATION_JSON)
public class DbResetResource {

	private static FriendsRelationsDao friendDao = App.dbi.open(FriendsRelationsDao.class);
	private static UserDao userDao = App.dbi.open(UserDao.class);
	private static LevelDao levelDao = App.dbi.open(LevelDao.class);

	
	@GET
	public String resetDatabase() {
		
		friendDao.dropFriendsRelationsTable();
		userDao.dropUserTable();
		levelDao.dropLevelsTable();
		
		friendDao.createFriendsRelationsTable();
		userDao.createUserTable();
		levelDao.createLevelsTable();
		
		
		Level l = new Level();
		l.setAuthor("toto");
		l.setName("niveau de toto");
		l.setContent("0 0 0 0,0 0 0 0,0 0 0 0,0 0 0 0");
		levelDao.insert(l.getName(), l.getContent(), l.getAuthor());
		
		
		friendDao.createRelation(2, 1);
		friendDao.createRelation(1, 2);
		
		return "Database Reset.";
	}
}
