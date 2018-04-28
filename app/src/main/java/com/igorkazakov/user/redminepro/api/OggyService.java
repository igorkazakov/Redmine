package com.igorkazakov.user.redminepro.api;

import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.OggyCalendarDay;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 14.07.17.
 */

public interface OggyService {

    @GET("/api/user_calendar")
    Observable<List<OggyCalendarDay>> getCalendarDays(@Query("login") String login,
                                                      @Query("password") String password,
                                                      @Query("month") Integer month,
                                                      @Query("year") Integer year);
}
