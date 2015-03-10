package fr.iutinfo.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import fr.iutinfo.beans.Level;
import fr.iutinfo.beans.LevelInfo;

public interface LevelDao {

	@SqlUpdate("create table levels (id integer primary key autoincrement, name varchar(100), content text, instructions text, maxInstructions integer, authorId integer)")
	void createLevelsTable();

	@SqlUpdate("insert into levels (name, content, instructions, maxInstructions, authorId) "
			+ "values (:name, :jsonContent, :instructions, :maxInstructions, :authorId)")
	@GetGeneratedKeys
	int insert(@Bind("name") String name, 
			@Bind("jsonContent") String jsonContent, 
			@Bind("instructions") String instructions, 
			@Bind("maxInstructions") int maxInstructions, 
			@Bind("authorId") int authorId);

	@SqlQuery("select * from levels where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
	Level findById(@Bind("id") int id);
	
	@SqlQuery("select * from levels")
    @RegisterMapperFactory(BeanMapperFactory.class)
	List<Level> getAll();
	
	@SqlQuery("select * from levels where authorId = :authorId")
    @RegisterMapperFactory(BeanMapperFactory.class)
	List<Level> getAllByAuthor(@Bind("authorId") int authorId);

	@SqlQuery("select id, name, authorId from levels")
    @RegisterMapperFactory(BeanMapperFactory.class)
	List<LevelInfo> getAllLevelInfo();
	
	@SqlQuery("select id, name, authorId from levels where authorId = :authorId")
    @RegisterMapperFactory(BeanMapperFactory.class)
	List<LevelInfo> getLevelInfoByAuthor(@Bind("authorId") int authorId);
	
	@SqlQuery("select id, name, authorId from levels where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
	LevelInfo getLevelInfoById(@Bind("id") int id);
	
	/*@SqlUpdate("update levels set nextLevelId=:nextLevelId where id=:id")
	void setNextLevel(@Bind("nextLevelId") int nextLevelId, @Bind("id") int id);*/
	
	@SqlUpdate("drop table if exists levels")
	void dropLevelsTable(); 
	
	void close();
	
	
}
