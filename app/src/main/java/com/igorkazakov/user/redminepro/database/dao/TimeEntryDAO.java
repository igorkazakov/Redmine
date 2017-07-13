package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.TimeEntryEntity;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 13.07.17.
 */

public final class TimeEntryDAO extends BaseDaoImpl<TimeEntryEntity, Long> {

    public TimeEntryDAO(ConnectionSource connectionSource, Class<TimeEntryEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<TimeEntryEntity> getAll() {
        List<TimeEntryEntity> timeEntryEntities = new ArrayList();
        try {
            timeEntryEntities = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return timeEntryEntities;
        }
    }

    public void saveTimeEntry(TimeEntryEntity timeEntryEntity) {

        if (timeEntryEntity == null) {
            return;
        }

        try {

            createOrUpdate(timeEntryEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveTimeEntries(List<TimeEntryEntity> timeEntries) {

        if (timeEntries == null) {
            return;
        }

        for (TimeEntryEntity timeEntryEntity : timeEntries) {
            try {
                this.createOrUpdate(timeEntryEntity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
