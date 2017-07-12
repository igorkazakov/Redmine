package com.igorkazakov.user.redminepro.application;

import android.app.Application;

import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

/**
 * Created by user on 11.07.17.
 */

public class RedmineApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtils.createPreferenceUtils(this);
        DatabaseManager.createDatabaseHelper(this);
    }

    @Override
    public void onTerminate() {
        PreferenceUtils.releasePreferenceUtils();
        DatabaseManager.releaseDatabaseHelper();
        super.onTerminate();
    }
}