package com.igorkazakov.user.redminepro.utils;

import android.content.res.Resources;

/**
 * Created by Igor on 16.01.2018.
 */

public class DimentionUtils {

    public static int dp2px(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
