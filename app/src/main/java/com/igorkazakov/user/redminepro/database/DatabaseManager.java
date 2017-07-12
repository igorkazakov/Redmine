package com.igorkazakov.user.redminepro.database;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by user on 11.07.17.
 */

public class DatabaseManager {

    public static volatile DatabaseHelper sDatabaseHelper;

    public static DatabaseHelper getDatabaseHelper() {
        return sDatabaseHelper;
    }

    public static void createDatabaseHelper(Context context) {
        sDatabaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void releaseDatabaseHelper() {
        OpenHelperManager.releaseHelper();
        sDatabaseHelper = null;
    }
}
