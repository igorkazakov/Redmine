package com.igorkazakov.user.redminepro.screen.calendar;

import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;

import java.util.List;

import io.reactivex.Single;

public interface CalendarServiceInterface {
    Single<List<OggyCalendarDay>> getCalendarDaysForYear();
}
