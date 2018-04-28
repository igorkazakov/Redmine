package com.igorkazakov.user.redminepro.api.responseEntity.TimeEntry.nestedObjects;

/**
 * Created by user on 13.07.17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TimeEntryIssue extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
