package fr.iutinfo.skeleton.api;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/v1/")
public class Api extends ResourceConfig {

    public Api() {
        packages("fr.iutinfo.skeleton.api");
        register(LoggingFilter.class);
    }

}
