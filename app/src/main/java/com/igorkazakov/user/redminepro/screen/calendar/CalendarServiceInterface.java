package com.igorkazakov.user.redminepro.screen.calendar;

import com.igorkazakov.user.redminepro.database.room.entity.OggyCalendarDayEntity;

import java.util.List;

import io.reactivex.Single;

public interface CalendarServiceInterface {
    Single<List<OggyCalendarDayEntity>> getCalendarDaysForYear();
}
