package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Child;

import java.util.List;

import io.realm.Realm;

public class IssueDAO {

    public static void saveIssues(List<Issue> issues) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(issues);
        realm.commitTransaction();
    }

    public static void saveIssue(Issue issue) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(issue);
        realm.commitTransaction();
    }

    public static List<Issue> getAll() {

        return Realm.getDefaultInstance()
                .where(Issue.class)
                .findAll();
    }

    public static List<Issue> getChildIssues(List<Child> children) {

        Long[] childIssueIds = new Long[children.size()];
        for (Child child: children) {
            childIssueIds[children.indexOf(child)] = child.getId();
        }

        return Realm.getDefaultInstance()
                .where(Issue.class)
                .in("id", childIssueIds)
                .findAll();
    }
}
