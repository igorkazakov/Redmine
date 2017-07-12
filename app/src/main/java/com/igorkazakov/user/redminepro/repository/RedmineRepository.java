package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.ContentType;
import com.igorkazakov.user.redminepro.api.responseEntity.User;
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
    public Observable<User> auth(@NonNull String login, @NonNull String password) {

        String authString = AuthorizationUtils.createAuthorizationString(login, password);
        return ApiFactory.getRedmineService()
                .login(authString, ContentType.JSON.getValue())
                .flatMap(user -> {

                    PreferenceUtils.getInstance().saveUserId(user.getId());
                    PreferenceUtils.getInstance().saveUserLogin(user.getLogin());
                    PreferenceUtils.getInstance().saveUserName(user.getFirstName() + " " + user.getLastName());
                    PreferenceUtils.getInstance().saveUserMail(user.getMail());
                    ApiFactory.recreate();
                    return Observable.just(user);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
