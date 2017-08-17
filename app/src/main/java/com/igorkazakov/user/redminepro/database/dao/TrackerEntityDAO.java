package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.TimeEntryEntity;
import com.igorkazakov.user.redminepro.database.entity.TrackerEntity;
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

public class TrackerEntityDAO extends BaseDaoImpl<TrackerEntity, Long> {

    public TrackerEntityDAO(ConnectionSource connectionSource, Class<TrackerEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<TrackerEntity> getAll() {
        List<TrackerEntity> trackerEntities = new ArrayList();
        try {
            trackerEntities = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return trackerEntities;
        }
    }

    public void saveTrackerEntity(TrackerEntity trackerEntity) {

        if (trackerEntity == null) {
            return;
        }

        try {

            createOrUpdate(trackerEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveTrackerEntities(List<TrackerEntity> trackerEntities) {

        if (trackerEntities == null) {
            return;
        }

        for (TrackerEntity timeEntryEntity : trackerEntities) {
            try {
                this.createOrUpdate(timeEntryEntity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<TrackerEntity> getTrackersByParent(long parent) {

        List<TrackerEntity> trackerEntities= new ArrayList<>();

        try {
            trackerEntities = this.queryBuilder().where().eq("parent_id", parent).query();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trackerEntities;
    }
}