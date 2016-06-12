package com.ashish.jiraissuetracker.requests;

import android.content.Context;
import android.util.Base64;

import com.android.volley.Request;
import com.ashish.jiraissuetracker.application.AppApplication;
import com.ashish.jiraissuetracker.extras.AppUrls;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.serverApi.AppRequestListenerJsonObject;
import com.ashish.jiraissuetracker.serverApi.CustomJsonObjectRequest;
import com.ashish.jiraissuetracker.serverApi.CustomStringRequest;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Ashish on 04/06/16.
 */
public class AppRequests implements RequestTags {

    public static void makeLoginRequest(String urlJira, String userName, String password, AppRequestListener requestListener, Context context) {
        ZPreferences.setBaseUrl(context, urlJira);
        urlJira = urlJira + AppUrls.LOGIN_URL;

        String headerToken = userName + ":" + password;
        headerToken = Base64.encodeToString(headerToken.toString().getBytes(), Base64.DEFAULT);
        headerToken = "Basic " + headerToken;

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", headerToken);

        ZPreferences.setHeaderTokenAuth(context, headerToken);

        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, urlJira, LOGIN_REQUEST, requestListener, null, headers);
        AppApplication.getInstance().addToRequestQueue(request, LOGIN_REQUEST);
    }

    public static void makeIssuesRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url,
                ISSUES_REQUEST, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, ISSUES_REQUEST);
    }

    public static void makeGetAllStatusForProjectRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url,
                GET_ALL_STATUS_FOR_PROJECT, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, GET_ALL_STATUS_FOR_PROJECT);
    }

    public static void makeGetIssueTransitionsRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url,
                GET_TRANSITIONS_FOR_ISSUE, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, GET_TRANSITIONS_FOR_ISSUE);
    }

    public static void makePostIssueTransitionsRequest(String url, AppRequestListenerJsonObject requestListener, Context context, JSONObject jsonObject) {
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST, url, jsonObject,
                POST_TRANSITIONS_FOR_ISSUE, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, POST_TRANSITIONS_FOR_ISSUE);
    }

    public static void makeActivityStreamRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url,
                ACTIVITY_STREAM, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, ACTIVITY_STREAM);
    }

    public static HashMap<String, String> getHeader(Context context) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", ZPreferences.getHeaderTokenAuth(context));
        return headers;
    }
}
