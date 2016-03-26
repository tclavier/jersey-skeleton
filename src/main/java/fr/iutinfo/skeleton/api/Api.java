package fr.iutinfo.skeleton.api;

import javax.ws.rs.ApplicationPath;

import fr.iutinfo.skeleton.auth.AuthFilter;
import org.glassfish.jersey.filter.LoggingFilter;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath("/v1/")
public class Api extends ResourceConfig {

    public Api() {
        packages("fr.iutinfo.skeleton.api");
        //register(LoggingFilter.class);
        register(AuthFilter.class);
        register(RolesAllowedDynamicFeature.class);
    }

}
