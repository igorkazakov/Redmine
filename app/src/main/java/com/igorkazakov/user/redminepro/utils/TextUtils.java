package com.igorkazakov.user.redminepro.utils;

import android.support.annotation.NonNull;

import java.util.regex.Pattern;

/**
 * Created by user on 12.07.17.
 */

public class TextUtils {

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean validateEmail(@NonNull String value) {
        return EMAIL_ADDRESS_PATTERN.matcher(value).matches();
    }

    public static boolean validatePassword(@NonNull String value) {
        return value.isEmpty();
    }
}
