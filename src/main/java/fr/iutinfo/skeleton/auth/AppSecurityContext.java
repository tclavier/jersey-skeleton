package fr.iutinfo.skeleton.auth;

import fr.iutinfo.skeleton.api.User;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class AppSecurityContext implements SecurityContext {
    private User user;
    private String scheme;

    public AppSecurityContext(User user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.user;
    }

    @Override
    public boolean isUserInRole(String s) {
        return true;
    }

    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme);
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
