package com.igorkazakov.user.redminepro.models;

public class ChartModel {

    private TimeModel mTimeModel;
    private Float mKpi;

    public ChartModel(TimeModel model, Float kpi) {
        mTimeModel = model;
        mKpi = kpi;
    }

    public TimeModel getTimeModel() {
        return mTimeModel;
    }

    public Float getKpi() {
        return mKpi;
    }
}
