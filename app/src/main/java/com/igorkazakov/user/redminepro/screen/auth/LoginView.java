package com.igorkazakov.user.redminepro.screen.auth;

import com.igorkazakov.user.redminepro.screen.base.BaseViewInterface;

/**
 * Created by user on 12.07.17.
 */

public interface LoginView extends BaseViewInterface {

    void showLoginError();
    void showPasswordError();
    void openDashboardScreen();
}
