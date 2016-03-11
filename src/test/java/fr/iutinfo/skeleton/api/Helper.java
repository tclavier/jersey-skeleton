package fr.iutinfo.skeleton.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class Helper {
    final static Logger logger = LoggerFactory.getLogger(Helper.class);
    private static UserDao dao;
    public WebTarget target;


    public Helper(WebTarget target) {
        this.target = target;
        dao = BDDFactory.getDbi().open(UserDao.class);
    }

    void initDb() {
        dao.dropUserTable();
        dao.createUserTable();
    }

    User createUserWithName(String name) {
        User user = new User(0, name);
        return doPost(user);
    }

    User createUserWithAlias(String name, String alias) {
        User user = new User(0, name, alias);
        return doPost(user);
    }


    User createUserWithEmail(String name, String email) {
        User user = new User(0, name);
        user.setEmail(email);
        return doPost(user);
    }

    User createUserWithPassword(String name, String passwd, String salt) {
        User user = new User(0, name);
        user.setSalt(salt);
        user.setPassword(passwd);
        logger.debug("createUserWithPassword Hash : " + user.getPasswdHash());
        return doPost(user);
    }

    User doPost(User user) {
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
        return target.request().post(userEntity).readEntity(User.class);
    }

}
