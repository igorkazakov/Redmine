package com.igorkazakov.user.redminepro.di.module;

import com.igorkazakov.user.redminepro.repository.OggyRepository;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    RedmineRepository provideRedmineRepository() {
        return new RedmineRepository();
    }

    @Provides
    @Singleton
    OggyRepository provideOggyRepository() {
        return new OggyRepository();
    }
}
