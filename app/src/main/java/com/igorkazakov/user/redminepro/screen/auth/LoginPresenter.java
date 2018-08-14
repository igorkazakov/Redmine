package com.igorkazakov.user.redminepro.screen.auth;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.repository.RepositoryInterface;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;
import com.igorkazakov.user.redminepro.utils.TextUtils;

/**
 * Created by user on 12.07.17.
 */
@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {

    private RepositoryInterface mRepository;
    private PreferenceUtils mPreferenceUtils;

    public LoginPresenter(RepositoryInterface repository,
                          PreferenceUtils preferenceUtils) {

        mPreferenceUtils = preferenceUtils;
        mRepository = repository;
    }

    public void init() {

        String login = mPreferenceUtils.getUserLogin();
        boolean saveCredentials = mPreferenceUtils.getUserCredentials();
        if (!login.isEmpty() && saveCredentials) {
            getViewState().openDashboardScreen();
        }
    }

    public void tryLogin(@NonNull String login, @NonNull String password) {

        if (login.isEmpty()) {
            getViewState().showLoginError();

        } else if (TextUtils.validatePassword(password)) {
            getViewState().showPasswordError();

        } else {

            mRepository.auth(login, password)
            .doOnSubscribe(__ -> getViewState().showLoading())
            .doOnTerminate(getViewState()::hideLoading)
            .subscribe(loginResponse -> onSuccessLogin(password),
                    throwable -> {
                        ApiException exception = (ApiException)throwable;
                        getViewState().showError(exception);
                    });
        }
    }

    public void onSuccessLogin(String password) {

        mPreferenceUtils.saveUserPassword(password);
        getViewState().openDashboardScreen();
    }

    public void saveSwitchState(boolean state) {
        mPreferenceUtils.saveUserCredentials(state);
    }
}
