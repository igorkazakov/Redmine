package com.igorkazakov.user.redminepro.screen.auth;

import com.igorkazakov.user.redminepro.api.response.LoginResponse;

import io.reactivex.Observable;

public interface LoginServiceInterface {

    String getUserLogin();
    boolean getUserCredentials();
    Observable<LoginResponse> auth(String login, String password);
    void saveUserPassword(String password);
    void saveUserCredentials(boolean state);
}
