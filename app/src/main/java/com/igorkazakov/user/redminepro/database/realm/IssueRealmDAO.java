package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;

import java.util.List;

import io.realm.Realm;

public class IssueRealmDAO {

    public static void saveIssues(List<Issue> issues) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(issues);
        realm.commitTransaction();
    }

    public static List<Issue> getAll() {

        return Realm.getDefaultInstance()
                .where(Issue.class)
                .findAll();
    }
}
