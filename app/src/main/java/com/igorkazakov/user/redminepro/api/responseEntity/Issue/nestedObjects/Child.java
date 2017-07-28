package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 28.07.17.
 */

public class Child {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("tracker")
    @Expose
    private Tracker tracker;
    @SerializedName("subject")
    @Expose
    private String subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tracker getTracker() {
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
