package fr.iutinfo;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.WebSocketContainer;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

import fr.iutinfo.resources.DbResetResource;
import fr.iutinfo.resources.FriendsRelationsResource;
import fr.iutinfo.resources.InstructionsResource;
import fr.iutinfo.resources.LevelListResource;
import fr.iutinfo.resources.LevelResource;
import fr.iutinfo.resources.MyServerEndpoint;
import fr.iutinfo.resources.ProfileInfoResource;
import fr.iutinfo.resources.UserResource;

@ApplicationPath("/v1/")
public class App extends Application {
	
	
    @Override
    public Set<Class<?>> getClasses() {
    	Set<Class<?>> s = new HashSet<Class<?>>();
    	s.add(LevelResource.class);
    	s.add(UserResource.class);
    	s.add(FriendsRelationsResource.class);
    	s.add(DbResetResource.class);
    	s.add(InstructionsResource.class);
    	s.add(ProfileInfoResource.class);
    	s.add(LevelListResource.class);    	
    	return s;
    }
    
    public static DBI dbi;
	static {
		SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:"+System.getProperty("user.home")+System.getProperty("file.separator")+"ludicode.db");
		dbi = new DBI(ds);
		System.out.println("Database created : " + System.getProperty("user.home")+System.getProperty("file.separator")+"ludicode.db");
		
		DbResetResource dbRessource = new DbResetResource();
		dbRessource.resetDatabase();
    }
}
