package com.igorkazakov.user.redminepro.api.responseEntity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.database.room.entity.ShortUserEntity;

/**
 * Created by user on 09.08.17.
 */

public class Membership {

    @SerializedName("user")
    @Expose
    private ShortUserEntity user;

    public ShortUserEntity getUser() {
        return user;
    }
}
