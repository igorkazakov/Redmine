package com.igorkazakov.user.redminepro.screen.attachment_screen;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;

/**
 * Created by Igor on 19.08.2017.
 */

public class AttachmentPresenter {

    private LifecycleHandler mLifecycleHandler;
    private AttachmentView mView;

    public AttachmentPresenter(@NonNull AttachmentView view, @NonNull LifecycleHandler lifecycleHandler) {
        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }
}
