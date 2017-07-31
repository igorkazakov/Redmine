package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Attachment;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Child;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Journal;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
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

    @DatabaseField(columnName = "spent_hours")
    private double spentHours;

    @DatabaseField(columnName = "estimated_hours")
    private double estimatedHours;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<ChildEntity> children = null;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<AttachmentEntity> attachments = null;

   // @ForeignCollectionField(eager = true)
   // private Collection<Object> changesets = null;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<JournalEntity> journals = null;

    public double getSpentHours() {
        return spentHours;
    }

    public void setSpentHours(double spentHours) {
        this.spentHours = spentHours;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

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

    public ForeignCollection<ChildEntity> getChildren() {
        return children;
    }

    public void setChildren(ForeignCollection<ChildEntity> children) {
        this.children = children;
    }

    public void convertChildren(List<Child> children, IssueEntity parent) {
        this.children = ChildEntity.concertItems(children, parent);
    }

    public ForeignCollection<AttachmentEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(ForeignCollection<AttachmentEntity> attachments) {
        this.attachments = attachments;
    }

    public void convertAttachments(List<Attachment> attachments, IssueEntity parent) {
        this.attachments = AttachmentEntity.convertItems(attachments, parent);
    }

//    public Collection<Object> getChangesets() {
//        return changesets;
//    }

//    public void setChangesets(Collection<Object> changesets) {
//        this.changesets = changesets;
//    }

    public ForeignCollection<JournalEntity> getJournals() {
        return journals;
    }

    public void setJournals(ForeignCollection<JournalEntity> journals) {
        this.journals = journals;
    }

    public void convertJournals(List<Journal> journals, IssueEntity parent) {
        this.journals = JournalEntity.convertItems(journals, parent);
    }

    public static IssueEntity convertItem(Issue issue) {

        if (issue == null) {
            return null;
        }

        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setId(issue.getId());
        issueEntity.setProjectId(issue.getProject().getId());
        issueEntity.setProjectName(issue.getProject().getName());
        issueEntity.setTrackerId(issue.getTracker().getId());
        issueEntity.setTrackerName(issue.getTracker().getName());
        issueEntity.setStatusId(issue.getStatus().getId());
        issueEntity.setStatusName(issue.getStatus().getName());
        issueEntity.setPriorityId(issue.getPriority().getId());
        issueEntity.setPriorityName(issue.getPriority().getName());
        issueEntity.setSpentHours(issue.getSpentHours());
        issueEntity.setEstimatedHours(issue.getEstimatedHours());

        if (issue.getAuthor() != null) {
            issueEntity.setAuthorId(issue.getAuthor().getId());
            issueEntity.setAuthorName(issue.getAuthor().getName());
        }

        if (issue.getAssignedTo() != null) {
            issueEntity.setAssignedToId(issue.getAssignedTo().getId());
            issueEntity.setAssignedToName(issue.getAssignedTo().getName());
        }

        if (issue.getFixedVersion() != null) {
            issueEntity.setFixedVersionId(issue.getFixedVersion().getId());
            issueEntity.setFixedVersionName(issue.getFixedVersion().getName());
        }

        issueEntity.setSubject(issue.getSubject());
        issueEntity.setDescription(issue.getDescription());
        issueEntity.setUpdatedOn(issue.getUpdatedOn());
        issueEntity.setCreatedOn(issue.getCreatedOn());
        issueEntity.setStartDate(issue.getStartDate());
        issueEntity.setDoneRatio(issue.getDoneRatio());
        issueEntity.convertChildren(issue.getChildren(), issueEntity);
        issueEntity.convertAttachments(issue.getAttachments(), issueEntity);
        issueEntity.convertJournals(issue.getJournals(), issueEntity);

        return issueEntity;
    }

    public static List<IssueEntity> convertItems(List<Issue> items) {

        if (items == null) {
            return null;
        }

        ArrayList<IssueEntity> issues = new ArrayList<>();
        for (Issue item : items) {

            issues.add(IssueEntity.convertItem(item));
        }

        return issues;
    }
}
