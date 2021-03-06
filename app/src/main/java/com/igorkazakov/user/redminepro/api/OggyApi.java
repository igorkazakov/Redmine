package com.igorkazakov.user.redminepro.api;

import com.igorkazakov.user.redminepro.database.room.entity.OggyCalendarDayEntity;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by user on 14.07.17.
 */

public interface OggyApi {

    @GET("/api/user_calendar")
    Single<List<OggyCalendarDayEntity>> getCalendarDays(@Query("login") String login,
                                                        @Query("password") String password,
                                                        @Query("month") Integer month,
                                                        @Query("year") Integer year);
}
