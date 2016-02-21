package fr.iutinfo.skeleton.auth;

import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.User;
import fr.iutinfo.skeleton.api.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {
    final static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
        String auth = containerRequest.getHeaderString("authorization");
        logger.debug("auth : " + auth);
        User user = new User(0, "Anonymous");

        if (auth != null) {
            String[] loginPassword = BasicAuth.decode(auth);
            if (loginPassword == null || loginPassword.length != 2) {
                throw new WebApplicationException(Status.NOT_ACCEPTABLE);
            }

            UserDao dao = BDDFactory.getDbi().open(UserDao.class);
            user = dao.findByName(loginPassword[0]);
            if (!user.isGoodPassword(loginPassword[1])) {
                throw new WebApplicationException(Status.UNAUTHORIZED);
            }
        }
        String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
        containerRequest.setSecurityContext(new AppSecurityContext(user, scheme));
    }
}
