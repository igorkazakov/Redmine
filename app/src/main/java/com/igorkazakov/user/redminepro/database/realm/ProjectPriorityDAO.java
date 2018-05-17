package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Priority;

import java.util.List;

import io.realm.Realm;

public class ProjectPriorityDAO {

    public static void saveIProjectPriorities(List<Priority> priorities) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(priorities);
        realm.commitTransaction();
    }

    public static Priority getPriorityById(long id) {

        Priority item = Realm.getDefaultInstance()
                .where(Priority.class)
                .equalTo("id", id)
                .findFirst();

        return Realm.getDefaultInstance().copyFromRealm(item);
    }

    public static List<Priority> getAll() {

        List<Priority> items = Realm.getDefaultInstance()
                .where(Priority.class)
                .findAll();

        return Realm.getDefaultInstance().copyFromRealm(items);
    }
}
