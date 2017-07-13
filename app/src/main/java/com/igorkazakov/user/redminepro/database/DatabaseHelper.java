package com.igorkazakov.user.redminepro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.igorkazakov.user.redminepro.database.dao.TimeEntryDAO;
import com.igorkazakov.user.redminepro.database.entity.TimeEntryEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by user on 11.07.17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "local.db";
    private static final int DATABASE_VERSION = 1;

    private TimeEntryDAO mTimeEntryDAO;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TimeEntryEntity.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, TimeEntryEntity.class, true);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TimeEntryDAO getTimeEntryDAO() {

        if (mTimeEntryDAO == null) {
            try {
                mTimeEntryDAO = new TimeEntryDAO(getConnectionSource(), TimeEntryEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return mTimeEntryDAO;
    }

    @Override
    public void close() {
        super.close();
    }
}
