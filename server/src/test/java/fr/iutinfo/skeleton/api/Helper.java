package fr.iutinfo.skeleton.api;

import fr.iutinfo.skeleton.common.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.GenericType;
import java.util.List;

import static fr.iutinfo.skeleton.api.BDDFactory.getDbi;

public class Helper {
    private final static Logger logger = LoggerFactory.getLogger(Helper.class);
    private static final UserDao dao = getDbi().onDemand(UserDao.class);
    static GenericType<List<UserDto>> listUserResponseType = new GenericType<List<UserDto>>() {
    };

    public static void initDb() {
        dao.dropUserTable();
        dao.createUserTable();
    }

    static User createUserWithName(String name) {
        User user = new User(0, name);
        return createUser(user);
    }

    public static User createUserWithPassword(String name, String passwd, String salt) {
        User user = new User(0, name);
        user.setSalt(salt);
        user.setPassword(passwd);
        logger.debug("createUserWithPassword Hash : " + user.getPasswdHash());
        return createUser(user);
    }

    private static User createUser(User user) {
        int id = dao.insert(user);
        user.setId(id);
        return user;
    }


    private static User createFullUSer(String name, String alias, String email, String paswword) {
        User user = new User(0, name);
        user.setAlias(alias);
        user.setEmail(email);
        user.setPassword(paswword);
        int id = dao.insert(user);
        user.setId(id);
        return user;
    }

    static void rms() {
        createFullUSer("Richard Stallman", "RMS", "rms@fsf.org", "gnuPaswword");
    }

    static User rob() {
        return createFullUSer("Robert Capillo", "rob", "rob@fsf.org", "paswword");
    }

    static User linus() {
        return createFullUSer("Linus Torvalds", "linus", "linus@linux.org", "paswword");
    }

    static User ian() {
        return createFullUSer("Ian Murdock", "debian", "ian@debian.org", "mot de passe");
    }
}
