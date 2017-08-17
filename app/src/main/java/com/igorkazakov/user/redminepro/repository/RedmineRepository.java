package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.api.ContentType;
import com.igorkazakov.user.redminepro.api.response.LoginResponse;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;
import com.igorkazakov.user.redminepro.api.responseEntity.Membership;
import com.igorkazakov.user.redminepro.api.responseEntity.Project;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.entity.CalendarDayEntity;
import com.igorkazakov.user.redminepro.database.entity.IssueEntity;
import com.igorkazakov.user.redminepro.database.entity.PriorityEntity;
import com.igorkazakov.user.redminepro.database.entity.ProjectEntity;
import com.igorkazakov.user.redminepro.database.entity.StatusEntity;
import com.igorkazakov.user.redminepro.database.entity.TimeEntryEntity;
import com.igorkazakov.user.redminepro.database.entity.TrackerEntity;
import com.igorkazakov.user.redminepro.database.entity.UserEntity;
import com.igorkazakov.user.redminepro.database.entity.VersionEntity;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.utils.AuthorizationUtils;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.PreferenceUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
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
    public static Observable<List<TimeEntryEntity>> getTimeEntriesWithInterval(TimeInterval interval, int offset) {

        long userId = PreferenceUtils.getInstance().getUserId();
        String startDateString = DateUtils.stringFromDate(interval.getStart(), DateUtils.getSimpleFormatter());
        String endDateString = DateUtils.stringFromDate(interval.getEnd(), DateUtils.getSimpleFormatter());
        String strInterval = "><" + startDateString + "|" + endDateString;

        return ApiFactory.getRedmineService()
                .timeEntriesForYear(limit, userId, offset, strInterval)
                .flatMap(timeEntryResponse -> {

                    List<TimeEntry> timeEntries = timeEntryResponse.getTimeEntries();
                    List<TimeEntryEntity> timeEntryEntities = TimeEntryEntity.convertItems(timeEntries);
                    DatabaseManager.getDatabaseHelper().getTimeEntryDAO().saveTimeEntries(timeEntryEntities);

                    return Observable.just(timeEntryEntities);
                })
                .onErrorResumeNext(throwable -> {

                    List<TimeEntryEntity> timeEntryEntities = new ArrayList<TimeEntryEntity>();//DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getAll();
                    return Observable.just(timeEntryEntities);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<TimeEntryEntity>> getTimeEntriesForYear() {

        TimeInterval interval = DateUtils.getIntervalFromStartYear();
        return Observable.range(0, Integer.MAX_VALUE - 1)
                .concatMap(new Func1<Integer, Observable<List<TimeEntryEntity>>>() {
                    @Override
                    public Observable<List<TimeEntryEntity>> call(Integer integer) {

                        int offset = integer * limit;
                        return getTimeEntriesWithInterval(interval, offset);
                    }
                })
                .takeUntil(List::isEmpty)
                .toList()
                .map(superList -> {

                    List<TimeEntryEntity> list = new ArrayList<>();

                    if (superList.size() != 0) {
                        for (List<TimeEntryEntity> itemList : superList) {
                            list.addAll(itemList);
                        }

                    } else {
                        list = DatabaseManager.getDatabaseHelper().getTimeEntryDAO().getAll();
                    }

                    return list;
                });
    }

    @NonNull
    public static Observable<List<IssueEntity>> getIssues(int offset) {

        return ApiFactory.getRedmineService()
                .issues(limit, offset)
                .flatMap(issuesResponse -> {

                    List<Issue> issues = issuesResponse.getIssues();
                    List<IssueEntity> issueEntities = IssueEntity.convertItems(issues);
                    DatabaseManager.getDatabaseHelper().getIssueEntityDAO().saveIssueEntities(issueEntities);
                    return Observable.just(issueEntities);
                })
                .onErrorResumeNext(throwable -> {

                    List<IssueEntity> issueEntities = new ArrayList<IssueEntity>();//DatabaseManager.getDatabaseHelper().getIssueEntityDAO().getAll();
                    return Observable.just(issueEntities);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<IssueEntity>> getMyIssues() {

        return Observable.range(0, Integer.MAX_VALUE - 1)
                .concatMap(new Func1<Integer, Observable<List<IssueEntity>>>() {
                    @Override
                    public Observable<List<IssueEntity>> call(Integer integer) {

                        int offset = integer * limit;
                        return getIssues(offset);
                    }
                })
                .takeUntil(List::isEmpty)
                .toList()
                .map(superList -> {

                    List<IssueEntity> list = new ArrayList<>();

                    if (superList.size() == 0) {
                        list = DatabaseManager.getDatabaseHelper().getIssueEntityDAO().getAll();

                    } else {

                        for (List<IssueEntity> itemList : superList) {
                            list.addAll(itemList);
                        }
                    }

                    return list;
                });
    }

    @NonNull
    public static Observable<List<CalendarDayEntity>> loadDashboardScreenData() {

        Observable<List<CalendarDayEntity>> calendarData = OggyRepository.getCalendarDaysForYear();
        Observable<List<TimeEntryEntity>> timeEntriesData = RedmineRepository.getTimeEntriesForYear();

        return Observable.zip(calendarData, timeEntriesData, new Func2<List<CalendarDayEntity>, List<TimeEntryEntity>, List<CalendarDayEntity>>() {
            @Override
            public List<CalendarDayEntity> call(List<CalendarDayEntity> calendarDayEntities, List<TimeEntryEntity> timeEntryEntities) {
                return null;
            }
        });
    }

    @NonNull
    public static Observable<IssueEntity> getIssueDetails(long issueId) {

        return ApiFactory.getRedmineService()
                .issueDetails(issueId)
                .flatMap(issuesResponse -> {

                    IssueEntity issueEntity = IssueEntity.convertItem(issuesResponse.getIssue());
                    DatabaseManager.getDatabaseHelper().getIssueEntityDAO().saveIssueEntity(issueEntity);
                    return Observable.just(issueEntity);
                })
                .onErrorResumeNext(throwable -> {

                    IssueEntity issueEntity = null;
                    try {
                        issueEntity = DatabaseManager.getDatabaseHelper().getIssueEntityDAO().queryForId(issueId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return Observable.just(issueEntity);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<ProjectEntity>> getProjects() {

        return ApiFactory.getRedmineService()
                .projects()
                .flatMap(projectsResponse -> {

                    List<Project> projects = projectsResponse.getProjects();
                    List<ProjectEntity> projectEntities = ProjectEntity.convertItems(projects);
                    DatabaseManager.getDatabaseHelper().getProjectEntityDAO().saveProjectEntities(projectEntities);

                    loadUsers(projectEntities);

                    return Observable.just(projectEntities);
                })
                .onErrorResumeNext(throwable -> {

                    List<ProjectEntity> projectEntities = DatabaseManager.getDatabaseHelper().getProjectEntityDAO().getAll();
                    return Observable.just(projectEntities);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<TrackerEntity>> getTrackers() {

        return ApiFactory.getRedmineService()
                .trackers()
                .flatMap(trackersResponse -> {

                    List<Tracker> trackers = trackersResponse.getTrackers();
                    List<TrackerEntity> trackerEntities = TrackerEntity.convertItems(trackers);
                    DatabaseManager.getDatabaseHelper().getTrackerEntityDAO().saveTrackerEntities(trackerEntities);

                    return Observable.just(trackerEntities);
                })
                .onErrorResumeNext(throwable -> {

                    List<TrackerEntity> trackerEntities = DatabaseManager.getDatabaseHelper().getTrackerEntityDAO().getAll();
                    return Observable.just(trackerEntities);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static Observable<List<StatusEntity>> getStatuses() {

        return ApiFactory.getRedmineService()
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

    @NonNull
    public static void getVersionsByProject(long projectId) {

        ApiFactory.getRedmineService()
                .versions(projectId)
                .flatMap(versionsResponse -> {

                    List<FixedVersion> fixedVersions = versionsResponse.getFixedVersions();
                    List<VersionEntity> versionEntities = VersionEntity.convertItems(fixedVersions);
                    DatabaseManager.getDatabaseHelper().getVersionEntityDAO().saveVersionEntities(versionEntities);

                    return Observable.just(versionEntities);
                })
                .onErrorResumeNext(throwable -> {

                    List<VersionEntity> versionEntities = DatabaseManager.getDatabaseHelper().getVersionEntityDAO().getAll();
                    return Observable.just(versionEntities);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @NonNull
    public static Observable<List<PriorityEntity>> getProjectPriorities() {

        return ApiFactory.getRedmineService()
                .priorities()
                .flatMap(prioritiesResponse -> {

                    List<Priority> priorities = prioritiesResponse.getPriorities();
                    List<PriorityEntity> priorityEntities = PriorityEntity.convertItems(priorities);
                    DatabaseManager.getDatabaseHelper().getPriorityEntityDAO().savePriorityEntities(priorityEntities);

                    return Observable.just(priorityEntities);
                })
                .onErrorResumeNext(throwable -> {

                    List<PriorityEntity> priorityEntities = DatabaseManager.getDatabaseHelper().getPriorityEntityDAO().getAll();
                    return Observable.just(priorityEntities);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static void loadUsers(@NonNull List<ProjectEntity> projectEntities) {

        for (ProjectEntity projectEntity: projectEntities) {

            getPaginatedMemberships(projectEntity.getId());
            getVersionsByProject(projectEntity.getId());
        }
    }

    private static void getPaginatedMemberships(long projectId) {

        Observable.range(0, Integer.MAX_VALUE - 1)
                .concatMap(new Func1<Integer, Observable<List<UserEntity>>>() {
                    @Override
                    public Observable<List<UserEntity>> call(Integer integer) {

                        int offset = integer * limit;
                        return getMemberships(offset, projectId);
                    }
                })
                .takeUntil(List::isEmpty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @NonNull
    private static  Observable<List<UserEntity>> getMemberships(int offset, long projectId) {

        return ApiFactory.getRedmineService()
                .memberships(projectId, limit, offset)
                .flatMap(membershipsResponse -> {

                    List<Membership> memberships = membershipsResponse.getMemberships();
                    List<ShortUser> shortUsers = new ArrayList<>();
                    for (Membership membership: memberships) {
                        if (membership.getUser() != null) {
                            shortUsers.add(membership.getUser());
                        }
                    }
                    List<UserEntity> userEntities = UserEntity.convertItems(shortUsers);
                    DatabaseManager.getDatabaseHelper().getUserEntityDAO().saveTimeEntries(userEntities);

                    return Observable.just(userEntities);
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
