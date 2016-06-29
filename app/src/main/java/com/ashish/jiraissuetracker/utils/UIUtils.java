package com.ashish.jiraissuetracker.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.ashish.jiraissuetracker.R;

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

    public static int loadIssueTypeImageFromIssueTypeString(String typeName, boolean subTask) {
        if (subTask) {
            return R.drawable.issue_type_bug;
        } else {
            if (typeName.equalsIgnoreCase("Bug")) {
                return R.drawable.issue_type_bug;
            } else if (typeName.equalsIgnoreCase("Epic")) {
                return R.drawable.issue_type_bug;
            } else if (typeName.equalsIgnoreCase("Story")) {
                return R.drawable.issue_type_bug;
            } else if (typeName.equalsIgnoreCase("Task")) {
                return R.drawable.issue_type_bug;
            } else {
                return R.drawable.issue_type_bug;
            }
        }
    }

    public static void hideSoftKeyboard(Activity context) {
        try {
            View v = context.getWindow().getCurrentFocus();
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            } else {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeViewTreeObserver(View view, ViewTreeObserver.OnGlobalLayoutListener victim) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(victim);
            } else {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(victim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
