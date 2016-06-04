package com.ashish.jiraissuetracker.utils;

import android.util.Log;

/**
 * Created by Ashish on 04/06/16.
 */
public class DebugUtils {

    static boolean showTags = true;

    public static void log(String message) {
        if (showTags) {
            Log.w("jiraissuetracker", message);
        }
    }
}
