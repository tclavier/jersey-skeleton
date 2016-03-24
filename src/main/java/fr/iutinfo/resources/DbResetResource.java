package fr.iutinfo.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.qos.logback.core.property.ResourceExistsPropertyDefiner;
import fr.iutinfo.App;
import fr.iutinfo.dao.FriendsRelationsDao;
import fr.iutinfo.dao.InstructionsDao;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.dao.LevelListDao;
import fr.iutinfo.dao.LevelProgressDao;
import fr.iutinfo.dao.UserDao;
import fr.iutinfo.utils.Utils;


@Path("/resetDb")
@Produces(MediaType.TEXT_HTML)
public class DbResetResource {

	private static FriendsRelationsDao friendDao = App.dbi.open(FriendsRelationsDao.class);
	private static UserDao userDao = App.dbi.open(UserDao.class);
	private static LevelDao levelDao = App.dbi.open(LevelDao.class);
	private static InstructionsDao instructionsDao = App.dbi.open(InstructionsDao.class);
	private static LevelListDao levelListDao = App.dbi.open(LevelListDao.class);
	private static LevelProgressDao levelProgressDAO = App.dbi.open(LevelProgressDao.class);


	
	@GET
	public String whichDatabase() {
		return "<ul>"
					+ "<li><a href='resetDb/users'>Reset users table</a></li>"
					+ "<li><a href='resetDb/relations'>Reset relations table</a></li>"
					+ "<li><a href='resetDb/levels'>Reset levels table</a></li>"
					+ "<li><a href='resetDb/instructions'>Reset instructions table</a></li>"
					+ "<li><a href='resetDb/levelList'>Reset levelList</a></li>"
					+ "<li><a href='resetDb/levelProgress'>Reset levelProgress</a></li>"
					+ "<li><a href='resetDb/all'>Reset ALL tables</a></li>"
				+ "</ul>";
	}
	
	
	@GET
	@Path("all")
	public String resetDatabase() {

		resetDbUsers();
		resetDbInstructions();
		resetDbLevels(); 
		resetDbFriendsRelations();
		resetDbLevelList();
		resetDbUsers();
		resetDbLevelProgress();
		
		return "All Tables Reset";
	}

	@GET
	@Path("users")
	public String resetDbUsers() {
		userDao.dropUserTable();

		userDao.createUserTable();

		userDao.insert("toto", Utils.hashMD5("toto"), "toto@toto.to");
		userDao.insert("titi", Utils.hashMD5("titi"), "titi@titi.ti");
		userDao.insert("tata", Utils.hashMD5("tata"), "tata@tata.ta");

		return "Table user Reset";
	}
	
	@GET
	@Path("levelProgress")
	public String resetDbLevelProgress() {
		levelProgressDAO.dropLevelProgessTable();
		levelProgressDAO.createLevelProgressTable();

		levelProgressDAO.insert(1, 1);
		levelProgressDAO.insert(1, 2);
		levelProgressDAO.insert(1, 3);

		levelProgressDAO.insert(2, 1);
		levelProgressDAO.insert(2, 4);
		
		
		return "Table levelProgress Reset";
	}


	@GET
	@Path("relations")
	public String resetDbFriendsRelations() {
		friendDao.dropFriendsRelationsTable();

		friendDao.createFriendsRelationsTable();

		friendDao.createRelation(2, 1);
		friendDao.createRelation(1, 2);
		return "Table friendsRelations Reset";
	}


