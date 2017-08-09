package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Membership;

import java.util.List;

/**
 * Created by user on 09.08.17.
 */

public class MembershipsResponse {

    @SerializedName("memberships")
    @Expose
    private List<Membership> memberships;

    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }
}

