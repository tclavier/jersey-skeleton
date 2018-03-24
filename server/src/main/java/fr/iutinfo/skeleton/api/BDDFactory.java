package fr.iutinfo.skeleton.api;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteDataSource;

import javax.inject.Singleton;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Singleton
public class BDDFactory {
    private static Jdbi dbi = null;
    final static Logger logger = LoggerFactory.getLogger(BDDFactory.class);

    public static Jdbi getDbi() {
        if(dbi == null) {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:" + System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "data.db");
            dbi = Jdbi.create(ds);
            dbi.installPlugin(new SqlObjectPlugin());
            logger.debug("user.dir : " + System.getProperty("user.dir"));
            logger.debug("java.io.tmpdir : " + System.getProperty("java.io.tmpdir"));
        }
        return dbi;
    }

    static boolean tableExist(String tableName) throws SQLException {
        DatabaseMetaData dbm = getDbi().open().getConnection().getMetaData();
        ResultSet tables = dbm.getTables(null, null, tableName, null);
        boolean exist = tables.next();
        tables.close();
        return exist;
    }
}