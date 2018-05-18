package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.ContentType;
import com.igorkazakov.user.redminepro.api.response.LoginResponse;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Detail;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.IssueDetail;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Journal;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Project;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;
import com.igorkazakov.user.redminepro.api.responseEntity.Membership;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.database.realm.FixedVersionDAO;
import com.igorkazakov.user.redminepro.database.realm.IssueDAO;
import com.igorkazakov.user.redminepro.database.realm.IssueDetailDAO;
import com.igorkazakov.user.redminepro.database.realm.ProjectDAO;
import com.igorkazakov.user.redminepro.database.realm.ProjectPriorityDAO;
import com.igorkazakov.user.redminepro.database.realm.ShortUserDAO;
import com.igorkazakov.user.redminepro.database.realm.StatusDAO;
import com.igorkazakov.user.redminepro.database.realm.TimeEntryDAO;
import com.igorkazakov.user.redminepro.database.realm.TrackerDAO;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.utils.AuthorizationUtils;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by user on 11.07.17.
 */

public class RedmineRepository {

    @Inject
    PreferenceUtils mPreferenceUtils;

    @Inject
    AuthorizationUtils mAuthorizationUtils;

    public RedmineRepository() {
        RedmineApplication.getComponent().inject(this);
    }

    private static final int limit = 100;

