package fr.iutinfo;

import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig {
	public  Application() {
		packages("fr.iutinfo");
		//property(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
	}
}
