package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;

import java.util.List;

import io.realm.Realm;

public class ProjectPriorityRealmDAO {

    public static void saveIssues(List<Priority> priorities) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(priorities);
        realm.commitTransaction();
    }

    public static Priority getPriorityById(long id) {

        return Realm.getDefaultInstance()
                .where(Priority.class)
                .equalTo("id", id)
                .findFirst();
    }

    public static List<Priority> getAll() {

        return Realm.getDefaultInstance()
                .where(Priority.class)
                .findAll();
    }
}
