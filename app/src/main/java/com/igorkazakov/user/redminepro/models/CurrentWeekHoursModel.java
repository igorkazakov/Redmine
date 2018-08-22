package com.igorkazakov.user.redminepro.models;

public class CurrentWeekHoursModel {

    private float mCurrentWeekHoursNorm;
    private float mRemainHoursForNormalKpi;
    private float mRemainHoursForWeek;

    public CurrentWeekHoursModel(float currentWeekHoursNorm,
                                 float remainHoursForNormalKpi,
                                 float remainHoursForWeek) {

        this.mCurrentWeekHoursNorm = currentWeekHoursNorm;
        this.mRemainHoursForNormalKpi = remainHoursForNormalKpi;
        this.mRemainHoursForWeek = remainHoursForWeek;
    }

    public float getCurrentWeekHoursNorm() {
        return mCurrentWeekHoursNorm;
    }

    public float getRemainHoursForNormalKpi() {
        return mRemainHoursForNormalKpi;
    }

    public float getRemainHoursForWeek() {
        return mRemainHoursForWeek;
    }
}
