package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.FixedVersion;

import java.util.List;

import io.realm.Realm;

public class FixedVersionDAO {

    public static void saveFixedVersions(List<FixedVersion> fixedVersions) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(fixedVersions);
        realm.commitTransaction();
    }

    public static FixedVersion getFixedVersionById(long id) {

        FixedVersion item = Realm.getDefaultInstance()
                .where(FixedVersion.class)
                .equalTo("id", id)
                .findFirst();

        return Realm.getDefaultInstance().copyFromRealm(item);
    }

    public static List<FixedVersion> getAll() {

        List<FixedVersion> items = Realm.getDefaultInstance()
                .where(FixedVersion.class)
                .findAll();

        return Realm.getDefaultInstance().copyFromRealm(items);
    }
}
