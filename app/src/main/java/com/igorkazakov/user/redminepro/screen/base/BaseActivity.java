package com.igorkazakov.user.redminepro.screen.base;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.utils.DialogUtils;

public class BaseActivity extends MvpAppCompatActivity {

    private ProgressInterface mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
    }

    public void showLoading() {
        mLoadingView.showLoading();
    }

    public void showError(ApiException e) {
        DialogUtils.ShowErrorDialog(e.getMessage(), this);
        hideLoading();
    }

    public void hideLoading() {
        mLoadingView.hideLoading();
    }
}
