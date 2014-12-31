package fr.iutinfo;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("resources")
public class Application extends ResourceConfig {
	public  Application() {
		packages("fr.iutinfo");
		//property(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
	}
}
