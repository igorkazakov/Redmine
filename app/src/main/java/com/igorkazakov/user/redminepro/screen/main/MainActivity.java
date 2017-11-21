package com.igorkazakov.user.redminepro.screen.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.base_classes.BaseFragmentActivity;
import com.igorkazakov.user.redminepro.screen.calendar_screen.CalendarFragment;
import com.igorkazakov.user.redminepro.screen.dashboard.DashboardFragment;
import com.igorkazakov.user.redminepro.screen.issues.IssuesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Igor on 07.11.2017.
 */

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_dashboard:
                            showFragment(DashboardFragment.newInstance(),
                                    getResources().getString(R.string.title_activity_dashboard));
                            break;
                        case R.id.action_calendar:
                            showFragment(CalendarFragment.newInstance(),
                                    getResources().getString(R.string.title_activity_calendar));
                            break;
                        case R.id.action_issues:
                            showFragment(IssuesFragment.newInstance(),
                                    getResources().getString(R.string.title_activity_issues));
                            break;
                    }
                    return true;
                });

        showFragment(DashboardFragment.newInstance(),
                getResources().getString(R.string.title_activity_dashboard));
    }
}
