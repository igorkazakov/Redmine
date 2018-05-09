package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Tracker;

import java.util.List;

import io.realm.Realm;

public class TrackerRealmDAO {

    public static void saveIssues(List<Tracker> trackers) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(trackers);
        realm.commitTransaction();
    }

    public static Tracker getTrackerById(long id) {

        return Realm.getDefaultInstance()
                .where(Tracker.class)
                .equalTo("id", id)
                .findFirst();
    }

    public static List<Tracker> getAll() {

        return Realm.getDefaultInstance()
                .where(Tracker.class)
                .findAll();
    }
}
