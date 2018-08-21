package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.ContentType;
import com.igorkazakov.user.redminepro.api.OggyApi;
import com.igorkazakov.user.redminepro.api.RedmineApi;
import com.igorkazakov.user.redminepro.api.response.LoginResponse;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Child;
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
import com.igorkazakov.user.redminepro.database.realm.CalendarDayDAO;
import com.igorkazakov.user.redminepro.database.realm.FixedVersionDAO;
import com.igorkazakov.user.redminepro.database.realm.IssueDAO;
import com.igorkazakov.user.redminepro.database.realm.IssueDetailDAO;
import com.igorkazakov.user.redminepro.database.realm.ProjectDAO;
import com.igorkazakov.user.redminepro.database.realm.ProjectPriorityDAO;
import com.igorkazakov.user.redminepro.database.realm.ShortUserDAO;
import com.igorkazakov.user.redminepro.database.realm.StatusDAO;
import com.igorkazakov.user.redminepro.database.realm.TimeEntryDAO;
import com.igorkazakov.user.redminepro.database.realm.TrackerDAO;
import com.igorkazakov.user.redminepro.database.room.database.RoomDbHelper;
import com.igorkazakov.user.redminepro.database.room.entity.OggyCalendarDayEntity;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.screen.Issue_detail.IssueDetailServiceInterface;
import com.igorkazakov.user.redminepro.screen.auth.LoginServiceInterface;
import com.igorkazakov.user.redminepro.screen.calendar.CalendarServiceInterface;
import com.igorkazakov.user.redminepro.screen.dashboard.DashboardServiceInterface;
import com.igorkazakov.user.redminepro.screen.issues.IssuesServiceInterface;
import com.igorkazakov.user.redminepro.utils.AuthorizationUtils;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;
import com.igorkazakov.user.redminepro.utils.RxUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class Repository implements LoginServiceInterface,
        CalendarServiceInterface,
        DashboardServiceInterface,
        IssueDetailServiceInterface,
        IssuesServiceInterface {

    @Inject
    AuthorizationUtils mAuthorizationUtils;
    @Inject
    PreferenceUtils mPreferenceUtils;
    @Inject
    RedmineApi mRedmineApi;
    @Inject
    OggyApi mOggyApi;
    @Inject
    RoomDbHelper mRoomDbHelper;

    private static volatile Repository sInstance;

    private static final int limit = 100;

    private Repository() {
        RedmineApplication.getComponent().inject(this);
    }

    public static Repository getInstance() {

        Repository localService = sInstance;
        if (localService == null) {
            synchronized (Repository.class) {
                localService = sInstance;
                if (localService == null) {
                    localService = sInstance = new Repository();
                }
            }
        }

        return localService;
    }

    /// ============================================================================================
    /// CalendarServiceInterface
    /// ============================================================================================

    public Single<List<OggyCalendarDayEntity>> getCalendarDaysForYear() {

        int year = DateUtils.getCurrentYear();

        List<Single<List<OggyCalendarDayEntity>>> observables = new ArrayList<>();
        observables.add(getCalendarDays(1, year));
        observables.add(getCalendarDays(2, year));
        observables.add(getCalendarDays(3, year));
        observables.add(getCalendarDays(4, year));
        observables.add(getCalendarDays(5, year));
        observables.add(getCalendarDays(6, year));
        observables.add(getCalendarDays(7, year));
        observables.add(getCalendarDays(8, year));
        observables.add(getCalendarDays(9, year));
        observables.add(getCalendarDays(10, year));
        observables.add(getCalendarDays(11, year));
        observables.add(getCalendarDays(12, year));

        return Single.zip(observables, args -> {

            List<OggyCalendarDayEntity> list1 = new ArrayList<>();
            for (Object arg : args) {
                list1.addAll((List<OggyCalendarDayEntity>) arg);
            }

            return list1;
        });
    }

    /// ============================================================================================
    /// LoginServiceInterface
    /// ============================================================================================

    @Override
    public String getUserLogin() {
        return mPreferenceUtils.getUserLogin();
    }

    @Override
    public boolean getUserCredentials() {
        return mPreferenceUtils.getUserCredentials();
    }

    @NonNull
    public Observable<LoginResponse> auth(String login, String password) {

        String authString = mAuthorizationUtils.createAuthorizationString(login, password);
        return mRedmineApi
                .login(authString, ContentType.JSON.getValue())
                .lift(RxUtils.getApiErrorTransformer())
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

    @Override
    public void saveUserPassword(String password) {
        mPreferenceUtils.saveUserPassword(password);
    }

    @Override
    public void saveUserCredentials(boolean state) {
        mPreferenceUtils.saveUserCredentials(state);
    }

    /// ============================================================================================
    /// IssuesServiceInterface
    /// ============================================================================================

    @NonNull
    public Observable<List<Issue>> getMyIssues() {

        Observable<List<Issue>> observable;

        Observable<List<Issue>> observableNetwork = Observable
                .range(0, Integer.MAX_VALUE - 1)
                .lift(RxUtils.getApiErrorTransformer())
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

    /// ============================================================================================
    /// IssueDetailServiceInterface
    /// ============================================================================================

    @NonNull
    public Observable<IssueDetail> getIssueDetails(long issueId) {

        Observable<IssueDetail> observable;

        Observable<IssueDetail> observableNetwork = mRedmineApi
                .issueDetails(issueId)
                .lift(RxUtils.getApiErrorTransformer())
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
                .subscribeOn(Schedulers.io());

        IssueDetail cachedData = IssueDetailDAO.getIssueDetailById(issueId);

        if (cachedData != null) {

            observable = Observable.just(cachedData)
                    .concatWith(observableNetwork);

        } else {
            observable = observableNetwork;
        }

        return observable.observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public List<Issue> getChildIssues(List<Child> children) {
        return IssueDAO.getChildIssues(children);
    }

    @Override
    public ShortUser getUserById(long id) {
        return ShortUserDAO.getUserById(id);
    }

    @Override
    public Status getStatusById(long id) {
        return StatusDAO.getStatusById(id);
    }

    @Override
    public Tracker getTrackerById(long id) {
        return TrackerDAO.getTrackerById(id);
    }

    @Override
    public FixedVersion getVersionById(long id) {
        return FixedVersionDAO.getFixedVersionById(id);
    }

    @Override
    public Priority getPriorityById(long id) {
        return ProjectPriorityDAO.getPriorityById(id);
    }

    /// ============================================================================================
    /// DashboardServiceInterface
    /// ============================================================================================

    @NonNull
    public Observable<List<TimeEntry>> getTimeEntriesWithInterval(TimeInterval interval, long offset) {

        long userId = mPreferenceUtils.getUserId();
        String startDateString = DateUtils.stringFromDate(interval.getStart(), DateUtils.getSimpleFormatter());
        String endDateString = DateUtils.stringFromDate(interval.getEnd(), DateUtils.getSimpleFormatter());
        String strInterval = "><" + startDateString + "|" + endDateString;

        return mRedmineApi
                .timeEntriesForYear(limit, userId, offset, strInterval)
                .lift(RxUtils.getApiErrorTransformer())
                .map(timeEntryResponse -> {

                    List<TimeEntry> timeEntries = timeEntryResponse.getTimeEntries();
                    TimeEntryDAO.saveTimeEntries(timeEntries);
                    return timeEntries;
                })
                .subscribeOn(Schedulers.computation());
    }

    @NonNull
    public Observable<List<TimeEntry>> getTimeEntriesForYear() {

        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        Observable<List<TimeEntry>> observable;

        Observable<List<TimeEntry>> observableNetwork = Observable
                .intervalRange(0,
                        Integer.MAX_VALUE - 1,
                        0,
                        1,
                        TimeUnit.SECONDS)
                .lift(RxUtils.getApiErrorTransformer())
                .subscribeOn(Schedulers.io())
                .concatMap( integer -> {

                    long offset = integer * limit;
                    return getTimeEntriesWithInterval(interval, offset);
                })
                .takeUntil((Predicate<? super List<TimeEntry>>) List::isEmpty);

        List<TimeEntry> cachedData = TimeEntryDAO.getAll();

        if (cachedData.size() > 0) {

            observable = Observable.just(cachedData)
                    .concatWith(observableNetwork);

        } else {
            observable = observableNetwork;
        }

        return observable.observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public float fetchHoursNormForInterval(TimeInterval interval) {
        return CalendarDayDAO.getHoursNormForInterval(interval);
    }

    @Override
    public TimeModel fetchWorkHoursWithInterval(TimeInterval interval) {
        return new TimeModel(0, 0, 0);//TimeEntryDAO.getWorkHoursWithInterval(interval);
    }

    @NonNull
    public Single getProjects() {

        return mRedmineApi
                .projects()
                .map(projectsResponse -> {

                    List<Project> projects = projectsResponse.getProjects();
                    ProjectDAO.saveProjects(projects);

                    loadUsers(projects);

                    return null;
                })
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    public Single getTrackers() {

        return mRedmineApi
                .trackers()
                .map(trackersResponse -> {

                    List<Tracker> trackers = trackersResponse.getTrackers();
                    TrackerDAO.saveTrackers(trackers);
                    trackers.clear();
                    return null;
                })
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    public Single getStatuses() {

        return mRedmineApi
                .statuses()
                .map(statusesResponse -> {

                    List<Status> statuses = statusesResponse.getStatuses();
                    StatusDAO.saveStatuses(statuses);
                    statuses.clear();

                    return null;
                })
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.io());
    }

    public Single getProjectPriorities() {

        return mRedmineApi
                .priorities()
                .map(prioritiesResponse -> {

                    List<Priority> priorities = prioritiesResponse.getPriorities();
                    ProjectPriorityDAO.saveIProjectPriorities(priorities);
                    priorities.clear();
                    return null;
                })
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.io());
    }

    public Single<TimeModel> workHoursWithInterval(TimeInterval interval) {
        return TimeEntryDAO.getWorkHoursWithInterval(interval)
                .subscribeOn(Schedulers.io());
    }

    public Single<TimeModel> workHoursWithDate(Date date) {
        return TimeEntryDAO.getWorkHoursWithDate(date)
                .subscribeOn(Schedulers.io());
    }

    public Single<Long> hoursNormForInterval(TimeInterval interval) {
        return mRoomDbHelper.oggyCalendarDayEntityDAO().getHoursNormForInterval(
                interval.getStart().getTime(),
                interval.getEnd().getTime())
                .subscribeOn(Schedulers.io());
    }

    public Single<Long> hoursNormForDate(Date date) {
        return mRoomDbHelper.oggyCalendarDayEntityDAO().getHoursNormForDate(date.getTime())
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(0L);
    }

    private void getVersionsByProject(long projectId) {

        mRedmineApi
                .versions(projectId)
                .map(versionsResponse -> {

                    List<FixedVersion> fixedVersions = versionsResponse.getFixedVersions();
                    FixedVersionDAO.saveFixedVersions(fixedVersions);

                    return fixedVersions;
                })
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.io())
                .subscribe();
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
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @NonNull
    private Observable<List<ShortUser>> getMemberships(int offset, long projectId) {

        return mRedmineApi
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
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.newThread());
    }

    @NonNull
    private Observable<List<Issue>> getIssues(int offset) {

        return mRedmineApi
                .issues(limit, offset)
                .lift(RxUtils.getApiErrorTransformer())
                .map(issuesResponse -> {

                    List<Issue> issues = issuesResponse.getIssues();
                    IssueDAO.saveIssues(issues);
                    return issues;
                })
                .subscribeOn(Schedulers.io());
    }

    private Single<List<OggyCalendarDayEntity>> getCalendarDays(int month, int year) {

        String login = mPreferenceUtils.getUserLogin();
        String password = mPreferenceUtils.getUserPassword();

        return mOggyApi
                .getCalendarDays(login, password, month, year)
                .lift(RxUtils.getApiErrorTransformer())
                .map(calendarDays -> {

                    mRoomDbHelper.oggyCalendarDayEntityDAO().insert(calendarDays);
                    return calendarDays;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
