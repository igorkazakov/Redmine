package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.database.room.entity.AttachmentEntity;
import com.igorkazakov.user.redminepro.database.room.entity.ChildEntity;

import java.util.List;


public class IssueDetail {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("project")
    @Expose
    private ShortProject project;
    @SerializedName("parent")
    @Expose
    private Parent parent;
    @SerializedName("tracker")
    @Expose
    private Tracker tracker;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("priority")
    @Expose
    private Priority priority;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("assigned_to")
    @Expose
    private AssignedTo assignedTo;
    @SerializedName("fixed_version")
    @Expose
    private FixedVersion fixedVersion;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("spent_hours")
    @Expose
    private double spentHours;
    @SerializedName("estimated_hours")
    @Expose
    private double estimatedHours;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("done_ratio")
    @Expose
    private Long doneRatio;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("children")
    @Expose
    private List<ChildEntity> children;
    @SerializedName("attachments")
    @Expose
    private List<AttachmentEntity> attachments;

    @SerializedName("journals")
    @Expose
    private List<Journal> journals;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShortProject getProject() {
        return project;
    }

    public void setProject(ShortProject project) {
        this.project = project;
    }

    public Tracker getTracker() {
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public AssignedTo getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(AssignedTo assignedTo) {
        this.assignedTo = assignedTo;
    }

    public FixedVersion getFixedVersion() {
        return fixedVersion;
    }

    public void setFixedVersion(FixedVersion fixedVersion) {
        this.fixedVersion = fixedVersion;
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

    public List<ChildEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ChildEntity> children) {
        this.children = children;
    }

    public List<AttachmentEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentEntity> attachments) {
        this.attachments = attachments;
    }

    public List<Journal> getJournals() {
        return journals;
    }

    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }
}