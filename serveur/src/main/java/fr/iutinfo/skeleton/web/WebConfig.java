package fr.iutinfo.skeleton.web;

import fr.iutinfo.skeleton.auth.AuthFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("html")
public class WebConfig extends ResourceConfig {

    public WebConfig() {
        packages("fr.iutinfo.skeleton.web");
        register(JspMvcFeature.class);
        registerSecurity();
        // Tracing support.
        property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
    }

    private void registerSecurity() {
        register(AuthFilter.class);
        register(RolesAllowedDynamicFeature.class);
    }
    
}
