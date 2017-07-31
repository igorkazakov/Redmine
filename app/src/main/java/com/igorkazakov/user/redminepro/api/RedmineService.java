package com.igorkazakov.user.redminepro.api;

import com.igorkazakov.user.redminepro.api.response.IssuesResponse;
import com.igorkazakov.user.redminepro.api.response.LoginResponse;
import com.igorkazakov.user.redminepro.api.response.TimeEntryResponse;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by user on 11.07.17.
 */

public interface RedmineService {

    @GET("/users/current.json")
    Observable<LoginResponse> login(@Header("Authorization") String authorization,
                                    @Header("Content-Type") String contentType);

    @GET("/time_entries.json")
    Observable<TimeEntryResponse> timeEntries(@Query("limit") int limit,
                                              @Query("user_id") long userId,
                                              @Query("offset") int offset);

    @GET("/time_entries.json")
    Observable<TimeEntryResponse> timeEntriesForYear(@Query("limit") int limit,
                                              @Query("user_id") long userId,
                                              @Query("offset") int offset,
                                              @Query("spent_on") String interval);

    @GET("/issues.json?assigned_to_id=me")
    Observable<IssuesResponse> issues(@Query("limit") int limit,
                                      @Query("offset") int offset);

    @GET("/issues/{issue_id}.json?include=attachments,journals,children,relations,changesets")
    Observable<IssuesResponse> issueDetails(@Path("issue_id") long issueid);

}
