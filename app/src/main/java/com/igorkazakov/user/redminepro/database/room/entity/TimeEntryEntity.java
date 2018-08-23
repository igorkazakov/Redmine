package com.igorkazakov.user.redminepro.database.room.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryActivity;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryIssue;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryProject;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryUser;
import com.igorkazakov.user.redminepro.database.room.converters.DateConverter;

import java.util.Date;

@Entity(tableName = "TimeEntryEntity")
@TypeConverters({DateConverter.class})
public class TimeEntryEntity extends EmptyEntity {

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

    public TimeEntryEntity() {

    }

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

    public void setId(Integer id) {
        this.id = id;
    }

    public TimeEntryProject getProject() {
        return project;
    }

    public void setProject(TimeEntryProject project) {
        this.project = project;
    }

    public TimeEntryIssue getIssue() {
        return issue;
    }

    public void setIssue(TimeEntryIssue issue) {
        this.issue = issue;
    }

    public TimeEntryUser getUser() {
        return user;
    }

    public void setUser(TimeEntryUser user) {
        this.user = user;
    }

    public TimeEntryActivity getActivity() {
        return activity;
    }

    public void setActivity(TimeEntryActivity activity) {
        this.activity = activity;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getSpentOn() {
        return spentOn;
    }

    public void setSpentOn(Date spentOn) {
        this.spentOn = spentOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static TimeEntryEntity createEmptyInstance() {
        TimeEntryEntity entity = new TimeEntryEntity();
        entity.setEmpty(true);
        return entity;
    }
}
