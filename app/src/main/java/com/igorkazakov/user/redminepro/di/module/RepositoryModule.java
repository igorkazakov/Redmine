package com.igorkazakov.user.redminepro.di.module;

import com.igorkazakov.user.redminepro.repository.Repository;
import com.igorkazakov.user.redminepro.repository.RepositoryInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public RepositoryInterface provideRepository() {
        return Repository.getInstance();
    }
}
