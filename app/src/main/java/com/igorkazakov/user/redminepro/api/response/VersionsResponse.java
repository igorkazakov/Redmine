package com.igorkazakov.user.redminepro.api.response;

import com.google.gson.annotations.SerializedName;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;

import java.util.List;

/**
 * Created by user on 15.08.17.
 */

public class VersionsResponse {

    @SerializedName("versions")
    private List<FixedVersion> fixedVersions;

    public List<FixedVersion> getFixedVersions() {
        return fixedVersions;
    }

    public void setFixedVersions(List<FixedVersion> fixedVersions) {
        this.fixedVersions = fixedVersions;
    }
}
