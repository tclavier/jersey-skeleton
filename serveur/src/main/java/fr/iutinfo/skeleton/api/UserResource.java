package fr.iutinfo.skeleton.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

import static fr.iutinfo.skeleton.api.BDDFactory.getDbi;
import static fr.iutinfo.skeleton.api.BDDFactory.tableExist;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    final static Logger logger = LoggerFactory.getLogger(UserResource.class);
    private static UserDao dao = getDbi().open(UserDao.class);

    public UserResource() throws SQLException {
        if (!tableExist("users")) {
            logger.debug("Crate table users");
            dao.createUserTable();
            dao.insert(new User(0, "Margaret Thatcher", "la Dame de fer"));
        }
    }

    @POST
    public User createUser(User user) {
        user.resetPasswordHash();
        int id = dao.insert(user);
        user.setId(id);
        return user;
    }

    @GET
    @Path("/{name}")
    public User getUser(@PathParam("name") String name) {
        User user = dao.findByName(name);
        if (user == null) {
            throw new WebApplicationException(404);
        }
        return user;
    }

    @GET
    public List<User> getAllUsers(@QueryParam("q") String query) {
        if (query == null) {
            return dao.all();
        } else {
            logger.debug("Search users with query: " + query);
            return dao.search("%" + query + "%");
        }
    }

    @DELETE
    @Path("/{id}")
    public void deleteUser(@PathParam("id") int id) {
        dao.delete(id);
    }

}
