package com.igorkazakov.user.redminepro.utils;

/**
 * Created by user on 17.07.17.
 */

public class TimeModel {

    private float regularTime;
    private float fuckupTime;
    private float teamFuckupTime;

    public TimeModel(float regularTime, float fuckupTime, float teamFuckupTime) {
        this.regularTime = regularTime;
        this.fuckupTime = fuckupTime;
        this.teamFuckupTime = teamFuckupTime;
    }

    public void setRegularTime(float regularTime) {
        this.regularTime = regularTime;
    }

    public void setFuckupTime(float fuckupTime) {
        this.fuckupTime = fuckupTime;
    }

    public void setTeamFuckupTime(float teamFuckupTime) {
        this.teamFuckupTime = teamFuckupTime;
    }

    public float getRegularTime() {
        return regularTime;
    }

    public float getFuckupTime() {
        return fuckupTime;
    }

    public float getTeamFuckupTime() {
        return teamFuckupTime;
    }
}
