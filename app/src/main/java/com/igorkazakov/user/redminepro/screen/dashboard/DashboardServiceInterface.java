package com.igorkazakov.user.redminepro.screen.dashboard;

import com.igorkazakov.user.redminepro.database.room.entity.OggyCalendarDayEntity;
import com.igorkazakov.user.redminepro.database.room.entity.TimeEntryEntity;
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
    Observable<List<TimeEntryEntity>> getTimeEntriesForYear();
    Single<Long> fetchHoursNormForInterval(TimeInterval interval);
    Single<TimeModel> fetchWorkHoursWithInterval(TimeInterval interval);
}
