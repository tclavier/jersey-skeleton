package fr.iutinfo.web;


import fr.iutinfo.api.BDDFactory;
import fr.iutinfo.api.*;
import org.glassfish.jersey.server.mvc.Template;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("user")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
public class UserViews {
    private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);

    @GET
    @Template
    public List<User> getAll() {
        dao.insert("thomas");
        dao.insert("yann");
        return dao.all();
    }

    @GET
    @Template(name = "detail")
    @Path("{id}")
    public User getDetail(@PathParam("id") String id) {
        User user = dao.findById(Integer.parseInt(id));
        if (user == null) {
            throw new WebApplicationException(404);
        }
        return user;
    }

}

