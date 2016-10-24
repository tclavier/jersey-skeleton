package fr.iutinfo.skeleton.api;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteDataSource;

import javax.inject.Singleton;

@Singleton
public class BDDFactory {
    private static DBI dbi = null;
    final static Logger logger = LoggerFactory.getLogger(BDDFactory.class);

    public static DBI getDbi() {
        if(dbi == null) {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:" + System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "data.db");
            dbi = new DBI(ds);
            logger.debug("user.dir : " + System.getProperty("user.dir"));
            logger.debug("java.io.tmpdir : " + System.getProperty("java.io.tmpdir"));
        }
        return dbi;
    }
}