package com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryActivity;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryCustomField;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryIssue;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryProject;
import com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects.TimeEntryUser;

import java.util.List;

public class TimeEntry {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("project")
    @Expose
    private TimeEntryProject project;
    @SerializedName("issue")
    @Expose
    private TimeEntryIssue issue;
    @SerializedName("user")
    @Expose
    private TimeEntryUser user;
    @SerializedName("activity")
    @Expose
    private TimeEntryActivity activity;
    @SerializedName("hours")
    @Expose
    private Double hours;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("spent_on")
    @Expose
    private String spentOn;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("custom_fields")
    @Expose
    private List<TimeEntryCustomField> customFields = null;

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

    public List<TimeEntryCustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<TimeEntryCustomField> customFields) {
        this.customFields = customFields;
    }
}
