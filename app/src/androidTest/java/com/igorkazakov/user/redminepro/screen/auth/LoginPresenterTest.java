package com.igorkazakov.user.redminepro.screen.auth;



import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.api.response.LoginResponse;
import com.igorkazakov.user.redminepro.repository.RedmineService;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    LoginPresenter loginPresenter;

    @Mock
    LoginView loginView;

    @Mock
    RedmineService repository;

    @Mock
    PreferenceUtils preferenceUtils;

    public LoginPresenterTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        loginPresenter = new LoginPresenter(repository, preferenceUtils);
        loginPresenter.attachView(loginView);
    }

    @Test
    public void showDashboardScreen() {

        when(preferenceUtils.getUserLogin()).thenReturn("login");
        when(preferenceUtils.getUserCredentials()).thenReturn(true);
        loginPresenter.init();
        verify(loginView).openDashboardScreen();
    }

    @Test
    public void showLoginScreen() {

        when(preferenceUtils.getUserLogin()).thenReturn("");
        when(preferenceUtils.getUserCredentials()).thenReturn(false);
        loginPresenter.init();
        verify(loginView, never()).openDashboardScreen();
    }

    @Test
    public void successLogin() {

        String login = "igor";
        String password = "igor123";

        when(repository.auth(login, password))
                .thenReturn(Observable.just(new LoginResponse()));

        loginPresenter.tryLogin(login, password);
        verify(loginView).showLoading();
        verify(loginView).hideLoading();
        verify(loginView).openDashboardScreen();
        verify(loginView, never()).showLoginError();
    }

    @Test
    public void failLogin() throws Exception {

        String login = "igor";
        String password = "igor123";
        ApiException exception = new ApiException("error", 9999);

        when(repository.auth(login, password))
                .thenReturn(Observable.error(exception));

        loginPresenter.tryLogin(login, password);
        verify(loginView).showLoading();
        verify(loginView).hideLoading();
        verify(loginView).showError(exception);
        verify(loginView, never()).openDashboardScreen();
    }

    @Test
    public void failLoginWithWrongCredentials() throws Exception {

        String login = "";
        String password = "igor123";

        loginPresenter.tryLogin(login, password);
        verify(loginView).showLoginError();
        verify(loginView, never()).openDashboardScreen();

        login = "igor123";
        password = "";

        loginPresenter.tryLogin(login, password);
        verify(loginView).showPasswordError();
        verify(loginView, never()).openDashboardScreen();
    }
}