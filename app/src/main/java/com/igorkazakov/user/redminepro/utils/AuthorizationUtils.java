package com.igorkazakov.user.redminepro.utils;

import android.support.annotation.NonNull;
import android.util.Base64;

/**
 * Created by user on 11.07.17.
 */

public final class AuthorizationUtils {

    private static final String BASIC_AUTHORIZATION = "Basic ";
    private static AuthorizationUtils sInstanse;

    public static AuthorizationUtils getInstanse() {
        if (sInstanse == null) {
            sInstanse = new AuthorizationUtils();
        }

        return sInstanse;
    }

    private AuthorizationUtils() {}

    @NonNull
    public String createAuthorizationString(@NonNull String login, @NonNull String password) {

        String str = String.format("%1$s:%2$s", login, password);
        String authString = BASIC_AUTHORIZATION + Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
        return authString.trim();
    }
}
