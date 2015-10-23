package fr.iutinfo.api;

import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

public class BDDFactory {
    private static DBI dbi = null;

    public static DBI getDbi() {
        if(dbi == null) {
            ;
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:" + System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "data.db");
            dbi = new DBI(ds);
        }
        return dbi;
    }
}