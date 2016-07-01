package fr.iutinfo.skeleton.cli;

import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class RestClient {

    final static Logger logger = LoggerFactory.getLogger(RestClient.class);

    public String getUrlAsString(String url) {
        return ClientBuilder.newClient()//
                .target(url)
                .request()
                .get(String.class);
    }

    public List<User> getUrlAsUser(String url) {
        return ClientBuilder.newClient()//
                .target(url)
                .request()
                .get(new GenericType<List<User>>() {
                });
    }

    public User addUser(User user, String url) {
        logger.debug("Create user : " + user.getName() + " on : " + url);
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        return ClientBuilder.newClient()
                .target(url)
                .request()
                .post(userEntity)
                .readEntity(User.class);
    }
}
