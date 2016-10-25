package fr.iutinfo.skeleton.common.remote;

import fr.iutinfo.skeleton.common.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class UsersProvider {

    final static Logger logger = LoggerFactory.getLogger(UsersProvider.class);
    private String baseUrl;

    public UsersProvider(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    public List<UserDto> readAllUsers() {
        try {
            return ClientBuilder.newClient()//
                    .target(baseUrl + "user/")
                    .request()
                    .get(new GenericType<List<UserDto>>() {
                    });
        } catch (Exception e) {
            String message = ClientBuilder.newClient()
                    .target(baseUrl + "user/")
                    .request()
                    .get(String.class);

            logger.error(e.getMessage());
            throw new RuntimeException(message);
        }
    }

    public UserDto addUser(UserDto user) {
        logger.debug("Create user : " + user.getName());
        Entity<UserDto> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        return ClientBuilder.newClient()
                .target(baseUrl + "user/")
                .request()
                .post(userEntity)
                .readEntity(UserDto.class);
    }

    public UserDto readUser(String name) {
        String url = baseUrl + "user/" + name;
        logger.debug("Reade url : " + url);

        return ClientBuilder.newClient()//
                .target(url)
                .request()
                .get(UserDto.class);
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
