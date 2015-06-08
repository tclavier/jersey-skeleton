package fr.iutinfo.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("html")
public class Web extends ResourceConfig {

    public Web() {
        packages("fr.iutinfo.web");
        register(JspMvcFeature.class);
        // Tracing support.
        property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
    }

    public static DBI dbi;

    static {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:" + System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "data.db");
        dbi = new DBI(ds);
    }
}
