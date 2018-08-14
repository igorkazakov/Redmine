package com.igorkazakov.user.redminepro.database.realm;

import com.igorkazakov.user.redminepro.api.responseEntity.Issue.nestedObjects.Project;

import java.util.List;

import io.realm.Realm;

public class ProjectDAO {

    public static void saveProjects(List<Project> projects) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(projects);
        realm.commitTransaction();
    }

    public static List<Project> getAll() {

        List<Project> items = Realm.getDefaultInstance()
                .where(Project.class)
                .findAll();

        return Realm.getDefaultInstance().copyFromRealm(items);
    }
}
