package com.igorkazakov.user.redminepro.database.room.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Author;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = IssueDetailEntity.class,
        parentColumns = "id",
        childColumns = "parentId",
        onDelete = CASCADE))

public class AttachmentEntity extends EmptyEntity {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("filesize")
    @Expose
    private Long filesize;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("content_url")
    @Expose
    private String contentUrl;
    @SerializedName("author")
    @Expose
    @Embedded
    private Author author;
    @SerializedName("created_on")
    @Expose
    private String createdOn;

    private Long parentId;

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public static AttachmentEntity createEmptyInstance() {
        AttachmentEntity entity = new AttachmentEntity();
        entity.setEmpty(true);
        return entity;
    }
}
