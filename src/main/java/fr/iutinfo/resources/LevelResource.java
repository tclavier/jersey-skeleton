package fr.iutinfo.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.bins.Level;
import fr.iutinfo.dao.LevelDao;
import fr.iutinfo.utils.SerializeTools;


@Path("/levels")
@Produces(MediaType.APPLICATION_JSON)
public class LevelResource {
	private static LevelDao levelDao = App.dbi.open(LevelDao.class);

	
	public LevelResource() {
		try {
			levelDao.createLevelsTable();
		} catch (Exception e) {
			System.out.println("Table levels déjà là !");
		}
		//levelDao.insert("toto", SerializeTools.serializeLevel(new Level()), "toto");
	}
	
	@GET
	@Path("{id}")
	public Level getLevel(@PathParam("id") Integer id) {
		/*Level level = levelDao.findById(id);
		if(level == null)
			throw new WebApplicationException(404);*/
		return new Level();
	}
	
	@GET
	public List<Level> getLevels() {
		Level lvl1 = new Level();
		Level lvl2 = new Level();
		Level lvl3 = new Level();
		List<Level> levels = new ArrayList<Level>();
		levels.add(lvl1);
		levels.add(lvl2);
		levels.add(lvl3);
		/*List<Level> levels = levelDao.getAll();
		if(levels == null)
			throw new WebApplicationException(404);*/
		return levels;
	}
}
