package fr.iutinfo.resources;

import java.util.Arrays;
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
import fr.iutinfo.dao.InstructionsDao;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.utils.Session;


@Path("/levels")
@Produces(MediaType.APPLICATION_JSON)
public class LevelResource {
	private static LevelDao levelDao = App.dbi.open(LevelDao.class);
	private static InstructionsDao instructionsDao = App.dbi.open(InstructionsDao.class);

	
	public LevelResource() {}
	
	@GET
	@Path("{id}")
	public Level getLevel(@PathParam("id") int id) {
		Level level = levelDao.findById(id);
		if(level == null)
			throw new WebApplicationException(404);
		
		level.setInstructionsList(instructionsDao.getAllId(Arrays.asList(level.getStructuredInstructions())));
		
		return level;
	}
	
	@GET
	public List<Level> getLevels() {
		List<Level> levels = levelDao.getAll();
		if(levels == null)
			throw new WebApplicationException(404);
		return levels;
	}
	
	@GET
	@Path("author/{authorId}")
	public List<Level> getLevelsByAuthor(@PathParam("authorId") int authorId) {
		List<Level> levels = levelDao.getAllByAuthor(authorId);
		if(levels == null)
			throw new WebApplicationException(404);
		return levels;
	}
	
	
	/*@PUT
	@Path("{id}/nextLevel/{nextLevelId}")
	public Feedback setNextLevel(@PathParam("id") int id, @PathParam("idNextLevel") int nextLevelId) {
		if(levelDao.findById(nextLevelId) == null)
			return new Feedback(false, "Le niveau suivant n'existe pas !");
		if(levelDao.findById(id) == null)
			return new Feedback(false, "Le niveau actuel n'existe pas !");
		levelDao.setNextLevel(nextLevelId, id);
		return new Feedback(true, "La modification à été apportée !");
	}*/
	
	
	/**
	 * Insert le niveau si celui ci est valide.
	 * @param level
	 * @return
	 */
	@POST
	@Path("/add/{cookie}")
	public Feedback createUser(Level level, @PathParam("cookie") String cookie) {
		if(Session.isLogged(cookie)) {
			// User enregistré, l'envoie du niveau peux être effectué
			if(isValidLevel(level)) {
				// -1 comme prochain niveau de la série = dernier niveau
			
				levelDao.insert(level.getName(), level.content(), level.instructions(), level.getMaxInstructions(), Session.getUser(cookie).getId());
				
				return new Feedback(true, "Le niveau a bien été enregistré !");
			}
			return new Feedback(false, "Le niveau n'est pas valide ou à été corrompu.");
		}
		
		
		return new Feedback(false, "Vous n'êtes pas enregistré !");
	}

	private boolean isValidLevel(Level level) {
		// TODO : Check the level
		return true;
	}
	
	
	
}
