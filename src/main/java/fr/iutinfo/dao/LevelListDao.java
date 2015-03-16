package fr.iutinfo.dao;
	
import java.util.List;
	
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;
	
import fr.iutinfo.beans.LevelList;
import fr.iutinfo.beans.LevelListAssociation;
	
public interface LevelListDao {
	
	@SqlUpdate("create table levelLists (id integer primary key autoincrement, name varchar(100), idAuthor integer)")
	void createLevelListsTable();
	
	@SqlUpdate("create table levelListAssociations (idList integer, idLevel integer, position integer, CONSTRAINT pk_association PRIMARY KEY (idList, idLevel))")
	void createLevelListAssociationsTable();
	
	@SqlUpdate("insert into levelLists (name, idAuthor) values (:name, :idAuthor)")
	@GetGeneratedKeys
	int createList(@Bind("name") String name, @Bind("idAuthor") int idAuthor);
	
	@SqlUpdate("insert into levelListAssociations (idList, idLevel, position) values (:idList, :idLevel, :position)")
	void insertAssociation(@Bind("idList") int idList, @Bind("idLevel") int idLevel, @Bind("position") int position);
	
	@SqlQuery("select * from levelLists where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
	LevelList findById(@Bind("id") int id);
	
	@SqlQuery("select * from levelLists where idAuthor = :idAuthor")
    @RegisterMapperFactory(BeanMapperFactory.class)
	List<LevelList> findByIdAuthor(@Bind("idAuthor") int idAuthor);
	
	@SqlQuery("select * from levelListAssociations where idList = :idList ORDER BY position")
    @RegisterMapperFactory(BeanMapperFactory.class)
	List<LevelListAssociation> getAssociationsOf(@Bind("idList") int idList);
	
	@SqlQuery("select * from levelLists")
    @RegisterMapperFactory(BeanMapperFactory.class)
	List<LevelList> getAllLevelLists();
	
	@SqlQuery("SELECT count(*) as levelCount, id, idLevel, name from levelListAssociations INNER JOIN levelLists WHERE id=idList GROUP BY idList;")
    @RegisterMapperFactory(BeanMapperFactory.class)
	List<LevelList> getAllLevelListWithCount();
	
	@SqlQuery("select * from levelLists where id = (select DISTINCT idList from levelListAssociations where idLevel = :idLevel)")
    @RegisterMapperFactory(BeanMapperFactory.class)
	LevelList getLevelListByLevelId(@Bind("idLevel") int idLevel);
	
	@SqlQuery("SELECT max(position) + 1 from levelListAssociations where idList=:idList")
    @RegisterMapperFactory(BeanMapperFactory.class)
	int getNextPosition(@Bind("idList") int idList);
	
	@SqlUpdate("drop table if exists levelLists")
	void dropLevelListsTable(); 
	
	@SqlUpdate("drop table if exists levelListAssociations")
	void dropLevelListAssociationsTable(); 
	
	void close();
}	
