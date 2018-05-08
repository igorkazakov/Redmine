package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.Journal;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 31.07.17.
 */

public class JournalEntityDAO extends BaseDaoImpl<Journal, Long> {

    public JournalEntityDAO(ConnectionSource connectionSource, Class<Journal> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Journal> getAll() {
        List<Journal> entityList = new ArrayList();
        try {
            entityList = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return entityList;
        }
    }

    public void saveJournalEntity(Journal entity) {

        if (entity == null) {
            return;
        }

        try {

            createOrUpdate(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveJournalEntities(List<Journal> entityList) {

        if (entityList == null) {
            return;
        }

        for (Journal item : entityList) {
            try {
                this.createOrUpdate(item);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteExtraEntitiesFromBd(List<com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Journal> journalList) {

        Set<Long> set = new HashSet<>();
        for (com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Journal journal: journalList) {
            set.add(journal.getId());
        }

        try {
            List<Journal> journalEntities = this.queryBuilder().where().not().in("id", set).query();
            delete(journalEntities);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Journal> getjournalsByParent(long parent) {

        List<Journal> journalEntities= new ArrayList<>();

        try {
            journalEntities = this.queryBuilder().where().eq("parent_id", parent).query();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return journalEntities;
    }
}