package com.igorkazakov.user.redminepro.screen.auth;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.utils.TextUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by user on 12.07.17.
 */
@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> implements LifecycleObserver {

    private LoginServiceInterface mRepository;
    private Disposable mDisposable;

    public LoginPresenter(LoginServiceInterface repository) {

        mRepository = repository;
    }

    public void init() {

        String login = mRepository.getUserLogin();
        boolean saveCredentials = mRepository.getUserCredentials();
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

            mDisposable = mRepository.auth(login, password)
                    .doOnSubscribe(__ -> getViewState().showLoading())
                    .doOnTerminate(getViewState()::hideLoading)
                    .subscribe(loginResponse -> onSuccessLogin(password),
                            throwable -> {
                                ApiException exception = (ApiException) throwable;
                                getViewState().showError(exception);
                            });
        }
    }

    public void onSuccessLogin(String password) {

        mRepository.saveUserPassword(password);
        getViewState().openDashboardScreen();
    }

    public void saveSwitchState(boolean state) {
        mRepository.saveUserCredentials(state);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onActivityDestroy() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
