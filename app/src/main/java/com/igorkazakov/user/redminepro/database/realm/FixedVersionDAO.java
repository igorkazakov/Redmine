package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;

import java.util.List;

import io.realm.Realm;

public class FixedVersionDAO {

    public static void saveFixedVersions(List<FixedVersion> fixedVersions) {

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