	@GET
	@Path("levels")
	public String resetDbLevels() {
		levelDao.dropLevelsTable();
		
		levelDao.createLevelsTable();

		levelDao.insert("Niveau 1", // name
				"1 2 1," + 			//
				"1 0 1," + 			// Level content
				"1 3 1", 			//
				"1", 				// instructions id list
				2,					// max number of instructions
				1);					// author id

		levelDao.insert("Niveau 2", // name
				"1 1 1," + 			//
				"2 0 3," + 			// Level content
				"1 1 1", 			//
				"1,3", 				// instructions id list
				3,					// max number of instructions
				1);					// author id
		
		
		levelDao.insert("Niveau 3", // name
				"1 2 1," + 			//
				"1 0 1," + 			// Level content
				"1 0 1," +
				"1 3 1", 			//
				"1,10", 			// instructions id list
				2,					// max number of instructions
				1);					// author id
		
		levelDao.insert("Niveau 4", // name
				"2 1 3," + 			//
				"0 1 0," + 			// Level content
				"0 0 0", 			//
				"1,3,10", 			// instructions id list
				4,					// max number of instructions
				1);					// author id
		
		levelDao.insert("Niveau 5", // name
				"2 1 1 1," + 		//
				"0 0 1 1," + 		// Level content
				"1 0 0 1," +
				"1 1 0 3", 			//
				"1,3,4,10", 		// instructions id list
				5,					// max number of instructions
				1);					// author id
		
		levelDao.insert("Niveau 1", // name
				"2 1 1 1," + 		//
				"0 1 1 1," + 		// Level content
				"0 1 1 1," +
				"0 0 0 3", 			//
				"1,2,4,5", 			// instructions id list
				5,					// max number of instructions
				1);					// author id
		
		levelDao.insert("Niveau 2", // name
				"3 0 0," + 			//
				"1 1 0," + 			// Level content
				"0 0 0," +
				"2 1 1", 			//
				"1,2,3,5", 			// instructions id list
				5,					// max number of instructions
				1);					// author id
		
		levelDao.insert("Niveau 3", // name
				"2 1 1 1," + 		//
				"0 1 1 1," + 		// Level content
				"0 1 1 1," +
				"0 0 0 3", 			//
				"1,3,7,10", 		// instructions id list
				4,					// max number of instructions
				1);					// author id
		
		levelDao.insert("Niveau 4", // name
				"2 1 1 1," + 		//
				"0 0 0 1," + 		// Level content
				"1 1 0 1," +
				"1 1 0 1," +
				"0 0 0 1," +
				"3 1 1 1", 			//
				"1,3,4,7,8,10", 		// instructions id list
				6,					// max number of instructions
				1);					// author id
		
		levelDao.insert("Niveau 5", // name
				"1 1 1 1 2 1," + 		//
				"0 0 3 1 0 1," + 		// Level content
				"0 1 1 1 0 0," +
				"0 1 1 1 0 1," +
				"0 0 0 0 0 0," +
				"1 1 1 0 1 1", 			//
				"1,3,4,7,8,10", 		// instructions id list
				4,					// max number of instructions
				1);					// author id
		
		levelDao.insert("Niveau 1", // name
				"1 1 1 1 1 1," + 		//
				"0 0 0 0 1 1," + 		// Level content
				"0 1 1 0 1 1," +
				"2 1 0 0 0 3," +
				"0 1 1 1 1 1," +
				"0 1 1 1 1 1", 			//
				"1,4,6,11,10", 		// instructions id list
				5,					// max number of instructions
				1);					// author id
		
		
		levelDao.insert("Niveau 2", // name
				"1 2 1 1 0 3," + 	//
				"1 0 1 1 0 1," + 	// Level content
				"1 0 0 1 0 1," +
				"0 0 1 1 0 1," +
				"1 0 1 1 0 1," +
				"0 0 0 0 0 1", 		//
				"1,3,4,7,8,10,11", 	// instructions id list
				7,					// max number of instructions
				1);					// author id
		
		levelDao.insert("Niveau 3", // name
				"0 0 0 0 0 2 1," + 	//
				"1 0 1 1 1 1 1," + 	// Level content
				"1 0 0 0 0 0 1," +
				"1 0 1 1 1 0 1," +
				"1 1 0 0 0 0 1," +
				"1 3 1 0 1 1 1," +
				"1 0 0 0 1 1 1", 		//
				"1,10,15,3,4,7", 	// instructions id list
				6,					// max number of instructions
				1);					// author id
		
		/*levelDao.insert("Niveau 2", // name
				"2 1 0 0 0 0," + 	//
				"0 1 0 1 0 0," + 	// Level content
				"0 0 0 0 1 1," +
				"0 1 0 1 0 0," +
				"1 1 0 0 0 1," +
				"0 0 0 1 0 0", 		//
				"1,3,4,7,8,10,11", 	// instructions id list
				7,					// max number of instructions
				1);					// author id*/
		
		
		

		return "Table levels Reset";
	}


