package com.igorkazakov.user.redminepro.screen.auth;

import com.igorkazakov.user.redminepro.screen.general.LoadingView;

/**
 * Created by user on 12.07.17.
 */

public interface LoginView extends LoadingView {

    void showLoginError();
    void showPasswordError();
    void openDashboardScreen();
}
