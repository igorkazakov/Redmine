package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.ShortUser;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Status;

import java.util.List;

import io.realm.Realm;

public class ShortUserRealmDAO {

    public static void saveIssues(List<ShortUser> shortUsers) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(shortUsers);
        realm.commitTransaction();
    }

    public static ShortUser getUserById(long id) {

        return Realm.getDefaultInstance()
                .where(ShortUser.class)
                .equalTo("id", id)
                .findFirst();
    }

    public static List<ShortUser> getAll() {

        return Realm.getDefaultInstance()
                .where(ShortUser.class)
                .findAll();
    }
}
