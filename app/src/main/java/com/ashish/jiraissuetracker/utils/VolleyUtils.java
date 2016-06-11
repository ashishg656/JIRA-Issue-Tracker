package com.ashish.jiraissuetracker.utils;

import com.android.volley.Cache;
import com.ashish.jiraissuetracker.application.AppApplication;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by Ashish on 10/05/16.
 */
public class VolleyUtils {

    public static Object getResponseObject(String response, Class objectClass) {
        return new Gson().fromJson(response, objectClass);
    }

    public static Object getResponseFromCache(Class objectClass, String url) throws Exception {
        try {
            Cache.Entry entry = AppApplication.getInstance()
                    .getRequestQueue().getCache().get(url);
            String data = new String(entry.data, "UTF-8");
            return new Gson().fromJson(data,
                    objectClass);
        } catch (Exception e) {
            throw e;
        }
    }
}
