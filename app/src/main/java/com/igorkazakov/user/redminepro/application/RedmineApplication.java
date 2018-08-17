package com.igorkazakov.user.redminepro.application;

import android.support.multidex.MultiDexApplication;

import com.igorkazakov.user.redminepro.di.component.ApplicationComponent;
import com.igorkazakov.user.redminepro.di.component.DaggerApplicationComponent;
import com.igorkazakov.user.redminepro.di.module.ApplicationModule;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by user on 11.07.17.
 */

public class RedmineApplication extends MultiDexApplication {

    @Inject
    PreferenceUtils mPreferenceUtils;

    private static ApplicationComponent sComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        sComponent.inject(this);

        Realm.init(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    @Override
    public void onTerminate() {
        mPreferenceUtils.releasePreferenceUtils();
        super.onTerminate();
    }

    public static ApplicationComponent getComponent() {
        return sComponent;
    }
}
