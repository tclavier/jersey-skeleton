package fr.iutinfo.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.Instruction;
import fr.iutinfo.beans.Level;
import fr.iutinfo.dao.FriendsRelationsDao;
import fr.iutinfo.dao.InstructionsDao;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.UserDao;
import fr.iutinfo.utils.Utils;


@Path("/resetDb")
@Produces(MediaType.APPLICATION_JSON)
public class DbResetResource {

	private static FriendsRelationsDao friendDao = App.dbi.open(FriendsRelationsDao.class);
	private static UserDao userDao = App.dbi.open(UserDao.class);
	private static LevelDao levelDao = App.dbi.open(LevelDao.class);
	private static InstructionsDao instructionsDao = App.dbi.open(InstructionsDao.class);

	
	@GET
	public String resetDatabase() {
		
		friendDao.dropFriendsRelationsTable();
		userDao.dropUserTable();
		levelDao.dropLevelsTable();
		instructionsDao.dropInstructionsTable();
		
		friendDao.createFriendsRelationsTable();
		userDao.createUserTable();
		levelDao.createLevelsTable();
		instructionsDao.createInstructionsTable();
		
		
		
		userDao.insert("toto", Utils.hashMD5("toto"), "toto@toto.to");
		userDao.insert("titi", Utils.hashMD5("titi"), "titi@titi.ti");	
		
		friendDao.createRelation(2, 1);
		friendDao.createRelation(1, 2);
		
		Level l = new Level();
		l.setAuthorId(1);
		l.setName("Niveau 1");
		l.setContent("1 2 1,1 0 1,1 3 1");
		l.setInstructions("1");
		l.setMaxInstructions(2);
		l.setNextLevelId(2);
		levelDao.insert(l.getName(), l.content(), l.instructions(), l.getMaxInstructions(), l.getAuthorId(), l.getNextLevelId());
		
		l.setAuthorId(1);
		l.setName("niveau de toto 2");
		l.setContent("1 2 1 1,0 0 0 0,0 0 0 0,0 0 0 0");
		l.setInstructions("1,2,3,4");
		l.setMaxInstructions(4);
		l.setNextLevelId(-1);
		levelDao.insert(l.getName(), l.content(), l.instructions(), l.getMaxInstructions(), l.getAuthorId(), l.getNextLevelId());
		
		
		instructionsDao.insert("Avancer", "player.moveForward();", 65, 0);
		instructionsDao.insert("Reculer", "player.moveBackward();", 65, 0);
		instructionsDao.insert("Tourner à gauche", "player.turnLeft();", 65, 0);
		instructionsDao.insert("Tourner à droite", "player.turnRight();", 65, 0);
		instructionsDao.insert("Répeter 3 fois", "for (var i = 0; i < 3; ++i)", 100, 1);
		
		return "Database Reset.";
	}
}
