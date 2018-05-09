package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;

import java.util.List;

import io.realm.Realm;

public class StatusRealmDAO {

    public static void saveIssues(List<Status> statuses) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(statuses);
        realm.commitTransaction();
    }

    public static Status getStatusById(long id) {

        return Realm.getDefaultInstance()
                .where(Status.class)
                .equalTo("id", id)
                .findFirst();
    }

    public static List<Status> getAll() {

        return Realm.getDefaultInstance()
                .where(Status.class)
                .findAll();
    }
}
