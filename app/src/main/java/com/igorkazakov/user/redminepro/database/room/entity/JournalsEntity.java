package com.igorkazakov.user.redminepro.database.room.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Journal;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = IssueDetailEntity.class,
        parentColumns = "id",
        childColumns = "parentId",
        onDelete = CASCADE))

public class JournalsEntity extends EmptyEntity {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("user")
    @Expose
    @Embedded
    private ShortUser user;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("created_on")
    @Expose
    private String createdOn;

    private Long parentId;

    public JournalsEntity() {

    }

    public JournalsEntity(Journal journal, Long parentId) {
        this.id = journal.getId();
        this.user = journal.getUser();
        this.notes = journal.getNotes();
        this.createdOn = journal.getCreatedOn();
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShortUser getUser() {
        return user;
    }

    public void setUser(ShortUser user) {
        this.user = user;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public static JournalsEntity createEmptyInstance() {
        JournalsEntity entity = new JournalsEntity();
        entity.setEmpty(true);
        return entity;
    }
}
