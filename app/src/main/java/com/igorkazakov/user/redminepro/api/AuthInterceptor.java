package com.igorkazakov.user.redminepro.api;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user on 13.07.17.
 */

public class AuthInterceptor implements Interceptor{

    @Inject
    PreferenceUtils mPreferenceUtils;

    private AuthInterceptor() {
        RedmineApplication.getComponent().inject(this);
    }

    @NonNull
    public static Interceptor create() {
        return new AuthInterceptor();
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        String authString = mPreferenceUtils.getAuthToken();

        if (authString.isEmpty()) {
            return chain.proceed(chain.request());
        }

        Request request = chain.request().newBuilder()
                .addHeader("Authorization", authString)
                .addHeader("Content-Type", ContentType.JSON.getValue())
                .build();

        return chain.proceed(request);
    }
}
