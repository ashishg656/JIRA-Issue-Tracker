package com.ashish.jiraissuetracker.utils;

import android.content.Context;

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

    public static void showNoInternetDialog(String text, Context context) {
        
    }
}
