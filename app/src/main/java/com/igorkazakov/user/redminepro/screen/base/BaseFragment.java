package com.igorkazakov.user.redminepro.screen.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.utils.DialogUtils;

import javax.inject.Inject;

public class BaseFragment extends MvpAppCompatFragment {

    @Inject
    DialogUtils mDialogUtils;

    private ProgressInterface mLoadingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RedmineApplication.getComponent().inject(this);
        mLoadingView = new LoadingFragment(getActivity(), container);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showLoading() {
        mLoadingView.showLoading();
    }

    public void showError(ApiException e) {
        mDialogUtils.showErrorDialog(e.getMessage(), getActivity());
        hideLoading();
    }

    public void hideLoading() {
        mLoadingView.hideLoading();
    }
}
