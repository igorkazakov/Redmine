package com.igorkazakov.user.redminepro.di.module;

import android.content.Context;

import com.igorkazakov.user.redminepro.application.RedmineApplication;
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
    Context provideContext() {
        return mApplication;
    }

    @Provides
    RedmineApplication provideApplication() {
        return mApplication;
    }

    @Provides
    PreferenceUtils provideSharedPrefs(Context context) {
        return new PreferenceUtils(context);
    }
}
