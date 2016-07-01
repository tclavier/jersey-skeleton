package fr.iutinfo.skeleton.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Helper {
    private final static Logger logger = LoggerFactory.getLogger(Helper.class);
    private static final UserDao dao = BDDFactory.getDbi().open(UserDao.class);

    public static void initDb() {
        dao.dropUserTable();
        dao.createUserTable();
    }

    public static User createUserWithName(String name) {
        User user = new User(0, name);
        return createUser(user);
    }

    User createUserWithAlias(String name, String alias) {
        User user = new User(0, name, alias);
        return createUser(user);
    }

    User createUserWithEmail(String name, String email) {
        User user = new User(0, name);
        user.setEmail(email);
        return createUser(user);
    }

    public User createUserWithPassword(String name, String passwd, String salt) {
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


}
