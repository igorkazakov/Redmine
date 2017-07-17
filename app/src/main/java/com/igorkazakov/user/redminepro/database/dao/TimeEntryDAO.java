package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.TimeEntryEntity;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.TimeInterval;
import com.igorkazakov.user.redminepro.utils.TimeModel;
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

    public TimeModel getWorkHoursWithInterval(TimeInterval interval) {

        TimeModel model = new TimeModel(0, 0, 0);

        String sqlLong = " select sum(hours) as rt from TimeEntryEntity where " +
                " TimeEntryEntity.type = ? and TimeEntryEntity.spent_on >= ? and TimeEntryEntity.spent_on <= ?";

        try {

            String startDate = DateUtils.stringFromDate(interval.getStart(), DateUtils.getSimpleFormatter());
            String endDate = DateUtils.stringFromDate(interval.getEnd(), DateUtils.getSimpleFormatter());

            long regular = this.queryRawValue(sqlLong, TimeEntryEntity.TimeType.REGULAR.getValue(), startDate, endDate);
            long fuckup = this.queryRawValue(sqlLong, TimeEntryEntity.TimeType.FUCKUP.getValue(), startDate, endDate);
            long teamFuckup = this.queryRawValue(sqlLong, TimeEntryEntity.TimeType.TEAMFUCKUP.getValue(), startDate, endDate);

            model.setRegularTime(regular);
            model.setFuckupTime(fuckup);
            model.setTeamFuckupTime(teamFuckup);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
}



