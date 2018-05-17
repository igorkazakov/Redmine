package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;

import java.util.List;

import io.realm.Realm;

public class TrackerDAO {

    public static void saveTrackers(List<Tracker> trackers) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(trackers);
        realm.commitTransaction();
    }

    public static Tracker getTrackerById(long id) {

        Tracker item = Realm.getDefaultInstance()
                .where(Tracker.class)
                .equalTo("id", id)
                .findFirst();

        return Realm.getDefaultInstance().copyFromRealm(item);
    }

    public static List<Tracker> getAll() {

        List<Tracker> items = Realm.getDefaultInstance()
                .where(Tracker.class)
                .findAll();

        return Realm.getDefaultInstance().copyFromRealm(items);
    }
}
