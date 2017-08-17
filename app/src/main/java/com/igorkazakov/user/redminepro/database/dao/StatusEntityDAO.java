package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.StatusEntity;
import com.igorkazakov.user.redminepro.database.entity.TimeEntryEntity;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.igorkazakov.user.redminepro.models.TimeModel;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 09.08.17.
 */

public class StatusEntityDAO extends BaseDaoImpl<StatusEntity, Long> {

    public StatusEntityDAO(ConnectionSource connectionSource, Class<StatusEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<StatusEntity> getAll() {
        List<StatusEntity> statusEntities = new ArrayList();
        try {
            statusEntities = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return statusEntities;
        }
    }

    public void saveStatusEntity(StatusEntity statusEntity) {

        if (statusEntity == null) {
            return;
        }

        try {

            createOrUpdate(statusEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveStatusEntities(List<StatusEntity> timeEntries) {

        if (timeEntries == null) {
            return;
        }

        for (StatusEntity timeEntryEntity : timeEntries) {
            try {
                this.createOrUpdate(timeEntryEntity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<StatusEntity> getStatusesByParent(long parent) {

        List<StatusEntity> statusEntities= new ArrayList<>();

        try {
            statusEntities = this.queryBuilder().where().eq("parent_id", parent).query();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statusEntities;
    }
}