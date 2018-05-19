package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.IssueDetail;

import io.realm.Realm;

public class IssueDetailDAO {

    public static void saveIssueDetail(IssueDetail issue) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(issue);
        realm.commitTransaction();
    }

    public static IssueDetail getIssueDetailById(long id) {

        return Realm.getDefaultInstance()
                .where(IssueDetail.class)
                .equalTo("id", id)
                .findFirst();
    }
}
