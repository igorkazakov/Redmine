package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 28.07.17.
 */

public class Attachment extends RealmObject {

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
    private Author author;
    @SerializedName("created_on")
    @Expose
    private String createdOn;

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

}
