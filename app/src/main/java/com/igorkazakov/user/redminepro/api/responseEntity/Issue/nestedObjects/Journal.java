package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 28.07.17.
 */

public class Journal {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("user")
    @Expose
    private ShortUser user;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShortUser getUser() {
        return user;
    }

    public void setUser(ShortUser user) {
        this.user = user;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }
}
