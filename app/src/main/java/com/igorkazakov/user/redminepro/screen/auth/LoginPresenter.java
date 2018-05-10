package com.igorkazakov.user.redminepro.screen.auth;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;
import com.igorkazakov.user.redminepro.utils.TextUtils;

/**
 * Created by user on 12.07.17.
 */
@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    public LoginPresenter() {}

    public void init() {

        String login = PreferenceUtils.getInstance().getUserLogin();
        boolean saveCredentials = PreferenceUtils.getInstance().getUserCredentials();
        if (!login.isEmpty() && saveCredentials) {
            getViewState().openDashboardScreen();
        }
    }

    public void tryLogin(@NonNull String login, @NonNull String password) {

        if (TextUtils.validateEmail(login)) {
            getViewState().showLoginError();

        } else if (TextUtils.validatePassword(password)) {
            getViewState().showPasswordError();

        } else {

            RedmineRepository.auth(login, password)
            .doOnSubscribe(getViewState()::showLoading)
            .doOnTerminate(getViewState()::hideLoading)
            .subscribe(loginResponse -> onSuccessLogin(password),
                    Throwable::printStackTrace);
        }
    }

    public void onSuccessLogin(String password) {

        PreferenceUtils.getInstance().saveUserPassword(password);
        getViewState().openDashboardScreen();
    }

    public void saveSwitchState(boolean state) {
        PreferenceUtils.getInstance().saveUserCredentials(state);
    }
}
