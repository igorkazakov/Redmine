package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 25.07.17.
 */
@DatabaseTable(tableName = "IssueEntity")
public class IssueEntity {

    @DatabaseField(id = true)
    private long id;

    @DatabaseField(columnName = "project_id")
    private long projectId;

    @DatabaseField(columnName = "project_name")
    private String projectName;

    @DatabaseField(columnName = "tracker_id")
    private long trackerId;

    @DatabaseField(columnName = "tracker_name")
    private String trackerName;

    @DatabaseField(columnName = "status_id")
    private long statusId;

    @DatabaseField(columnName = "status_name")
    private String statusName;

    @DatabaseField(columnName = "priority_id")
    private long priorityId;

    @DatabaseField(columnName = "priority_name")
    private String priorityName;

    @DatabaseField(columnName = "author_id")
    private long authorId;

    @DatabaseField(columnName = "author_name")
    private String authorName;

    @DatabaseField(columnName = "assigned_to_id")
    private long assignedToId;

    @DatabaseField(columnName = "assigned_to_name")
    private String assignedToName;

    @DatabaseField(columnName = "fixed_version_id")
    private long fixedVersionId;

    @DatabaseField(columnName = "fixed_version_name")
    private String fixedVersionName;

    @DatabaseField(columnName = "subject")
    private String subject;

    @DatabaseField(columnName = "description")
    private String description;

    @DatabaseField(columnName = "start_date")
    private String startDate;

    @DatabaseField(columnName = "done_ratio")
    private Long doneRatio;

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

    public long getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(long trackerId) {
        this.trackerId = trackerId;
    }

    public String getTrackerName() {
        return trackerName;
    }

    public void setTrackerName(String trackerName) {
        this.trackerName = trackerName;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(long priorityId) {
        this.priorityId = priorityId;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }

    public long getFixedVersionId() {
        return fixedVersionId;
    }

    public void setFixedVersionId(long fixedVersionId) {
        this.fixedVersionId = fixedVersionId;
    }

    public String getFixedVersionName() {
        return fixedVersionName;
    }

    public void setFixedVersionName(String fixedVersionName) {
        this.fixedVersionName = fixedVersionName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getDoneRatio() {
        return doneRatio;
    }

    public void setDoneRatio(Long doneRatio) {
        this.doneRatio = doneRatio;
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

    public static List<IssueEntity> convertItems(List<Issue> items) {

        if (items == null) {
            return null;
        }

        ArrayList<IssueEntity> issues = new ArrayList<>();
        for (Issue item : items) {

            IssueEntity issueEntity = new IssueEntity();
            issueEntity.setId(item.getId());
            issueEntity.setProjectId(item.getProject().getId());
            issueEntity.setProjectName(item.getProject().getName());
            issueEntity.setTrackerId(item.getTracker().getId());
            issueEntity.setTrackerName(item.getTracker().getName());
            issueEntity.setStatusId(item.getStatus().getId());
            issueEntity.setStatusName(item.getStatus().getName());
            issueEntity.setPriorityId(item.getPriority().getId());
            issueEntity.setPriorityName(item.getPriority().getName());
            issueEntity.setAuthorId(item.getAuthor().getId());
            issueEntity.setAuthorName(item.getAuthor().getName());
            issueEntity.setAssignedToId(item.getAssignedTo().getId());
            issueEntity.setAssignedToName(item.getAssignedTo().getName());
            issueEntity.setFixedVersionId(item.getFixedVersion().getId());
            issueEntity.setFixedVersionName(item.getFixedVersion().getName());
            issueEntity.setSubject(item.getSubject());
            issueEntity.setDescription(item.getDescription());
            issueEntity.setUpdatedOn(item.getUpdatedOn());
            issueEntity.setCreatedOn(item.getCreatedOn());
            issueEntity.setStartDate(item.getStartDate());
            issueEntity.setDoneRatio(item.getDoneRatio());
            issues.add(issueEntity);
        }

        return issues;
    }
}
