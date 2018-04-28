package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.TimeEntry;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 13.07.17.
 */

@DatabaseTable(tableName = "TimeEntryEntity")
public class TimeEntryEntity {

    @DatabaseField(id = true)
    private long id;

    @DatabaseField(columnName = "project_id")
    private long projectId;

    @DatabaseField(columnName = "project_name")
    private String projectName;

    @DatabaseField(columnName = "issue_id")
    private long issueId;

    @DatabaseField(columnName = "user_id")
    private long userId;

    @DatabaseField(columnName = "user_name")
    private String userName;

    @DatabaseField(columnName = "activity_id")
    private long activityId;

    @DatabaseField(columnName = "activity_name")
    private String activityName;

    @DatabaseField(columnName = "hours")
    private double hours;

    @DatabaseField(columnName = "comments")
    private String comments;

    @DatabaseField(columnName = "type")
    private String type;

    @DatabaseField(columnName = "spent_on")
    private String spentOn;

    @DatabaseField(columnName = "created_on")
    private String createdOn;

    @DatabaseField(columnName = "updated_on")
    private String updatedOn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpentOn() {
        return spentOn;
    }

    public void setSpentOn(String spentOn) {
        this.spentOn = spentOn;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public static List<TimeEntryEntity> convertItems(List<TimeEntry> items) {

        if (items == null) {
            return null;
        }

        ArrayList<TimeEntryEntity> times = new ArrayList<>();
        for (TimeEntry item : items) {

            TimeEntryEntity timeEntryEntity = new TimeEntryEntity();
            timeEntryEntity.setId(item.getId());
            timeEntryEntity.setProjectId(item.getProject().getId());
            timeEntryEntity.setProjectName(item.getProject().getName());
            timeEntryEntity.setIssueId(item.getIssue().getId());
            timeEntryEntity.setUserId(item.getUser().getId());
            timeEntryEntity.setUserName(item.getUser().getName());
            timeEntryEntity.setActivityId(item.getActivity().getId());
            timeEntryEntity.setActivityName(item.getActivity().getName());
            timeEntryEntity.setHours(item.getHours());
            timeEntryEntity.setComments(item.getComments());
           // timeEntryEntity.setSpentOn(item.getSpentOn());
           // timeEntryEntity.setUpdatedOn(item.getUpdatedOn());
           // timeEntryEntity.setCreatedOn(item.getCreatedOn());
            timeEntryEntity.setType(item.getCustomFields().get(0).getValue());
            times.add(timeEntryEntity);
        }

        return times;
    }

    public enum TimeType {

        REGULAR("Regular"),
        FUCKUP("Fuc%up"),
        TEAMFUCKUP("Duties");

        private String value;

        private TimeType(String string){
            this.value = string;
        }

        public String getValue(){
            return  this.value;
        }
    }
}
