package com.igorkazakov.user.redminepro.models;

/**
 * Created by user on 20.07.17.
 */

public class StatisticModel {

    TimeModel timeModel;
    float kpi;
    String title;

    public String getTitle() {
        return title;
    }

    public TimeModel getTimeModel() {
        return timeModel;
    }

    public float getKpi() {
        return kpi;
    }

    public StatisticModel(TimeModel timeModel, float kpi, String title) {
        this.timeModel = timeModel;
        this.kpi = kpi;
        this.title = title;
    }
}
