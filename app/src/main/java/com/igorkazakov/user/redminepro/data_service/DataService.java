package com.igorkazakov.user.redminepro.data_service;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.ContentType;
import com.igorkazakov.user.redminepro.api.OggyService;
import com.igorkazakov.user.redminepro.api.RedmineService;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.database.DatabaseHelper;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.StatusEntity;
import com.igorkazakov.user.redminepro.repository.RedmineRepository;
import com.igorkazakov.user.redminepro.screen.auth.AuthService;
import com.igorkazakov.user.redminepro.screen.auth.LoginView;
import com.igorkazakov.user.redminepro.screen.dashboard.DashboardService;
import com.igorkazakov.user.redminepro.utils.AuthorizationUtils;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import java.util.List;

import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Igor on 21.08.2017.
 */

public class DataService implements AuthService, DashboardService {

    private static DataService sService;
    private RedmineService mRedmineService;
    private OggyService mOggyService;
    private DatabaseHelper mDatabaseHelper;
    private PreferenceUtils mPreferenceUtils;
    private AuthorizationUtils mAuthorizationUtils;
    private static int offset = 0;
    private static final int limit = 100;

    public static DataService getInstance() {
        if (sService == null) {
            sService = new DataService();
        }

        return sService;
    }

    private DataService() {
        mRedmineService = ApiFactory.getRedmineService();
        mOggyService = ApiFactory.getOggyService();
        mDatabaseHelper = DatabaseManager.getDatabaseHelper();
        mPreferenceUtils = PreferenceUtils.getInstance();
        mAuthorizationUtils = AuthorizationUtils.getInstanse();
    }

    public void login(@NonNull String login, @NonNull String password,
                      @NonNull LifecycleHandler lifecycleHandler, @NonNull LoginView loginView) {

        mPreferenceUtils.saveUserPassword(password);
        String authString = mAuthorizationUtils.createAuthorizationString(login, password);

        mRedmineService.login(authString, ContentType.JSON.getValue())
                .flatMap(loginResponse -> {

                    PreferenceUtils.getInstance().saveUserId(loginResponse.getUser().getId());
                    PreferenceUtils.getInstance().saveUserLogin(loginResponse.getUser().getLogin());
                    PreferenceUtils.getInstance().saveUserName(loginResponse.getUser().getFirstName()
                            + " " + loginResponse.getUser().getLastName());
                    PreferenceUtils.getInstance().saveUserMail(loginResponse.getUser().getMail());
                    // ******WTF********
                    ApiFactory.recreate();
                    mRedmineService = ApiFactory.getRedmineService();
                    //******************
                    return Observable.just(loginResponse);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(loginView::showLoading)
                .doOnTerminate(loginView::hideLoading)
                .compose(lifecycleHandler.reload(R.id.auth_request))
                .subscribe(user -> loginView.openDashboardScreen(),
                        throwable -> loginView.showPasswordError());
    }

    @Override
    public void loadStatuses() {
        ApiFactory.getRedmineService()
                .statuses()
                .flatMap(statusesResponse -> {

                    List<Status> statuses = statusesResponse.getStatuses();
                    List<StatusEntity> statusEntities = StatusEntity.convertItems(statuses);
                    DatabaseManager.getDatabaseHelper().getStatusEntityDAO().saveStatusEntities(statusEntities);

                    return Observable.just(statusEntities);
                })
                .onErrorResumeNext(throwable -> {

                    List<StatusEntity> statusEntities = DatabaseManager.getDatabaseHelper().getStatusEntityDAO().getAll();
                    return Observable.just(statusEntities);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void loadTrackers() {

    }

    @Override
    public void loadProjectPriorities() {

    }

    @Override
    public void loadProjects() {

    }

    @Override
    public void loadCalendarDaysForYear() {

    }

    @Override
    public void loadTimeEntriesData() {

    }
}
