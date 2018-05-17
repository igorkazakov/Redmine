package com.igorkazakov.user.redminepro.di.module;

import com.igorkazakov.user.redminepro.utils.AuthorizationUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthModule {

    @Singleton
    @Provides
    AuthorizationUtils provideAuthorizationUtils() {
        return new AuthorizationUtils();
    }
}
