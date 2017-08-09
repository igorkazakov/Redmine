package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.ProjectEntity;
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

public class ProjectEntityDAO extends BaseDaoImpl<ProjectEntity, Long> {

    public ProjectEntityDAO(ConnectionSource connectionSource, Class<ProjectEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<ProjectEntity> getAll() {
        List<ProjectEntity> projectEntities = new ArrayList();
        try {
            projectEntities = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return projectEntities;
        }
    }

    public void saveProjectEntity(ProjectEntity projectEntity) {

        if (projectEntity == null) {
            return;
        }

        try {

            createOrUpdate(projectEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveProjectEntities(List<ProjectEntity> projectEntities) {

        if (projectEntities == null) {
            return;
        }

        for (ProjectEntity projectEntity : projectEntities) {
            try {
                this.createOrUpdate(projectEntity);
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

    public TimeModel getWorkHoursWithDate(Date date) {

        TimeModel model = new TimeModel(0, 0, 0);

        String sqlLong = " select sum(hours) as rt from TimeEntryEntity where " +
                " TimeEntryEntity.type = ? and TimeEntryEntity.spent_on == ? ";

        try {

            String queryDate = DateUtils.stringFromDate(date, DateUtils.getSimpleFormatter());

            long regular = this.queryRawValue(sqlLong, TimeEntryEntity.TimeType.REGULAR.getValue(), queryDate);
            long fuckup = this.queryRawValue(sqlLong, TimeEntryEntity.TimeType.FUCKUP.getValue(), queryDate);
            long teamFuckup = this.queryRawValue(sqlLong, TimeEntryEntity.TimeType.TEAMFUCKUP.getValue(), queryDate);

            model.setRegularTime(regular);
            model.setFuckupTime(fuckup);
            model.setTeamFuckupTime(teamFuckup);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
}