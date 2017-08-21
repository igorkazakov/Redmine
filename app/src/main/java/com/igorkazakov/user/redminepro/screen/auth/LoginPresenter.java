package com.igorkazakov.user.redminepro.screen.auth;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;
import com.igorkazakov.user.redminepro.utils.TextUtils;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by user on 12.07.17.
 */

public class LoginPresenter {

    private final LifecycleHandler mLifecycleHandler;
    private final LoginView mLoginView;
    private final AuthService mService;

    public LoginPresenter(@NonNull LifecycleHandler lifecycleHandler,
                          @NonNull LoginView loginView, @NonNull AuthService service) {

        mService = service;
        mLifecycleHandler = lifecycleHandler;
        mLoginView = loginView;
    }

    public void init() {

        String login = PreferenceUtils.getInstance().getUserLogin();
        boolean saveCredentials = PreferenceUtils.getInstance().getUserCredentials();
        if (!login.isEmpty() && saveCredentials) {
            mLoginView.openDashboardScreen();
        }
    }

    public void tryLogin(@NonNull String login, @NonNull String password) {

        if (TextUtils.validateEmail(login)) {
            mLoginView.showLoginError();

        } else if (TextUtils.validatePassword(password)) {
            mLoginView.showPasswordError();

        } else {

            mService.login(login, password, mLifecycleHandler, mLoginView);
        }
    }

    public void saveSwitchState(boolean state) {
        PreferenceUtils.getInstance().saveUserCredentials(state);
    }
}
