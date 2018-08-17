package com.igorkazakov.user.redminepro.screen.dashboard;

import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.database.room.entity.OggyCalendarDayEntity;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface DashboardServiceInterface {

    Single<List<OggyCalendarDayEntity>> getCalendarDaysForYear();
    Single getStatuses();
    Single getProjectPriorities();
    Single getTrackers();
    Single getProjects();
    Observable<List<TimeEntry>> getTimeEntriesForYear();
    float fetchHoursNormForInterval(TimeInterval interval);
    TimeModel fetchWorkHoursWithInterval(TimeInterval interval);
}