    @NonNull
    public Observable<LoginResponse> auth(@NonNull String login, @NonNull String password) {

        String authString = mAuthorizationUtils.createAuthorizationString(login, password);
        return ApiFactory.getRedmineService()
                .login(authString, ContentType.JSON.getValue())
                .lift(ApiFactory.getApiErrorTransformer())
                .map(loginResponse -> {

                    mPreferenceUtils.saveAuthToken(authString);
                    mPreferenceUtils.saveUserId(loginResponse.getUser().getId());
                    mPreferenceUtils.saveUserLogin(loginResponse.getUser().getLogin());
                    mPreferenceUtils.saveUserName(loginResponse.getUser().getFirstName() +
                            " " + loginResponse.getUser().getLastName());
                    mPreferenceUtils.saveUserMail(loginResponse.getUser().getMail());
                    ApiFactory.recreate();
                    return loginResponse;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public Observable<List<TimeEntry>> getTimeEntriesWithInterval(TimeInterval interval, int offset) {

        long userId = mPreferenceUtils.getUserId();
        String startDateString = DateUtils.stringFromDate(interval.getStart(), DateUtils.getSimpleFormatter());
        String endDateString = DateUtils.stringFromDate(interval.getEnd(), DateUtils.getSimpleFormatter());
        String strInterval = "><" + startDateString + "|" + endDateString;

        return ApiFactory.getRedmineService()
                .timeEntriesForYear(limit, userId, offset, strInterval)
                .lift(ApiFactory.getApiErrorTransformer())
                .map(timeEntryResponse -> {

                    List<TimeEntry> timeEntries = timeEntryResponse.getTimeEntries();
                    TimeEntryDAO.saveTimeEntries(timeEntries);
                    return timeEntries;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public Observable<List<TimeEntry>> getTimeEntriesForYear() {

        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        Observable<List<TimeEntry>> observable;

        Observable<List<TimeEntry>> observableNetwork = Observable
                .range(0, Integer.MAX_VALUE - 1)
                .lift(ApiFactory.getApiErrorTransformer())
                .subscribeOn(Schedulers.io())
                .concatMap( integer -> {

                    int offset = integer * limit;
                    return getTimeEntriesWithInterval(interval, offset);
                })
                .takeUntil((Predicate<? super List<TimeEntry>>) List::isEmpty)
                .toList()
                .map(superList -> {

                    List<TimeEntry> list = new ArrayList<>();

                    if (superList.size() != 0) {
                        for (List<TimeEntry> itemList : superList) {
                            list.addAll(itemList);
                        }

                    }

                    return list;
                })
                .toObservable();

        List<TimeEntry> cachedData = TimeEntryDAO.getAll();

        if (cachedData.size() > 0) {

            observable = Observable.just(cachedData)
                    .concatWith(observableNetwork);

        } else {
            observable = observableNetwork;
        }

        return observable.observeOn(AndroidSchedulers.mainThread());
    }


    @NonNull
    public Observable<List<Issue>> getIssues(int offset) {

        return ApiFactory.getRedmineService()
                .issues(limit, offset)
                .lift(ApiFactory.getApiErrorTransformer())
                .map(issuesResponse -> {

                    List<Issue> issues = issuesResponse.getIssues();
                    IssueDAO.saveIssues(issues);
                    return issues;
                })
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    public Observable<List<Issue>> getMyIssues() {

        Observable<List<Issue>> observable;

        Observable<List<Issue>> observableNetwork = Observable
                .range(0, Integer.MAX_VALUE - 1)
                .lift(ApiFactory.getApiErrorTransformer())
                .subscribeOn(Schedulers.io())
                .concatMap(integer -> {

                    int offset = integer * limit;
                    return getIssues(offset);
                })
                .takeUntil((Predicate<? super List<Issue>>) List::isEmpty)
                .toList()
                .map(superList -> {

                    List<Issue> list = new ArrayList<>();

                    if (superList.size() != 0) {
                        for (List<Issue> itemList : superList) {
                            list.addAll(itemList);
                        }

                    }

                    return list;
                })
                .toObservable();

        List<Issue> cachedData = IssueDAO.getAll();

        if (cachedData.size() > 0) {

            observable = Observable.just(cachedData)
                    .concatWith(observableNetwork);

        } else {
            observable = observableNetwork;
        }

        return observable.observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public Observable<IssueDetail> getIssueDetails(long issueId) {

        Observable<IssueDetail> observable;

        Observable<IssueDetail> observableNetwork = ApiFactory.getRedmineService()
                .issueDetails(issueId)
                .lift(ApiFactory.getApiErrorTransformer())
                .map(issuesResponse -> {

                    IssueDetail issue = issuesResponse.getIssue();

                    for (Journal journal: issue.getJournals()) {
                        for (Detail detail: journal.getDetails()) {
                            detail.generateId();
                        }
                    }

                    IssueDetailDAO.saveIssueDetail(issue);
                    return issue;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        IssueDetail cachedData = IssueDetailDAO.getIssueDetailById(issueId);

        if (cachedData != null) {

            observable = Observable.just(cachedData)
                    .concatWith(observableNetwork);

        } else {
            observable = observableNetwork;
        }

        return observable.observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public Observable<List<Project>> getProjects() {

        return ApiFactory.getRedmineService()
                .projects()
                .map(projectsResponse -> {

                    List<Project> projects = projectsResponse.getProjects();
                    ProjectDAO.saveProjects(projects);

                    loadUsers(projects);

                    return projects;
                })
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public Observable<List<Tracker>> getTrackers() {

        return ApiFactory.getRedmineService()
                .trackers()
                .map(trackersResponse -> {

                    List<Tracker> trackers = trackersResponse.getTrackers();
                    TrackerDAO.saveTrackers(trackers);

                    return trackers;
                })
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public Observable<List<Status>> getStatuses() {

        return ApiFactory.getRedmineService()
                .statuses()
                .map(statusesResponse -> {

                    List<Status> statuses = statusesResponse.getStatuses();
                    StatusDAO.saveStatuses(statuses);

                    return statuses;
                })
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public void getVersionsByProject(long projectId) {

        ApiFactory.getRedmineService()
                .versions(projectId)
                .map(versionsResponse -> {

                    List<FixedVersion> fixedVersions = versionsResponse.getFixedVersions();
                    FixedVersionDAO.saveFixedVersions(fixedVersions);

                    return fixedVersions;
                })
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @NonNull
    public Observable<List<Priority>> getProjectPriorities() {

        return ApiFactory.getRedmineService()
                .priorities()
                .map(prioritiesResponse -> {

                    List<Priority> priorities = prioritiesResponse.getPriorities();
                    ProjectPriorityDAO.saveIProjectPriorities(priorities);

                    return priorities;
                })
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void loadUsers(@NonNull List<Project> projects) {

        for (Project projectEntity: projects) {

            getPaginatedMemberships(projectEntity.getId());
            getVersionsByProject(projectEntity.getId());
        }
    }

    private void getPaginatedMemberships(long projectId) {

        Observable.range(0, Integer.MAX_VALUE - 1)
                .concatMap(integer -> {

                    int offset = integer * limit;
                    return getMemberships(offset, projectId);
                })
                .takeUntil((Predicate<? super List<ShortUser>>) List::isEmpty)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @NonNull
    private Observable<List<ShortUser>> getMemberships(int offset, long projectId) {

        return ApiFactory.getRedmineService()
                .memberships(projectId, limit, offset)
                .map(membershipsResponse -> {

                    List<Membership> memberships = membershipsResponse.getMemberships();
                    List<ShortUser> shortUsers = new ArrayList<>();
                    for (Membership membership: memberships) {
                        if (membership.getUser() != null) {
                            shortUsers.add(membership.getUser());
                        }
                    }

                    ShortUserDAO.saveShortUsers(shortUsers);

                    return shortUsers;
                })
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
