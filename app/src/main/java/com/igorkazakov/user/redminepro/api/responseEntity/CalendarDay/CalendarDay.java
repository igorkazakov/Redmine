package com.igorkazakov.user.redminepro.api.responseEntity.CalendarDay;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 14.07.17.
 */

public class CalendarDay {


    @SerializedName("id")
    private Long id;

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("date")
    private String date;

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
}
