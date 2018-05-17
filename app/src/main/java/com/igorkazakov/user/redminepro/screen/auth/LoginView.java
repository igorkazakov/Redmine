package com.igorkazakov.user.redminepro.screen.auth;

import com.arellomobile.mvp.MvpView;
import com.igorkazakov.user.redminepro.screen.base.ProgressInterface;
import com.igorkazakov.user.redminepro.screen.base.ErrorInterface;

/**
 * Created by user on 12.07.17.
 */

public interface LoginView extends MvpView , ProgressInterface, ErrorInterface {

    void showLoginError();
    void showPasswordError();
    void openDashboardScreen();
}
