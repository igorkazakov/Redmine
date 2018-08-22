package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.database.room.entity.FixedVersionEntity;

import java.util.List;

/**
 * Created by user on 15.08.17.
 */

public class VersionsResponse {

    @SerializedName("versions")
    private List<FixedVersionEntity> fixedVersions;

    public List<FixedVersionEntity> getFixedVersions() {
        return fixedVersions;
    }

    public void setFixedVersions(List<FixedVersionEntity> fixedVersions) {
        this.fixedVersions = fixedVersions;
    }
}
