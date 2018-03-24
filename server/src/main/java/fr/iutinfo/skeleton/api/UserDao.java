package fr.iutinfo.skeleton.api;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface UserDao {
    @SqlUpdate("create table users (id integer primary key autoincrement, name varchar(100), alias varchar(100), email varchar(100), passwdHash varchar(64), salt varchar(64), search varchar(1024))")
    void createUserTable();

    @SqlUpdate("insert into users (name,alias,email, passwdHash, salt, search) values (:name, :alias, :email, :passwdHash, :salt, :search)")
    @GetGeneratedKeys
    int insert(@BindBean() User user);

    @SqlQuery("select * from users where name = :name")
    @RegisterBeanMapper(User.class)
    User findByName(@Bind("name") String name);

    @SqlQuery("select * from users where search like :name")
    @RegisterBeanMapper(User.class)
    List<User> search(@Bind("name") String name);

    @SqlUpdate("drop table if exists users")
    void dropUserTable();

    @SqlUpdate("delete from users where id = :id")
    void delete(@Bind("id") int id);

    @SqlQuery("select * from users order by id")
    @RegisterBeanMapper(User.class)
    List<User> all();

    @SqlQuery("select * from users where id = :id")
    @RegisterBeanMapper(User.class)
    User findById(@Bind("id") int id);

}
