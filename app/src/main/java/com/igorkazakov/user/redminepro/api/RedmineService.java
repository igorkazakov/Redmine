package com.igorkazakov.user.redminepro.api;

import com.igorkazakov.user.redminepro.api.response.IssuesResponse;
import com.igorkazakov.user.redminepro.api.response.LoginResponse;
import com.igorkazakov.user.redminepro.api.response.MembershipsResponse;
import com.igorkazakov.user.redminepro.api.response.PrioritiesResponse;
import com.igorkazakov.user.redminepro.api.response.ProjectsResponse;
import com.igorkazakov.user.redminepro.api.response.StatusesResponse;
import com.igorkazakov.user.redminepro.api.response.TimeEntryResponse;
import com.igorkazakov.user.redminepro.api.response.TrackersResponse;
import com.igorkazakov.user.redminepro.api.response.VersionsResponse;

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

    @GET("/issue_statuses.json")
    Observable<StatusesResponse> statuses();

    @GET("/trackers.json")
    Observable<TrackersResponse> trackers();

    @GET("/projects.json")
    Observable<ProjectsResponse> projects();

    @GET("/projects/{project_id}/memberships.json")
    Observable<MembershipsResponse> memberships(@Path("project_id") long projectId,
                                                @Query("limit") int limit,
                                                @Query("offset") int offset);

    @GET("/projects/{project_id}/versions.json")
    Observable<VersionsResponse> versions(@Path("project_id") long projectId);

    @GET("/enumerations/issue_priorities.json")
    Observable<PrioritiesResponse> priorities();
}
