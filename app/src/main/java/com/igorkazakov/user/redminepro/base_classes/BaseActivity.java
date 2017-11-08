package com.igorkazakov.user.redminepro.base_classes;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Igor on 08.11.2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setBarTitle(String value){
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(value);
        }
    }

    public void setBarTitle(int resourceID){
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(resourceID);
        }
    }
}

