package com.igorkazakov.user.redminepro.repository;

import android.support.annotation.NonNull;

import com.igorkazakov.user.redminepro.api.response.LoginResponse;
import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.IssueDetail;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.models.TimeInterval;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface RepositoryInterface {

    Single<List<OggyCalendarDay>> getCalendarDaysForYear();
    Observable<LoginResponse> auth(@NonNull String login, @NonNull String password);
    Observable<List<TimeEntry>> getTimeEntriesWithInterval(TimeInterval interval, int offset);
    Observable<List<TimeEntry>> getTimeEntriesForYear();
    Observable<List<Issue>> getMyIssues();
    Observable<IssueDetail> getIssueDetails(long issueId);
    Single getProjects();
    Single getTrackers();
    Single getStatuses();
    void getVersionsByProject(long projectId);
    Single getProjectPriorities();
}
