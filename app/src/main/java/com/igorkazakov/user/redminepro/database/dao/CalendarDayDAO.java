package com.igorkazakov.user.redminepro.database.dao;

import com.igorkazakov.user.redminepro.database.entity.CalendarDayEntity;
import com.igorkazakov.user.redminepro.utils.DateUtils;
import com.igorkazakov.user.redminepro.models.TimeInterval;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

    public long getHoursNormForDate(Date date) {

        long result = 0;

        String sqlLong = " select sum(hours) as rt from CalendarDayEntity where " +
                " CalendarDayEntity.date == ? ";

        try {

            String queryDate = DateUtils.stringFromDate(date, DateUtils.getSimpleFormatter());

            result = this.queryRawValue(sqlLong, queryDate);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

//    public long getHoursNormForIntervalWithObjects(TimeInterval interval) {
//
//        List<CalendarDayEntity> calendarDayEntityList = new ArrayList();
//        long result = 0;
//
//        try {
//
//            String startDate = DateUtils.stringFromDate(interval.getStart(), DateUtils.getSimpleFormatter());
//            String endDate = DateUtils.stringFromDate(interval.getEnd(), DateUtils.getSimpleFormatter());
//            calendarDayEntityList = this.queryBuilder().where().
//                    between("date", startDate, endDate).query();
//
//            for (int i = 0; i < calendarDayEntityList.size(); i++) {
//
//                CalendarDayEntity item = calendarDayEntityList.get(i);
//                result += item.getHours();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            return result;
//        }
//    }
}