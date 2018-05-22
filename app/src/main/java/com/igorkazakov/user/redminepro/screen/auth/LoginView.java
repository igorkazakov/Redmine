package com.igorkazakov.user.redminepro.screen.auth;

import com.igorkazakov.user.redminepro.screen.base.ErrorInterface;
import com.igorkazakov.user.redminepro.screen.base.ProgressInterface;

/**
 * Created by user on 12.07.17.
 */

public interface LoginView extends ProgressInterface, ErrorInterface {

    void showLoginError();
    void showPasswordError();
    void openDashboardScreen();
}
