package com.igorkazakov.user.redminepro.database.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.database.room.converters.DateConverter;

import java.util.Date;

@Entity
@TypeConverters({DateConverter.class})
public class OggyCalendarDayEntity {

    public static final String WORK = "work";
    public static final String HOLIDAY = "holiday";
    public static final String FEAST = "feast";
    public static final String VACATION = "vacation";
    public static final String HOSPITAL = "hospital";

    @SerializedName("id")
    @PrimaryKey
    private Long id;

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("date")
    private Date date;

    @SerializedName("type")
    private String type;

    @SerializedName("hours")
    private Float hours;

    @SerializedName("comment")
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}