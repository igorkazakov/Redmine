package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Detail;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Journal;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by user on 31.07.17.
 */
@DatabaseTable(tableName = "JournalEntity")
public class JournalEntity {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField(columnName = "user_id")
    private long userId;

    @DatabaseField(columnName = "notes")
    private String notes;

    @DatabaseField(columnName = "created_on")
    private String createdOn;

    @ForeignCollectionField(eager = true)
    private Collection<DetailEntity> details = null;

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

    public long getUser() {
        return userId;
    }

    public void setUser(long userId) {
        this.userId = userId;
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

    public Collection<DetailEntity> getDetails() {
        return details;
    }

    public void setDetails(Collection<DetailEntity> details) {

        this.details = details;
    }

    public void convertDetails(List<Detail> details, JournalEntity parent) {

        this.details = DetailEntity.convertItems(details, parent);
    }

    public static ForeignCollection<JournalEntity> convertItems(List<Journal> journalList, IssueEntity parent) {

        if (journalList == null) {
            return null;
        }

        ForeignCollection<JournalEntity> journalEntityCollection = null;
        try {
            journalEntityCollection = DatabaseManager.getDatabaseHelper().getIssueEntityDAO().getEmptyForeignCollection("journals");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Collection<JournalEntity> journalEntityCollection = new ArrayList<>();

        for (Journal journal: journalList) {

            JournalEntity journalEntity = new JournalEntity();
            journalEntity.setParent(parent);
            journalEntity.setId(journal.getId());
            journalEntity.setCreatedOn(journal.getCreatedOn());
            journalEntity.convertDetails(journal.getDetails(), journalEntity);
            journalEntity.setNotes(journal.getNotes());
            journalEntity.setUser(journal.getUser().getId());

            if (journalEntityCollection != null) {
                journalEntityCollection.add(journalEntity);
            }
        }

        return journalEntityCollection;
    }
}
