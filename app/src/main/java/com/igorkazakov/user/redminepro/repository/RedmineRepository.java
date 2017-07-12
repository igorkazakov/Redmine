package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.ContentType;
import com.igorkazakov.user.redminepro.api.response.LoginResponse;
import com.igorkazakov.user.redminepro.utils.AuthorizationUtils;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user on 11.07.17.
 */

public class RedmineRepository {

    @NonNull
    public static Observable<LoginResponse> auth(@NonNull String login, @NonNull String password) {

        String authString = AuthorizationUtils.createAuthorizationString(login, password);
        return ApiFactory.getRedmineService()
                .login(authString, ContentType.JSON.getValue())
                .flatMap(loginResponse -> {

                    PreferenceUtils.getInstance().saveUserId(loginResponse.getUser().getId());
                    PreferenceUtils.getInstance().saveUserLogin(loginResponse.getUser().getLogin());
                    PreferenceUtils.getInstance().saveUserName(loginResponse.getUser().getFirstName() + " " + loginResponse.getUser().getLastName());
                    PreferenceUtils.getInstance().saveUserMail(loginResponse.getUser().getMail());
                    ApiFactory.recreate();
                    return Observable.just(loginResponse);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
