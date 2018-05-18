package com.igorkazakov.user.redminepro.screen.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.screen.calendar.CalendarFragment;
import com.igorkazakov.user.redminepro.screen.dashboard.DashboardFragment;
import com.igorkazakov.user.redminepro.screen.issues.IssuesFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Igor on 07.11.2017.
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_dashboard:
                            showOrCreateFragment(DashboardFragment.class);
                            break;
                        case R.id.action_calendar:
                            showOrCreateFragment(CalendarFragment.class);
                            break;
                        case R.id.action_issues:
                            showOrCreateFragment(IssuesFragment.class);
                            break;
                    }
                    return true;
                });

        showOrCreateFragment(DashboardFragment.class);
    }

    public void showOrCreateFragment(Class fragmentClass){

        boolean fragmentExist = false;
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment: fragments) {

            if (fragment.getClass().toString().equalsIgnoreCase(fragmentClass.toString())) {
                fragmentExist = true;

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.show(fragment).commit();
                fragment.onResume();

            } else {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(fragment).commit();
            }
        }

        if (!fragmentExist) {
            try {
                Fragment fragment = (Fragment) fragmentClass.newInstance();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frame_layout_fragments, fragment).commit();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
