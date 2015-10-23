package fr.iutinfo.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.filter.LoggingFilter;

import org.glassfish.jersey.server.ResourceConfig;
import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

@ApplicationPath("/v1/")
public class Api extends ResourceConfig {

    public Api() {
        packages("fr.iutinfo.api");
        register(LoggingFilter.class);
    }

}
