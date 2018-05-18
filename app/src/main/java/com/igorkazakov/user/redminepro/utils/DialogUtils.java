package com.igorkazakov.user.redminepro.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AlertDialog;

import com.igorkazakov.user.redminepro.R;

public class DialogUtils {

    private Dialog mErrorDialog;

    public void showErrorDialog(String message, Context context) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog);
        } else {
            builder = new AlertDialog.Builder(context);
        }

        builder.setTitle("Warning!")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, null);

        if (mErrorDialog == null || !mErrorDialog.isShowing()) {
            mErrorDialog = builder.show();
        }
    }

    public void showProggressDialog(Context context) {

    }
}
