package com.igorkazakov.user.redminepro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 11.07.17.
 */

public class PreferenceUtils {

    private SharedPreferences mSharedPreferences;

    private static PreferenceUtils sPreferenceUtils;

    private static final String USER_ID = "USER_ID";
    private static final String USER_LOGIN = "USER_LOGIN";
    private static final String USER_PASSWORD = "USER_PASSWORD";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_MAIL = "USER_MAIL";
    private static final String SAVE_CREDENTIALS = "SAVE_CREDENTIALS";
    private static final String USER_TOKEN = "USER_TOKEN";

    public PreferenceUtils() {}

    public static void createPreferenceUtils(Context context) {
        sPreferenceUtils = new PreferenceUtils();
        sPreferenceUtils.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void releasePreferenceUtils() {
        sPreferenceUtils.cleanUserCredentials();
        sPreferenceUtils.mSharedPreferences = null;
        sPreferenceUtils = null;
    }

    public static PreferenceUtils getInstance() {
        return sPreferenceUtils;
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

        if (!sPreferenceUtils.getUserCredentials()) {
            sPreferenceUtils.saveUserMail("");
            sPreferenceUtils.saveUserName("");
            sPreferenceUtils.saveUserId(-1);
            sPreferenceUtils.saveUserPassword("");
            sPreferenceUtils.saveAuthToken("");
            sPreferenceUtils.saveUserLogin("");
        }
    }

    private void setValue(String key, String value){
        SharedPreferences.Editor ed = sPreferenceUtils.mSharedPreferences.edit();
        ed.putString(key, value);
        ed.commit();
    }

    private void setValue(String key, boolean value){
        SharedPreferences.Editor ed = sPreferenceUtils.mSharedPreferences.edit();
        ed.putBoolean(key, value);
        ed.commit();
    }

    private void setValue(String key, int value){
        SharedPreferences.Editor ed = sPreferenceUtils.mSharedPreferences.edit();
        ed.putInt(key, value);
        ed.commit();
    }

    private void setValue(String key, long value){
        SharedPreferences.Editor ed = sPreferenceUtils.mSharedPreferences.edit();
        ed.putLong(key, value);
        ed.commit();
    }

    private boolean getBooleanValue(String key, boolean defValue) {
        return sPreferenceUtils.mSharedPreferences.getBoolean(key, defValue);
    }

    private String getStringValue(String key, String defValue) {
        return sPreferenceUtils.mSharedPreferences.getString(key, defValue);
    }

    private int getIntValue(String key, int defValue) {
        return sPreferenceUtils.mSharedPreferences.getInt(key, defValue);
    }

    private long getLongValue(String key, long defValue) {
        return sPreferenceUtils.mSharedPreferences.getLong(key, defValue);
    }

    private float getFloatValue(String key, float defValue) {
        return sPreferenceUtils.mSharedPreferences.getFloat(key, defValue);
    }
}
