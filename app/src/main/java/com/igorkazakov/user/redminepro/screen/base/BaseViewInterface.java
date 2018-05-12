package com.igorkazakov.user.redminepro.screen.base;

import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.Disposable;

/**
 * Created by user on 12.07.17.
 */

public interface BaseViewInterface extends MvpView {

    void showLoading();
    void hideLoading();
}
