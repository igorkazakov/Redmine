package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.ContentType;
import com.igorkazakov.user.redminepro.api.OggyApi;
import com.igorkazakov.user.redminepro.api.RedmineApi;
import com.igorkazakov.user.redminepro.api.response.LoginResponse;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Child;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Detail;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.IssueDetail;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Journal;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;
import com.igorkazakov.user.redminepro.api.responseEntity.Membership;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.database.realm.FixedVersionDAO;
import com.igorkazakov.user.redminepro.database.realm.IssueDetailDAO;
import com.igorkazakov.user.redminepro.database.realm.ProjectPriorityDAO;
import com.igorkazakov.user.redminepro.database.realm.ShortUserDAO;
import com.igorkazakov.user.redminepro.database.realm.StatusDAO;
import com.igorkazakov.user.redminepro.database.realm.TrackerDAO;
import com.igorkazakov.user.redminepro.database.room.database.RoomDbHelper;
import com.igorkazakov.user.redminepro.database.room.entity.ChildEntity;
import com.igorkazakov.user.redminepro.database.room.entity.FixedVersionEntity;
import com.igorkazakov.user.redminepro.database.room.entity.IssueEntity;
import com.igorkazakov.user.redminepro.database.room.entity.OggyCalendarDayEntity;
import com.igorkazakov.user.redminepro.database.room.entity.ProjectEntity;
import com.igorkazakov.user.redminepro.database.room.entity.ShortUserEntity;
import com.igorkazakov.user.redminepro.database.room.entity.TimeEntryEntity;
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
    public Observable<List<IssueEntity>> getMyIssues() {

        Observable<List<IssueEntity>> observableNetwork = Observable
                .range(0, Integer.MAX_VALUE - 1)
                .lift(RxUtils.getApiErrorTransformer())
                .subscribeOn(Schedulers.io())
                .concatMap(integer -> {

                    int offset = integer * limit;
                    return getIssues(offset);
                })
                .takeUntil((Predicate<? super List<IssueEntity>>) List::isEmpty);

        return mRoomDbHelper.issueEntityDAO()
                .getAll()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .concatWith(observableNetwork);
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

                    for (Journal journal : issue.getJournals()) {
                        for (Detail detail : journal.getDetails()) {
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
    public Single<List<IssueEntity>> getChildIssues(List<ChildEntity> children) {

        List<Long> childIssueIds = new ArrayList<>(children.size());
        for (ChildEntity child: children) {
            childIssueIds.add(child.getChildId());
        }

        return mRoomDbHelper
                .issueEntityDAO()
                .getChildIssues(childIssueIds)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<ChildEntity>> getChildsByIssueDetailId(long issueDetailId) {

        return mRoomDbHelper
                .childEntityDAO()
                .getChildrensById(issueDetailId)
                .subscribeOn(Schedulers.io());
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
    public Observable<List<TimeEntryEntity>> getTimeEntriesWithInterval(TimeInterval interval, long offset) {

        long userId = mPreferenceUtils.getUserId();
        String startDateString = DateUtils.stringFromDate(interval.getStart(), DateUtils.getSimpleFormatter());
        String endDateString = DateUtils.stringFromDate(interval.getEnd(), DateUtils.getSimpleFormatter());
        String strInterval = "><" + startDateString + "|" + endDateString;

        return mRedmineApi
                .timeEntriesForYear(limit, userId, offset, strInterval)
                .subscribeOn(Schedulers.io())
                .lift(RxUtils.getApiErrorTransformer())
                .map(timeEntryResponse -> {

                    List<TimeEntryEntity> timeEntryEntities = new ArrayList<>(timeEntryResponse.getTimeEntries().size());

                    for (TimeEntry timeEntry : timeEntryResponse.getTimeEntries()) {

                        if (timeEntry.getCustomFields().size() > 0) {
                            String type = null;
                            if (timeEntry.getCustomFields().get(0) != null) {
                                type = timeEntry.getCustomFields().get(0).getValue();
                            }
                            timeEntry.setType(type != null ? type : "");
                        }

                        TimeEntryEntity entity = new TimeEntryEntity(timeEntry);
                        timeEntryEntities.add(entity);
                    }

                    mRoomDbHelper.timeEntryEntityDAO().insertOrUpdate(timeEntryEntities);
                    return timeEntryEntities;
                });
    }

    @NonNull
    public Observable<List<TimeEntryEntity>> getTimeEntriesForYear() {

        TimeInterval interval = DateUtils.getIntervalFromStartYear();

        Observable<List<TimeEntryEntity>> observableNetwork = Observable
                .intervalRange(0,
                        Integer.MAX_VALUE - 1,
                        0,
                        1,
                        TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .lift(RxUtils.getApiErrorTransformer())
                .concatMap(integer -> {

                    long offset = integer * limit;
                    return getTimeEntriesWithInterval(interval, offset);
                })
                .takeUntil((Predicate<? super List<TimeEntryEntity>>) List::isEmpty);

        Observable<List<TimeEntryEntity>> cachedData = mRoomDbHelper.timeEntryEntityDAO()
                .getAll()
                .subscribeOn(Schedulers.io())
                .toObservable();

        return cachedData.concatWith(observableNetwork)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Long> fetchHoursNormForInterval(TimeInterval interval) {
        return mRoomDbHelper.oggyCalendarDayEntityDAO().getHoursNormForInterval(
                interval.getStartLong(),
                interval.getEndLong())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<TimeModel> fetchWorkHoursWithInterval(TimeInterval interval) {
        return mRoomDbHelper.timeEntryEntityDAO().getWorkHoursWithInterval(interval);
    }

    @NonNull
    public Single getProjects() {

        return mRedmineApi
                .projects()
                .map(projectsResponse -> {

                    List<ProjectEntity> projects = projectsResponse.getProjects();
                    mRoomDbHelper.projectEntityDAO().insertOrUpdate(projects);

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

                    mRoomDbHelper.trackerEntityDAO()
                            .insertOrUpdate(trackersResponse.getTrackers());
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

                    mRoomDbHelper.statusEntityDAO()
                            .insertOrUpdate(statusesResponse.getStatuses());

                    return null;
                })
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.io());
    }

    public Single getProjectPriorities() {

        return mRedmineApi
                .priorities()
                .map(prioritiesResponse -> {

                    mRoomDbHelper.priorityEntityDAO()
                            .insertOrUpdate(prioritiesResponse.getPriorities());
                    return null;
                })
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.io());
    }

    public Single<TimeModel> workHoursWithInterval(TimeInterval interval) {
        return mRoomDbHelper.timeEntryEntityDAO().getWorkHoursWithInterval(interval);
    }

    public Single<TimeModel> workHoursWithDate(Date date) {
        return mRoomDbHelper.timeEntryEntityDAO().getWorkHoursWithDate(date);
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

                    List<FixedVersionEntity> fixedVersions = versionsResponse.getFixedVersions();
                    mRoomDbHelper.fixedVersionEntityDAO().insertOrUpdate(fixedVersions);

                    return fixedVersions;
                })
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    private void loadUsers(@NonNull List<ProjectEntity> projects) {

        for (ProjectEntity projectEntity : projects) {

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
                .takeUntil((Predicate<? super List<ShortUserEntity>>) List::isEmpty)
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @NonNull
    private Observable<List<ShortUserEntity>> getMemberships(int offset, long projectId) {

        return mRedmineApi
                .memberships(projectId, limit, offset)
                .map(membershipsResponse -> {

                    List<Membership> memberships = membershipsResponse.getMemberships();
                    List<ShortUserEntity> shortUsers = new ArrayList<>();
                    for (Membership membership : memberships) {
                        if (membership.getUser() != null) {
                            shortUsers.add(membership.getUser());
                        }
                    }

                    mRoomDbHelper.shortUserEntityDAO().insertOrUpdate(shortUsers);
                    return shortUsers;
                })
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribeOn(Schedulers.newThread());
    }

    @NonNull
    private Observable<List<IssueEntity>> getIssues(int offset) {

        return mRedmineApi
                .issues(limit, offset)
                .lift(RxUtils.getApiErrorTransformer())
                .map(issuesResponse -> {

                    List<IssueEntity> issues = issuesResponse.getIssues();
                    mRoomDbHelper.issueEntityDAO().insertOrUpdate(issues);
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
