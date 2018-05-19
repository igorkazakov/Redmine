package com.igorkazakov.user.redminepro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 11.07.17.
 */

public class PreferenceUtils {

    private SharedPreferences mSharedPreferences;

    private static final String USER_ID = "USER_ID";
    private static final String USER_LOGIN = "USER_LOGIN";
    private static final String USER_PASSWORD = "USER_PASSWORD";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_MAIL = "USER_MAIL";
    private static final String SAVE_CREDENTIALS = "SAVE_CREDENTIALS";
    private static final String USER_TOKEN = "USER_TOKEN";

    public PreferenceUtils(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void releasePreferenceUtils() {
        cleanUserCredentials();
    }

    public void saveUserId(long value) {
        setValue(USER_ID, value);
    }

    public long getUserId() {
        return getLongValue(USER_ID, 0);
    }

    public void saveUserLogin(String value) {
        setValue(USER_LOGIN, value);
    }

    public String getUserLogin() {
        return getStringValue(USER_LOGIN, "");
    }

    public void saveUserPassword(String value) {
        setValue(USER_PASSWORD, value);
    }

    public String getUserPassword() {
        return getStringValue(USER_PASSWORD, "");
    }

    public void saveAuthToken(String value) {
        setValue(USER_TOKEN, value);
    }

    public String getAuthToken() {
        return getStringValue(USER_TOKEN, "");
    }

    public void saveUserName(String value) {
        setValue(USER_NAME, value);
    }

    public String getUserName() {
        return getStringValue(USER_NAME, "");
    }

    public void saveUserMail(String value) {
        setValue(USER_MAIL, value);
    }

    public String getUserMail() {
        return getStringValue(USER_MAIL, "");
    }

    public void saveUserCredentials(boolean value) {
        setValue(SAVE_CREDENTIALS, value);
    }

    public boolean getUserCredentials() {
        return getBooleanValue(SAVE_CREDENTIALS, false);
    }

    public void cleanUserCredentials() {

        if (!getUserCredentials()) {
            saveUserMail("");
            saveUserName("");
            saveUserId(-1);
            saveUserPassword("");
            saveAuthToken("");
            saveUserLogin("");
        }
    }

    private void setValue(String key, String value){
        SharedPreferences.Editor ed = mSharedPreferences.edit();
        ed.putString(key, value);
        ed.commit();
    }

    private void setValue(String key, boolean value){
        SharedPreferences.Editor ed = mSharedPreferences.edit();
        ed.putBoolean(key, value);
        ed.commit();
    }

    private void setValue(String key, int value){
        SharedPreferences.Editor ed = mSharedPreferences.edit();
        ed.putInt(key, value);
        ed.commit();
    }

    private void setValue(String key, long value){
        SharedPreferences.Editor ed = mSharedPreferences.edit();
        ed.putLong(key, value);
        ed.commit();
    }

    private boolean getBooleanValue(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    private String getStringValue(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    private int getIntValue(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    private long getLongValue(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    private float getFloatValue(String key, float defValue) {
        return mSharedPreferences.getFloat(key, defValue);
    }
}
