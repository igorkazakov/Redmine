package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.Issue;
import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Project;

import java.util.List;

import io.realm.Realm;

public class ProjectRealmDAO {

    public static void saveIssues(List<Project> projects) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(projects);
        realm.commitTransaction();
    }

    public static List<Project> getAll() {

        return Realm.getDefaultInstance()
                .where(Project.class)
                .findAll();
    }
}
