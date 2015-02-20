package fr.iutinfo.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import fr.iutinfo.bins.Level;

public interface LevelDao {

	@SqlUpdate("create table levels (id integer primary key autoincrement, name varchar(100), content text, author text)")
	void createLevelsTable();

	@SqlUpdate("insert into levels (name, content, author) values (:name, :jsonContent, :author)")
	@GetGeneratedKeys
	int insert(@Bind("name") String name, @Bind("jsonContent") String jsonContent, @Bind("author") String author);

	@SqlQuery("select * from levels where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
	Level findById(@Bind("id") int id);
	
	@SqlQuery("select * from levels")
    @RegisterMapperFactory(BeanMapperFactory.class)
	List<Level> getAll();

	@SqlUpdate("drop table if exists levels")
	void dropLevelsTable(); 
	
	void close();
	
	
}
