package com.igorkazakov.user.redminepro.screen.general;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.igorkazakov.user.redminepro.R;

/**
 * Created by user on 21.07.17.
 */

public class LoadingFragment implements LoadingView {

    private View loadingView;
    private ViewGroup container;

    public LoadingFragment(Activity activity, ViewGroup container) {

        LinearLayout linearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(linLayoutParam);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        loadingView = layoutInflater.inflate(R.layout.fragment_loading, linearLayout, false);
        this.container = container;
    }

    @Override
    public void showLoading() {
        container.addView(loadingView);
    }

    @Override
    public void hideLoading() {
        container.removeView(loadingView);
    }
}
