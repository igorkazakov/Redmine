package com.igorkazakov.user.redminepro.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.igorkazakov.user.redminepro.BuildConfig;
import com.igorkazakov.user.redminepro.R;

/**
 * Created by user on 21.07.17.
 */

public class ColorUtils {

    public static int getColorForKpi(float kpi, Context context) {

        if (kpi >= BuildConfig.NORMAL_KPI) {
            return ContextCompat.getColor(context, R.color.colorRegular);

        } else {
            return ContextCompat.getColor(context, R.color.colorFuckup);
        }
    }
}
