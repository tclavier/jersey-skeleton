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
		l.setAuthor("toto");
		l.setName("niveau de toto");
		l.setContent("1 2 0 0,0 0 0 0,0 0 0 0,0 0 0 0");
		l.setInstructions("1,2");
		l.setMaxInstructions(1);
		levelDao.insert(l.getName(), l.content(), l.instructions(), l.getMaxInstructions(), l.getAuthor());
		
		
		Instruction i = new Instruction();
		i.setBlock(0);
		i.setColor(42);
		i.setCode("codeJsIci");
		i.setId(1);
		i.setName("block de test");
		instructionsDao.insert(i.getName(), i.getCode(), i.getColor(), i.getBlock());
		i.setBlock(1);
		i.setColor(133);
		i.setCode("codeJsIciv2");
		i.setId(2);
		i.setName("block de test 2");
		instructionsDao.insert(i.getName(), i.getCode(), i.getColor(), i.getBlock());
		
		return "Database Reset.";
	}
}
