package com.igorkazakov.user.redminepro.utils;

/**
 * Created by user on 17.07.17.
 */

public class NumberUtils {

    public static float round(float value) {
        value = value *100;
        value = Math.round(value);
        value = value / 100;
        return value;
    }
}
