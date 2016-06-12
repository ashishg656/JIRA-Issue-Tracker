package com.ashish.jiraissuetracker.utils;

import android.util.Log;

/**
 * Created by Ashish on 04/06/16.
 */
public class DebugUtils {

    public static boolean showTags = true;

    public static void log(String message) {
        if (showTags && message != null) {
            Log.w("jiraissuetracker", message);
        }
    }

    public static void logRequests(String message) {
        if (showTags && message != null) {
            Log.w("CustomStringRequest", message);
        }
    }

    public static void printToSystem(String response) {
        if (showTags && response != null) {
            System.out.println(response);
        }
    }

    public static void log(String tag, String message) {
        if (showTags && message != null && tag != null) {
            Log.w(tag, message);
        }
    }
}
