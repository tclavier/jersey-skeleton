package fr.iutinfo;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

import fr.iutinfo.resources.AvatarResource;
import fr.iutinfo.resources.DbResetResource;
import fr.iutinfo.resources.FriendsRelationsResource;
import fr.iutinfo.resources.InstructionsResource;
import fr.iutinfo.resources.LevelListResource;
import fr.iutinfo.resources.LevelProgressRessource;
import fr.iutinfo.resources.LevelResource;
import fr.iutinfo.resources.ProfileInfoResource;
import fr.iutinfo.resources.UserResource;

/**
 * Main classe for the Maven Application.
 * Initiate the connection with the the SQL database and the DBI.
 * Allows to add all the project resources to mvn.
 * @author Florent
 */
@ApplicationPath("/v1/")
public class App extends Application {
	
    /**
     * Rewrite Application getClasses
     * @return a Set with the project resource.class
     */
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
    	s.add(LevelProgressRessource.class);
    	s.add(AvatarResource.class);
    	s.add(MultiPartFeature.class);
    	return s;
    }
    
    public static DBI dbi;
    
    /*
    Initiate a new SQLDatabase named ludicode.db in the home
        directory of the server
    Instantiate the dbi based on the created database
    */
	static {
		SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:"+System.getProperty("user.home")+System.getProperty("file.separator")+"ludicode.db");
		dbi = new DBI(ds);
		System.out.println("Database created : " + System.getProperty("user.home")+System.getProperty("file.separator")+"ludicode.db");
		
		//DbResetResource dbRessource = new DbResetResource();
		//dbRessource.resetDatabase();
    }
}
