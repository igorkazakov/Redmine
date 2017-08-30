package com.igorkazakov.user.redminepro.screen.CalendarScreen;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by Igor on 29.08.2017.
 */

public class CalendarPresenter {

    private LifecycleHandler mLifecycleHandler;
    private CalendarView mView;

    public CalendarPresenter(@NonNull LifecycleHandler lifecycleHandler, CalendarView view) {
        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }
}
