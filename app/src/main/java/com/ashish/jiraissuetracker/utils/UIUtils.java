package com.ashish.jiraissuetracker.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

/**
 * Created by Ashish on 11/05/16.
 */
public class UIUtils {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void openAndroidSettingsScreen(Context context) {
        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }
}
