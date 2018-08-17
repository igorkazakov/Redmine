package com.igorkazakov.user.redminepro.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.database.room.database.RoomDbHelper;
import com.igorkazakov.user.redminepro.utils.DialogUtils;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final RedmineApplication mApplication;

    public ApplicationModule(RedmineApplication app) {
        mApplication = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApplication;
    }

    @Provides
    public RedmineApplication provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    public PreferenceUtils provideSharedPrefs(Context context) {
        return new PreferenceUtils(context);
    }

    @Provides
    @Singleton
    public DialogUtils provideDialogUtils() {
        return new DialogUtils();
    }

    @Provides
    @Singleton
    public RoomDbHelper provideRoomDb(Context context) {
        return Room.databaseBuilder(context,
                RoomDbHelper.class, "database").build();
    }
}
