package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.AuthUser;

/**
 * Created by user on 12.07.17.
 */

public class LoginResponse {

    @SerializedName("user")
    private AuthUser user;

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }
}
