package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.ContentType;
import com.igorkazakov.user.redminepro.api.response.IssuesResponse;
import com.igorkazakov.user.redminepro.api.response.LoginResponse;
import com.igorkazakov.user.redminepro.api.response.TimeEntryResponse;
import com.igorkazakov.user.redminepro.api.responseEntity.LoginAndTimeEntries;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.TimeEntryEntity;
import com.igorkazakov.user.redminepro.utils.AuthorizationUtils;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by user on 11.07.17.
 */

public class RedmineRepository {

    private static int offset = 0;
    private static final int limit = 100;

    @NonNull
    public static Observable<LoginResponse> auth(@NonNull String login, @NonNull String password) {

        String authString = AuthorizationUtils.createAuthorizationString(login, password);
        return ApiFactory.getRedmineService()
                .login(authString, ContentType.JSON.getValue())
                .flatMap(loginResponse -> {

                    PreferenceUtils.getInstance().saveUserId(loginResponse.getUser().getId());
                    PreferenceUtils.getInstance().saveUserLogin(loginResponse.getUser().getLogin());
                    PreferenceUtils.getInstance().saveUserName(loginResponse.getUser().getFirstName() + " " + loginResponse.getUser().getLastName());
                    PreferenceUtils.getInstance().saveUserMail(loginResponse.getUser().getMail());
                    ApiFactory.recreate();
                    return Observable.just(loginResponse);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<LoginAndTimeEntries> issuesWithTimeEntries() {

        Observable<IssuesResponse> issuesResponseObservable = ApiFactory.getRedmineService()
                .issues()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<TimeEntryResponse> jsonObjectObservable = ApiFactory.getRedmineService()
                .timeEntries(50, 171, 0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        return Observable.zip(issuesResponseObservable, jsonObjectObservable, (issuesResponse, timeEntryResponse) -> {

            return new LoginAndTimeEntries(issuesResponse.getIssues(), timeEntryResponse.getTimeEntries());
        });
    }

    @NonNull
    public static Observable<List<TimeEntryEntity>> getTimeEntries() {

        long userId = PreferenceUtils.getInstance().getUserId();
        return ApiFactory.getRedmineService()
                .timeEntries(limit, userId, offset)
                .flatMap(timeEntryResponse -> {

                    List<TimeEntry> timeEntries = timeEntryResponse.getTimeEntries();
                    List<TimeEntryEntity> timeEntryEntities = TimeEntryEntity.convertItems(timeEntries);
                    DatabaseManager.getDatabaseHelper().getTimeEntryDAO().saveTimeEntries(timeEntryEntities);

                    return Observable.just(timeEntryEntities);
                })
                .onErrorResumeNext(throwable -> {

                    List<TimeEntryEntity> timeEntryEntities = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getAll();
                    return Observable.just(timeEntryEntities);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<TimeEntryEntity>> getTimeEntriesWithInterval(String interval) {

        long userId = PreferenceUtils.getInstance().getUserId();
        interval = "%3E%3C" + "2017-01-01|2017-07-13";
        return ApiFactory.getRedmineService()
                .timeEntriesForYear(limit, userId, offset, interval)
                .flatMap(timeEntryResponse -> {

                    List<TimeEntry> timeEntries = timeEntryResponse.getTimeEntries();
                    List<TimeEntryEntity> timeEntryEntities = TimeEntryEntity.convertItems(timeEntries);
                    DatabaseManager.getDatabaseHelper().getTimeEntryDAO().saveTimeEntries(timeEntryEntities);

                    return Observable.just(timeEntryEntities);
                })
                .onErrorResumeNext(throwable -> {

                    List<TimeEntryEntity> timeEntryEntities = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getAll();
                    return Observable.just(timeEntryEntities);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static void getTimeEntriesForYear() {

        Observable.range(0, Integer.MAX_VALUE - 1)
                .concatMap(new Func1<Integer, Observable<List<TimeEntryEntity>>>() {
                    @Override
                    public Observable<List<TimeEntryEntity>> call(Integer integer) {
                        offset += limit;
                        return getTimeEntriesWithInterval("");
                    }
                })
                .takeUntil(timeEntryEntities -> !timeEntryEntities.isEmpty())
                .toList()
                .map(superList -> {

                    List<TimeEntryEntity> list = new ArrayList<TimeEntryEntity>();
                    for (List<TimeEntryEntity> itemList : superList) {
                        list.addAll(itemList);
                    }

                    return list;
                })
//                .scan((timeEntryEntities, timeEntryEntities2) -> {
//                    timeEntryEntities.addAll(timeEntryEntities2);
//                    return timeEntryEntities;
//                })
                .subscribe(list -> System.out.println("total list size: " + list.size()));
    }

    @NonNull
    public static Observable<IssuesResponse> getIssues() {

        return ApiFactory.getRedmineService()
                .issues()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
