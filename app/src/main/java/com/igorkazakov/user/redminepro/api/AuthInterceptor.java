package com.igorkazakov.user.redminepro.api;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.utils.AuthorizationUtils;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user on 13.07.17.
 */

public class AuthInterceptor implements Interceptor{

    private AuthInterceptor() {}

    @NonNull
    public static Interceptor create() {
        return new AuthInterceptor();
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        String login = PreferenceUtils.getInstance().getUserLogin();
        String password = PreferenceUtils.getInstance().getUserPassword();
        String authString = AuthorizationUtils.getInstanse().createAuthorizationString(login, password);

        if (login.isEmpty() || password.isEmpty()) {
            return chain.proceed(chain.request());
        }

        Request request = chain.request().newBuilder()
                .addHeader("Authorization", authString)
                .addHeader("Content-Type", ContentType.JSON.getValue())
                .build();

        return chain.proceed(request);
    }
}
