package com.igorkazakov.user.redminepro.base_classes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.igorkazakov.user.redminepro.R;

/**
 * Created by Igor on 08.11.2017.
 */

public class BaseFragmentActivity extends BaseActivity {

    protected Fragment mCurrentFragment;

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_fragments, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        mCurrentFragment = fragment;
    }

    public void showFragment(Fragment fragment, int titleResourceID){
        showFragment(fragment);
        setBarTitle(titleResourceID);
    }

    public void showFragment(Fragment fragment, String title) {
        showFragment(fragment);
        setBarTitle(title);
    }

    public void removeCurrentFragment() {

        if (mCurrentFragment == null) {
            return;
        }

        setBarTitle(null);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mCurrentFragment);
        fragmentTransaction.commitAllowingStateLoss();

        mCurrentFragment = null;
    }
}

