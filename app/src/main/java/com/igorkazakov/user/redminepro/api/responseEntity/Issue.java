package com.igorkazakov.user.redminepro.api.responseEntity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 13.07.17.
 */

public class Issue {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @SerializedName("id")
    private long id;
}