	@GET
	@Path("instructions")
	public String resetDbInstructions() {
		instructionsDao.dropInstructionsTable();

		instructionsDao.createInstructionsTable();

		instructionsDao.insert("Avancer", "player.moveForward();", 65, 0, "images/doc/avancer.png", "images/doc/avancer.gif", "Le personnage se déplace d'une case vers avant.", 0);					// ID 1
		instructionsDao.insert("Reculer", "player.moveBackward();", 65, 0, "images/doc/reculer.png", "images/doc/avancer.gif", "Le personnage se déplace d'une case vers l'arrière.", 0);					// ID 2	
		instructionsDao.insert("Pivoter à gauche", "player.turnLeft();", 65, 0, "images/doc/pivoter_gauche.png", "images/doc/avancer.gif", "Le personnage effectue un quart de tour vers la gauche.", 0);			// ID 3
		instructionsDao.insert("Pivoter à droite", "player.turnRight();", 65, 0, "images/doc/pivoter_droite.png", "images/doc/avancer.gif", "Le personnage effectue un quart de tour vers la droite.", 0);			// ID 4
		instructionsDao.insert("Répeter 3 fois", "for (var i%line% = 0; i%line% < 3; ++i%line%)", 100, 1, "images/doc/répéter_n.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront executées 3 fois.", 1);	// ID 5
		instructionsDao.insert("Si chemin devant", "if (player.canGoForward())", 200, 1, "images/doc/si_devant.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il y a un chemin devant le personnage.", 2);	// ID 6
		instructionsDao.insert("Si chemin à gauche", "if (player.canGoLeft())", 200, 1, "images/doc/si_gauche.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il y a un chemin à gauche du personnage.", 2);	// ID 7
		instructionsDao.insert("Si chemin à droite", "if (player.canGoRight())", 200, 1, "images/doc/si_droite.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il y a un chemin à droite du personnage.", 2);	// ID 8
		instructionsDao.insert("Si chemin derrière", "if (player.canGoBackward())", 200, 1, "images/doc/si_derrière.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il y a un chemin dérrière le personnage.", 2);	// ID 9
        instructionsDao.insert("Répeter jusqu'a l'arrivée", "while (!player.hasArrived())", 100, 1, "images/doc/répéter_arrivée.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront executées jusqu'à ce que le personnage arrive à la fin.", 1); // ID 10	
		instructionsDao.insert("Si PAS de chemin devant", "if (!player.canGoForward())", 200, 1, "images/doc/si_non_devant.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin devant le personnage.", 2);	// ID 11
		instructionsDao.insert("Si PAS de chemin à gauche", "if (!player.canGoLeft())", 200, 1, "images/doc/si_non_gauche.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin à gauche du personnage.", 2);	// ID 12
		instructionsDao.insert("Si PAS de chemin à droite", "if (!player.canGoRight())", 200, 1, "images/doc/si_non_droite.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin à droite du personnage.", 2);	// ID 13
		instructionsDao.insert("Si PAS de chemin derrière", "if (!player.canGoBackward())", 200, 1, "images/doc/si_non_derrière.png", "images/doc/avancer.gif", "Les instructions dans ce bloc seront exécutées s'il n'y a pas de chemin dérrière le personnage.", 2);	// ID 14
		instructionsDao.insert("Si chemin devant", "if (player.canGoForward())", 200, 2, "images/doc/si_devant_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin devant le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);	// ID 15
		instructionsDao.insert("Si chemin à gauche", "if (player.canGoLeft())", 200, 2, "images/doc/si_gauche_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin à gauche le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);	// ID 16
		instructionsDao.insert("Si chemin à droite", "if (player.canGoRight())", 200, 2, "images/doc/si_droite_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin à droite le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);	// ID 17
		instructionsDao.insert("Si chemin derrière", "if (player.canGoBackward())", 200, 2, "images/doc/si_derrière_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il y a un chemin derrière le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);	// ID 18
		instructionsDao.insert("Si PAS de chemin devant", "if (!player.canGoForward())", 200, 2, "images/doc/si_non_devant_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin devant le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);	// ID 19
		instructionsDao.insert("Si PAS de chemin à gauche", "if (!player.canGoLeft())", 200, 2, "images/doc/si_non_gauche_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin à gauche le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);	// ID 20
		instructionsDao.insert("Si PAS de chemin à droite", "if (!player.canGoRight())", 200, 2, "images/doc/si_non_droite_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin à droite le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);	// ID 21
		instructionsDao.insert("Si PAS de chemin derrière", "if (!player.canGoBackward())", 200, 2, "images/doc/si_non_derrière_sinon.png", "images/doc/avancer.gif", "Les instructions dans la première partie du bloc seront exécutées s'il n'y a pas de chemin derrière le personnage. Sinon, les instructions de la deuxième partie du bloc seront executés.", 2);	// ID 22
        return "Table instructions Reset";
	}
	
	
	@GET
	@Path("levelList")
	public String resetDbLevelList() {
		levelListDao.dropLevelListAssociationsTable();
		levelListDao.dropLevelListsTable();
		
		levelListDao.createLevelListAssociationsTable();
		levelListDao.createLevelListsTable();
        
		levelListDao.createList("Tutoriel", 1);
		levelListDao.createList("Intermédiaire", 1);
		levelListDao.createList("Expert", 1);
		levelListDao.insertAssociation(1, 1, 0);
		levelListDao.insertAssociation(1, 2, 1);
		levelListDao.insertAssociation(1, 3, 2);
		levelListDao.insertAssociation(1, 4, 3);
		levelListDao.insertAssociation(1, 5, 4);
		levelListDao.insertAssociation(2, 6, 0);
		levelListDao.insertAssociation(2, 7, 1);
		levelListDao.insertAssociation(2, 8, 2);
		levelListDao.insertAssociation(2, 9, 3);
		levelListDao.insertAssociation(2, 10, 4);
		levelListDao.insertAssociation(3, 11, 0);
		levelListDao.insertAssociation(3, 12, 1);
		levelListDao.insertAssociation(3, 13, 2);
		//levelListDao.insertAssociation(3, 14, 3);
		//levelListDao.insertAssociation(3, 15, 4);
		
        return "Table instructions Reset";
	}

}
