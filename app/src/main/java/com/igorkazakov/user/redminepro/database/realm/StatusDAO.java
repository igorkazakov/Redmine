package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;

import java.util.List;

import io.realm.Realm;

public class StatusDAO {

    public static void saveStatuses(List<Status> statuses) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(statuses);
        realm.commitTransaction();
    }

    public static Status getStatusById(long id) {

        Status item = Realm.getDefaultInstance()
                .where(Status.class)
                .equalTo("id", id)
                .findFirst();

        return Realm.getDefaultInstance().copyFromRealm(item);
    }

    public static List<Status> getAll() {

        List<Status> items = Realm.getDefaultInstance()
                .where(Status.class)
                .findAll();

        return Realm.getDefaultInstance().copyFromRealm(items);
    }
}
