package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;

import java.util.List;

import io.realm.Realm;

public class FixedVersionRealmDAO {

    public static void saveIssues(List<FixedVersion> fixedVersions) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(fixedVersions);
        realm.commitTransaction();
    }

    public static FixedVersion getFixedVersionById(long id) {

        return Realm.getDefaultInstance()
                .where(FixedVersion.class)
                .equalTo("id", id)
                .findFirst();
    }

    public static List<FixedVersion> getAll() {

        return Realm.getDefaultInstance()
                .where(FixedVersion.class)
                .findAll();
    }
}
