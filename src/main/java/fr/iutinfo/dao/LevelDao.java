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
import fr.iutinfo.beans.NotifLevel;
import fr.iutinfo.beans.NotifLevelCount;

/**
 * Database Access Object relative to the Level
 *  A Level has a String content which define his structure, a type, his
 *   proper instructions, and id and a name
 * Table levels
 * @author Florent
 */
public interface LevelDao {

    @SqlUpdate("create table levels (id integer primary key autoincrement, "
            + "name varchar(100), "
            + "content text, "
            + "instructions text, "
            + "maxInstructions integer, "
            + "authorId integer, "
            + "creationDate DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + "levelType char(10),"
            + "orientation boolean DEFAULT TRUE)")
    void createLevelsTable();

    @SqlUpdate("insert into levels (name, content, instructions, maxInstructions, authorId, levelType, orientation) "
            + "values (:name, :jsonContent, :instructions, :maxInstructions, :authorId, :levelType, :orientation)")
    @GetGeneratedKeys
    int insert(@Bind("name") String name,
            @Bind("jsonContent") String jsonContent,
            @Bind("instructions") String instructions,
            @Bind("maxInstructions") int maxInstructions,
            @Bind("authorId") int authorId,
            @Bind("levelType") String levelType,
            @Bind("orientation") boolean orientation);

    @SqlQuery("select * "
            + "from levels "
            + "where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Level findById(@Bind("id") int id);

    @SqlQuery("select * "
            + "from levels")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Level> getAll();

    @SqlQuery("select * "
            + "from levels "
            + "where authorId = :authorId")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Level> getAllByAuthor(@Bind("authorId") int authorId);

    @SqlQuery("select id, name, authorId,levelType "
            + "from levels")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<LevelInfo> getAllLevelInfo();

    @SqlQuery("select id, name, authorId, levelType "
            + "from levels "
            + "where authorId = :authorId")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<LevelInfo> getLevelInfoByAuthor(@Bind("authorId") int authorId);

    @SqlQuery("select id, name, authorId, levelType "
            + "from levels "
            + "where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
    LevelInfo getLevelInfoById(@Bind("id") int id);

    @SqlQuery("select * "
            + "from levels "
            + "where id = (select idLevel "
            + "from levelListAssociations "
            + "where idList=:idList and position=:idLevel)")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Level getLevelOnList(@Bind("idList") int idList, @Bind("idLevel") int idLevel);

    @SqlQuery("select * "
            + "from levels "
            + "where id in (select idLevel "
            + "from levelListAssociations "
            + "where idList=:idList)")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Level> getLevelsOnList(@Bind("idList") int idList);

    @SqlQuery("select levels.id as levelId, levels.name as levelName, users.id as userId, users.name as userName "
            + "from levels INNER JOIN users "
            + "where users.id = authorId AND creationDate > (select lastNotifChecking "
            + "FROM users "
            + "where id = :userId) AND authorId in (select idFriend "
            + "FROM friendsRelations "
            + "where idUser = :userId);")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<NotifLevel> getNewLevelsFor(@Bind("userId") int userId);

    @SqlQuery("select count(*) as notifCount "
            + "from levels INNER JOIN users "
            + "where users.id = authorId AND creationDate > (select lastNotifChecking "
            + "FROM users "
            + "where id = :userId) AND authorId in (select idFriend "
            + "FROM friendsRelations "
            + "where idUser = :userId);")
    @RegisterMapperFactory(BeanMapperFactory.class)
    NotifLevelCount getNewLevelsCountFor(@Bind("userId") int userId);

    @SqlUpdate("drop table if exists levels")
    void dropLevelsTable();

    void close();
}
