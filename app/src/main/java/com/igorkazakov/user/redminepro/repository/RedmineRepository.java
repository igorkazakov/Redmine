package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.ContentType;
import com.igorkazakov.user.redminepro.api.response.LoginResponse;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Project;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;
import com.igorkazakov.user.redminepro.api.responseEntity.Membership;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.database.realm.FixedVersionDAO;
import com.igorkazakov.user.redminepro.database.realm.IssueDAO;
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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by user on 11.07.17.
 */

public class RedmineRepository {

    private static int offset = 0;
    private static final int limit = 100;

    @NonNull
    public static Observable<LoginResponse> auth(@NonNull String login, @NonNull String password) {

        String authString = AuthorizationUtils.getInstanse().createAuthorizationString(login, password);
        return ApiFactory.getRedmineService()
                .login(authString, ContentType.JSON.getValue())
                .map(loginResponse -> {

                    PreferenceUtils utils = PreferenceUtils.getInstance();
                    utils.saveAuthToken(authString);
                    utils.saveUserId(loginResponse.getUser().getId());
                    utils.saveUserLogin(loginResponse.getUser().getLogin());
                    utils.saveUserName(loginResponse.getUser().getFirstName() +
                            " " + loginResponse.getUser().getLastName());
                    utils.saveUserMail(loginResponse.getUser().getMail());
                    ApiFactory.recreate();
                    return loginResponse;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<TimeEntry>> getTimeEntriesWithInterval(TimeInterval interval, int offset) {

        long userId = PreferenceUtils.getInstance().getUserId();
        String startDateString = DateUtils.stringFromDate(interval.getStart(), DateUtils.getSimpleFormatter());
        String endDateString = DateUtils.stringFromDate(interval.getEnd(), DateUtils.getSimpleFormatter());
        String strInterval = "><" + startDateString + "|" + endDateString;

        return ApiFactory.getRedmineService()
                .timeEntriesForYear(limit, userId, offset, strInterval)
                .map(timeEntryResponse -> {

                    List<TimeEntry> timeEntries = timeEntryResponse.getTimeEntries();
                    TimeEntryDAO.saveTimeEntries(timeEntries);
                    return timeEntries;
                })
                .onErrorResumeNext(throwable -> {

                    return Observable.just(TimeEntryDAO.getAll());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<TimeEntry>> getTimeEntriesForYear() {

        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        Observable<List<TimeEntry>> observable;

        Observable<List<TimeEntry>> observableNetwork = Observable
                .range(0, Integer.MAX_VALUE - 1)
                .subscribeOn(Schedulers.io())
                .concatMap( integer -> {

                    int offset = integer * limit;
                    return getTimeEntriesWithInterval(interval, offset);
                })
                .takeUntil((Predicate<? super List<TimeEntry>>) List::isEmpty)
                .concatMap(Observable::fromArray);

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
    public static Observable<List<Issue>> getIssues(int offset) {

        Observable<List<Issue>> observable;

        Observable<List<Issue>> observableNetwork = ApiFactory.getRedmineService()
                .issues(limit, offset)
                .map(issuesResponse -> {

                    List<Issue> issues = issuesResponse.getIssues();
                    IssueDAO.saveIssues(issues);
                    return issues;
                })
                .subscribeOn(Schedulers.io());

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
    public static Observable<List<Issue>> getMyIssues() {

        Observable<List<Issue>> observable;

        Observable<List<Issue>> observableNetwork = Observable
                .range(0, Integer.MAX_VALUE - 1)
                .subscribeOn(Schedulers.io())
                .concatMap(integer -> {

                    int offset = integer * limit;
                    return getIssues(offset);
                })
                .takeUntil((Predicate<? super List<Issue>>) List::isEmpty)
                .concatMap(Observable::fromArray);

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
    public static Observable<Issue> getIssueDetails(long issueId) {

        return ApiFactory.getRedmineService()
                .issueDetails(issueId)
                .map(issuesResponse -> {

                    Issue issue = issuesResponse.getIssue();
                    IssueDAO.saveIssue(issue);
                    return issue;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<Project>> getProjects() {

        return ApiFactory.getRedmineService()
                .projects()
                .map(projectsResponse -> {

                    List<Project> projects = projectsResponse.getProjects();
                    ProjectDAO.saveProjects(projects);

                    loadUsers(projects);

                    return projects;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<Tracker>> getTrackers() {

        return ApiFactory.getRedmineService()
                .trackers()
                .map(trackersResponse -> {

                    List<Tracker> trackers = trackersResponse.getTrackers();
                    TrackerDAO.saveTrackers(trackers);

                    return trackers;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<Status>> getStatuses() {

        return ApiFactory.getRedmineService()
                .statuses()
                .map(statusesResponse -> {

                    List<Status> statuses = statusesResponse.getStatuses();
                    StatusDAO.saveStatuses(statuses);

                    return statuses;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static void getVersionsByProject(long projectId) {

        ApiFactory.getRedmineService()
                .versions(projectId)
                .map(versionsResponse -> {

                    List<FixedVersion> fixedVersions = versionsResponse.getFixedVersions();
                    FixedVersionDAO.saveFixedVersions(fixedVersions);

                    return fixedVersions;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @NonNull
    public static Observable<List<Priority>> getProjectPriorities() {

        return ApiFactory.getRedmineService()
                .priorities()
                .map(prioritiesResponse -> {

                    List<Priority> priorities = prioritiesResponse.getPriorities();
                    ProjectPriorityDAO.saveIProjectPriorities(priorities);

                    return priorities;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static void loadUsers(@NonNull List<Project> projects) {

        for (Project projectEntity: projects) {

            getPaginatedMemberships(projectEntity.getId());
            getVersionsByProject(projectEntity.getId());
        }
    }

    private static void getPaginatedMemberships(long projectId) {

        Observable.range(0, Integer.MAX_VALUE - 1)
                .concatMap(integer -> {

                    int offset = integer * limit;
                    return getMemberships(offset, projectId);
                })
                .takeUntil((Predicate<? super List<ShortUser>>) List::isEmpty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @NonNull
    private static  Observable<List<ShortUser>> getMemberships(int offset, long projectId) {

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
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
