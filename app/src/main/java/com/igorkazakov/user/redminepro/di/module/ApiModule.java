package com.igorkazakov.user.redminepro.di.module;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.OggyApi;
import com.igorkazakov.user.redminepro.api.RedmineApi;
import com.igorkazakov.user.redminepro.api.rxoperator.error.ApiErrorOperator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public RedmineApi provideRedmineApi() {
        return ApiFactory.getRedmineService();
    }

    @Provides
    @Singleton
    public OggyApi provideOggyApi() {
        return ApiFactory.getOggyService();
    }

    @Provides
    @Singleton
    public ApiErrorOperator provideApiErrorTransformer() {

        return new ApiErrorOperator<>();
    }
}
