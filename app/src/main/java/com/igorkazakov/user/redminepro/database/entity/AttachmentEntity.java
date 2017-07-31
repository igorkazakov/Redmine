package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Attachment;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 31.07.17.
 */
@DatabaseTable(tableName = "AttachmentEntity")
public class AttachmentEntity {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField(columnName = "filename")
    private String filename;

    @DatabaseField(columnName = "filesize")
    private Long filesize;

    @DatabaseField(columnName = "content_type")
    private String contentType;

    @DatabaseField(columnName = "description")
    private String description;

    @DatabaseField(columnName = "content_url")
    private String contentUrl;

    @DatabaseField(columnName = "author_id")
    private long authorId;

    @DatabaseField(columnName = "created_on")
    private String createdOn;

    @DatabaseField(foreign = true, foreignAutoRefresh= true)
    protected IssueEntity parent;

    public void setParent(IssueEntity parent) {

        this.parent = parent;
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

    public long getAuthor() {
        return authorId;
    }

    public void setAuthor(long author) {
        this.authorId = author;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public static ForeignCollection<AttachmentEntity> convertItems(List<Attachment> attachmentList, IssueEntity parent) {

        if (attachmentList == null) {
            return null;
        }

        ForeignCollection<AttachmentEntity> attachmentEntityCollection = null;
        try {
            attachmentEntityCollection = DatabaseManager.getDatabaseHelper().getIssueEntityDAO().getEmptyForeignCollection("attachments");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Collection<AttachmentEntity> attachmentEntityCollection = new ArrayList<>();

        for (Attachment attachment: attachmentList) {

            AttachmentEntity attachmentEntity = new AttachmentEntity();
            attachmentEntity.setParent(parent);
            attachmentEntity.setId(attachment.getId());
            if (attachment.getAuthor() != null) {
                attachmentEntity.setAuthor(attachment.getAuthor().getId());
            }
            attachmentEntity.setContentType(attachment.getContentType());
            attachmentEntity.setCreatedOn(attachment.getCreatedOn());
            attachmentEntity.setContentUrl(attachment.getContentUrl());
            attachmentEntity.setDescription(attachment.getDescription());
            attachmentEntity.setFilename(attachment.getFilename());
            attachmentEntity.setFilesize(attachment.getFilesize());

            if (attachmentEntityCollection != null) {
                attachmentEntityCollection.add(attachmentEntity);
            }
        }

        return attachmentEntityCollection;
    }
}
