package fr.iutinfo.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;
import org.skife.jdbi.v2.unstable.BindIn;

import fr.iutinfo.beans.Instruction;

/**
 * Database Access Object relative to the Instructions.
 *  An instruction is an object which define an elemantary computing order
 * Table instructions
 * @author Florent
 */
@UseStringTemplate3StatementLocator
public interface InstructionsDao {

    @SqlUpdate("create table instructions "
            + "(id integer primary key autoincrement, "
            + "name varchar(100), "
            + "code text, "
            + "color integer, "
            + "block integer, "
            + "imageUrl text, "
            + "animationUrl text, "
            + "description text, "
            + "category integer)")
    void createInstructionsTable();

    @SqlUpdate("insert into instructions (name, code, color, block, imageUrl, animationUrl, description, category) "
            + "values (:name, :code, :color, :block, :imageUrl, :animationUrl, :description, :category)")
    @GetGeneratedKeys
    int insert(@Bind("name") String name,
            @Bind("code") String code,
            @Bind("color") int color,
            @Bind("block") int block,
            @Bind("imageUrl") String imageUrl,
            @Bind("animationUrl") String animationUrl,
            @Bind("description") String description,
            @Bind("category") int category);

    @SqlQuery("select * "
            + "from instructions "
            + "where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Instruction findById(@Bind("id") int id);

    @SqlQuery("select * "
            + "from instructions")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Instruction> getAll();

    @SqlQuery("select * "
            + "from instructions "
            + "where id in (<listId>)")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Instruction> getAllId(@BindIn("listId") List<Integer> listId);

    @SqlUpdate("drop table if exists instructions")
    void dropInstructionsTable();

    void close();

}
