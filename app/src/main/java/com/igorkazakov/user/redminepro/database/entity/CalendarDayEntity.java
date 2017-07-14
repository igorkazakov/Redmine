package com.igorkazakov.user.redminepro.database.entity;

import com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay.CalendarDay;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14.07.17.
 */
@DatabaseTable(tableName = "CalendarDayEntity")
public class CalendarDayEntity {


    @DatabaseField(id = true)
    private Long id;

    @DatabaseField(columnName = "user_id")
    private Long userId;

    @DatabaseField(columnName = "date")
    private String date;

    @DatabaseField(columnName = "type")
    private String type;

    @DatabaseField(columnName = "hours")
    private Float hours;

    @DatabaseField(columnName = "comment")
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getHours() {
        return hours;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



    public static List<CalendarDayEntity> convertItems(List<CalendarDay> items) {

        if (items == null) {
            return null;
        }

        ArrayList<CalendarDayEntity> times = new ArrayList<>();
        for (CalendarDay item : items) {

            CalendarDayEntity entity = new CalendarDayEntity();
            entity.setId(item.getId());
            entity.setUserId(item.getUserId());
            entity.setDate(item.getDate());
            entity.setType(item.getType());
            entity.setHours(item.getHours());
            entity.setComment(item.getComment());

            times.add(entity);
        }

        return times;
    }
}
