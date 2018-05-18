package com.igorkazakov.user.redminepro.screen.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.utils.DialogUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends MvpAppCompatActivity {

    @Inject
    DialogUtils mDialogUtils;

    @Nullable
    @BindView(R.id.contentView)
    FrameLayout mContentView;

    private ProgressInterface mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        RedmineApplication.getComponent().inject(this);

        if (mContentView != null) {
            mLoadingView = new LoadingFragment(this, mContentView);

        } else {
            mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        }
    }

    public void showLoading() {
        mLoadingView.showLoading();
    }

    public void showError(ApiException e) {
        mDialogUtils.showErrorDialog(e.getMessage(), this);
        hideLoading();
    }

    public void hideLoading() {
        mLoadingView.hideLoading();
    }
}
