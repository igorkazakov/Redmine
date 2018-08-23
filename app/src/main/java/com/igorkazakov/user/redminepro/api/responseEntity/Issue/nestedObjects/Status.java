package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 25.07.17.
 */

public class Status implements Namable {

    @SerializedName("id")
    @Expose
    private Long statusId;
    @SerializedName("name")
    @Expose
    private String name;

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long id) {
        this.statusId = id;
    }

    @Override
    public String getName() {
        return name != null ? name : "";
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
