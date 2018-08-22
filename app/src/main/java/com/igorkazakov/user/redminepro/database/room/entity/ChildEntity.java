package com.igorkazakov.user.redminepro.database.room.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = IssueDetailEntity.class,
        parentColumns = "id",
        childColumns = "parentId",
        onDelete = CASCADE))

public class ChildEntity {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Long childId;
    @SerializedName("tracker")
    @Expose
    @Embedded
    private Tracker tracker;
    @SerializedName("subject")
    @Expose
    private String subject;

    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long id) {
        this.childId = id;
    }

    public Tracker getTracker() {
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
