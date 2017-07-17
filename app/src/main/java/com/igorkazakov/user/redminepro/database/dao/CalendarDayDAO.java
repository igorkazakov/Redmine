package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.CalendarDayEntity;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.utils.TimeInterval;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14.07.17.
 */

public class CalendarDayDAO extends BaseDaoImpl<CalendarDayEntity, Long> {

    public CalendarDayDAO(ConnectionSource connectionSource, Class<CalendarDayEntity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<CalendarDayEntity> getAll() {
        List<CalendarDayEntity> calendarDayEntityList = new ArrayList();
        try {
            calendarDayEntityList = this.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return calendarDayEntityList;
        }
    }

    public void saveCalendarDay(CalendarDayEntity calendarDayEntity) {

        if (calendarDayEntity == null) {
            return;
        }

        try {

            createOrUpdate(calendarDayEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveCalendarDays(List<CalendarDayEntity> calendarDayEntities) {

        if (calendarDayEntities == null) {
            return;
        }

        for (CalendarDayEntity calendarDayEntity : calendarDayEntities) {
            try {
                this.createOrUpdate(calendarDayEntity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public long getHoursNormForInterval(TimeInterval interval) {

        long result = 0;

        String sqlLong = " select sum(hours) as rt from CalendarDayEntity where " +
                " CalendarDayEntity.date >= ? and CalendarDayEntity.date <= ?";

        try {

            String startDate = DateUtils.stringFromDate(interval.getStart(), DateUtils.getSimpleFormatter());
            String endDate = DateUtils.stringFromDate(interval.getEnd(), DateUtils.getSimpleFormatter());
            result = this.queryRawValue(sqlLong, startDate, endDate);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}