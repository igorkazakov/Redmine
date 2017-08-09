package com.igorkazakov.user.redminepro.api.responseEntity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;

/**
 * Created by user on 09.08.17.
 */

public class Membership {

    @SerializedName("user")
    @Expose
    private ShortUser user;

    public ShortUser getUser() {
        return user;
    }
}
