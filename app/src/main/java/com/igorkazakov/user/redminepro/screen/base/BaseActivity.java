package com.igorkazakov.user.redminepro.screen.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.ApiException;
import com.igorkazakov.user.redminepro.application.RedmineApplication;
import com.igorkazakov.user.redminepro.utils.DialogUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends MvpAppCompatActivity {

    @Inject
    DialogUtils mDialogUtils;

    @BindView(R.id.main_wrapper)
    FrameLayout mMainWrapper;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ProgressInterface mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_base);
        getLayoutInflater().inflate(getMainContentLayout(), findViewById(R.id.main_wrapper));

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        RedmineApplication.getComponent().inject(this);

        if (mMainWrapper != null) {
            mLoadingView = new LoadingFragment(this, mMainWrapper);

        } else {
            mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        }
    }

    public abstract int getMainContentLayout();

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
