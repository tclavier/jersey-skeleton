package fr.iutinfo.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.Level;
import fr.iutinfo.dao.InstructionsDao;
import fr.iutinfo.dao.LevelDao;


@Path("/levels")
@Produces(MediaType.APPLICATION_JSON)
public class LevelResource {
	private static LevelDao levelDao = App.dbi.open(LevelDao.class);
	private static InstructionsDao instructionsDao = App.dbi.open(InstructionsDao.class);

	
	public LevelResource() {}
	
	@GET
	@Path("{id}")
	public Level getLevel(@PathParam("id") Integer id) {
		Level level = levelDao.findById(id);
		if(level == null)
			throw new WebApplicationException(404);
		
		level.setInstructionsList(instructionsDao.getAllId(level.structuredInstructions()));
		
		return level;
	}
	
	@GET
	public List<Level> getLevels() {
		List<Level> levels = levelDao.getAll();
		if(levels == null)
			throw new WebApplicationException(404);
		return levels;
	}
}
