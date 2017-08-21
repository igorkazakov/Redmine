package com.igorkazakov.user.redminepro.screen.auth;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by Igor on 21.08.2017.
 */

public interface AuthService {

    void login(@NonNull String login, @NonNull String password,
               LifecycleHandler lifecycleHandler, LoginView loginView);
}
