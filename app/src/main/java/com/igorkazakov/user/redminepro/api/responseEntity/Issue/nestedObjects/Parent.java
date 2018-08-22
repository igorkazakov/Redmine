package com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parent {

    @SerializedName("id")
    @Expose
    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long id) {
        this.parentId = id;
    }
}
