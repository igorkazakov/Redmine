package com.igorkazakov.user.redminepro.screen.base;

import com.arellomobile.mvp.MvpView;

/**
 * Created by user on 12.07.17.
 */

public interface BaseViewInterface extends MvpView {

    void showLoading();
    void hideLoading();
}
