package com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 14.07.17.
 */

public class OggyCalendarDay extends RealmObject {

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
