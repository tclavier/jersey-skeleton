package fr.iutinfo.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.App;
import fr.iutinfo.beans.LevelList;
import fr.iutinfo.dao.LevelListDao;



@Path("/levelList")
@Produces(MediaType.APPLICATION_JSON)
public class LevelListResource {
	private static LevelListDao levelListDao = App.dbi.open(LevelListDao.class);
	
	@GET
	@Path("{id}")
	public LevelList getLevelList(@PathParam("id") int id) {
		LevelList list = levelListDao.findById(id);
		
		if(list == null)
			throw new WebApplicationException(404);
		
		list.setLevelsAssociation(levelListDao.getAssociationsOf(id));
		
		if(list.getLevelsAssociation() == null)
			throw new WebApplicationException(404);
		
		return list;
	}
	
	@GET
	public List<LevelList> getLevelLists() {
		List<LevelList> list = levelListDao.getAllLevelLists();
		
		if(list == null)
			throw new WebApplicationException(404);
		
		return list;
	}

}
