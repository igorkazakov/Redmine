package com.igorkazakov.user.redminepro.api;

public class ApiException extends Exception {

    private String mMessage;
    private long mCode;

    public ApiException(String message, long code) {
        mMessage = message;
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public long getCode() {
        return mCode;
    }
}
