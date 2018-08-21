package com.igorkazakov.user.redminepro.database.room.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryActivity;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryIssue;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryProject;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryUser;
import com.igorkazakov.user.redminepro.database.room.converters.DateConverter;

import java.util.Date;

import io.realm.annotations.PrimaryKey;

@Entity
@TypeConverters({DateConverter.class})
public class TimeEntryEntity {

    @PrimaryKey
    private Integer id;
    @Embedded
    private TimeEntryProject project;
    @Embedded
    private TimeEntryIssue issue;
    @Embedded
    private TimeEntryUser user;
    @Embedded
    private TimeEntryActivity activity;
    private Double hours;
    private String comments;
    private Date spentOn;
    private Date createdOn;
    private Date updatedOn;
    private String type;

    public TimeEntryEntity(TimeEntry timeEntry) {
        this.id = timeEntry.getId();
        this.project = timeEntry.getProject();
        this.issue = timeEntry.getIssue();
        this.user = timeEntry.getUser();
        this.activity = timeEntry.getActivity();
        this.hours = timeEntry.getHours();
        this.comments = timeEntry.getComments();
        this.spentOn = timeEntry.getSpentOn();
        this.createdOn = timeEntry.getCreatedOn();
        this.updatedOn = timeEntry.getUpdatedOn();
        this.type = timeEntry.getType();
    }

    public Integer getId() {
        return id;
    }

    public TimeEntryProject getProject() {
        return project;
    }

    public TimeEntryIssue getIssue() {
        return issue;
    }

    public TimeEntryUser getUser() {
        return user;
    }

    public TimeEntryActivity getActivity() {
        return activity;
    }

    public Double getHours() {
        return hours;
    }

    public String getComments() {
        return comments;
    }

    public Date getSpentOn() {
        return spentOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public String getType() {
        return type;
    }
}
