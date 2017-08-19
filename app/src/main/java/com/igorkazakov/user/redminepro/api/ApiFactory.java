package com.igorkazakov.user.redminepro.api;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 11.07.17.
 */

public final class ApiFactory {

    private static OkHttpClient sClient;
    private static volatile RedmineService sService;
    private static volatile OggyService sOggyService;

    private ApiFactory() {}

    @NonNull
    public static RedmineService getRedmineService() {

        RedmineService service = sService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit(BuildConfig.REDMINE_API_ENDPOINT).create(RedmineService.class);
                }
            }
        }

        return service;
    }

    @NonNull
    public static OggyService getOggyService() {

        OggyService service = sOggyService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sOggyService;
                if (service == null) {
                    service = sOggyService = buildRetrofit(BuildConfig.OGGY_API_ENDPOINT).create(OggyService.class);
                }
            }
        }

        return service;
    }

    @NonNull
    public static void recreate() {
        sClient = null;
        sClient = getClient();
        sService = buildRetrofit(BuildConfig.REDMINE_API_ENDPOINT).create(RedmineService.class);
    }

    @NonNull
    private static Retrofit buildRetrofit(String baseUrl) {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @NonNull
    public static OkHttpClient getClient() {

        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }

        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {

        return new OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor.create())
                .addInterceptor(AuthInterceptor.create())
                .build();
    }
}
