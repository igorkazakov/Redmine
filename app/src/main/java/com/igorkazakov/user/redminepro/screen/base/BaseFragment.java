package com.igorkazakov.user.redminepro.screen.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.utils.DialogUtils;

public class BaseFragment extends MvpAppCompatFragment {

    private ProgressInterface mLoadingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mLoadingView = new LoadingFragment(getActivity(), container);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showLoading() {
        mLoadingView.showLoading();
    }

    public void showError(ApiException e) {
        DialogUtils.ShowErrorDialog(e.getMessage(), getActivity());
        hideLoading();
    }

    public void hideLoading() {
        mLoadingView.hideLoading();
    }
}
