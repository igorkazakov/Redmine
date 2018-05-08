package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Detail;
import com.igorkazakov.user.redminepro.database.DatabaseManager;
import com.igorkazakov.user.redminepro.database.dao.JournalEntityDAO;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 31.07.17.
 */
@DatabaseTable(tableName = "Journal")
public class Journal {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField(columnName = "user_id")
    private long userId;

    @DatabaseField(columnName = "user_name")
    private String userName;

    @DatabaseField(columnName = "notes")
    private String notes;

    @DatabaseField(columnName = "created_on")
    private String createdOn;

    @DatabaseField(columnName = "parent_id")
    protected long parent;

    public void setParent(long parent) {

        this.parent = parent;
    }

    public long getParent() {
        return parent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void convertDetails(List<Detail> details, Journal parent) {

        DetailEntity.convertItems(details, parent);
    }

    public static void convertItems(List<com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Journal> journalList, IssueEntity parent) {

        if (journalList == null) {
            return;
        }

        JournalEntityDAO journalEntityDAO = DatabaseManager.getDatabaseHelper().getJournalEntityDAO();
        journalEntityDAO.deleteExtraEntitiesFromBd(journalList);

        try {

            //journalEntityDAO.delete(journalEntityDAO.getAll());

            for (com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Journal journal: journalList) {

                Journal journalEntity = new Journal();
                journalEntity.setParent(parent.getId());
                journalEntity.setId(journal.getId());
                journalEntity.setCreatedOn(journal.getCreatedOn());
                journalEntity.convertDetails(journal.getDetails(), journalEntity);
                journalEntity.setNotes(journal.getNotes());

                if (journal.getUser() != null) {
                    journalEntity.setUser(journal.getUser().getId());
                    journalEntity.setUserName(journal.getUser().getName());
                }

                journalEntityDAO.createOrUpdate(journalEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
