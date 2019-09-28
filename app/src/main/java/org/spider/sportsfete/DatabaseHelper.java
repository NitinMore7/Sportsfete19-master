package org.spider.sportsfete;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.spider.sportsfete.API.EventDetailsPOJO;

import java.sql.SQLException;

/**
 * Created by dhananjay on 26/1/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "SportsFeteDB";
    private static final int DATABASE_VERSION = 1;
    private Dao<EventDetailsPOJO, Long> eventDetailsDao;
    private ConnectionSource connectionSource;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, EventDetailsPOJO.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, EventDetailsPOJO.class,false);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Dao<EventDetailsPOJO, Long> getEventsDetailDao() throws SQLException {
        if(eventDetailsDao == null) {
            eventDetailsDao = getDao(EventDetailsPOJO.class);
        }
        return eventDetailsDao;
    }

    public ConnectionSource getConnectionSource(){
        if (connectionSource == null) {
            connectionSource = super.getConnectionSource();
        }
        return connectionSource;
    }
}
