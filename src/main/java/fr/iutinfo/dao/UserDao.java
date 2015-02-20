package fr.iutinfo.dao;


import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import fr.iutinfo.bins.User;

public interface UserDao {
	@SqlUpdate("create table users (id integer primary key autoincrement, name varchar(100))")
	void createUserTable();

	@SqlUpdate("insert into users (name) values (:name)")
	@GetGeneratedKeys
	int insert(@Bind("name") String name);

	@SqlQuery("select * from users where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
	User findById(@Bind("id") int id);

	@SqlUpdate("drop table if exists users")
	void dropUserTable(); 
	
	void close();

	@SqlQuery("select * from users")
    @RegisterMapperFactory(BeanMapperFactory.class)
	List<User> getAll();
}
