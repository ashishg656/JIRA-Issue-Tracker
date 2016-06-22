package com.ashish.jiraissuetracker.requests;

import android.content.Context;
import android.util.Base64;

import com.android.volley.Request;
import com.ashish.jiraissuetracker.application.AppApplication;
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

    public static void makeGetUserProfileRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url,
                USER_PROFILE, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, USER_PROFILE);
    }

    public static void makeGetAllProjectsRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url,
                GET_ALL_PROJECTS, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, GET_ALL_PROJECTS);
    }

    public static void makeGetProjectDetailsRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url,
                GET_PROJECT_DETAILS, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, GET_PROJECT_DETAILS);
    }

    public static void makeGetIssuesForProjectRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url,
                ISSUES_REQUEST_FOR_PROJECT, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, ISSUES_REQUEST_FOR_PROJECT);
    }

    public static void makeGetIssueDetailRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url,
                ISSUE_DETAIL_REQUEST, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, ISSUE_DETAIL_REQUEST);
    }

    public static void makeRemoveVoteFromIssueRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.DELETE, url,
                REMOVE_VOTE_FROM_ISSUE, requestListener, null, getHeaderWithContentType(context));
        AppApplication.getInstance().addToRequestQueue(request, REMOVE_VOTE_FROM_ISSUE);
    }

    public static void makeAddVoteToIssueRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.POST, url,
                ADD_VOTE_TO_ISSUE, requestListener, null, getHeaderWithContentType(context));
        AppApplication.getInstance().addToRequestQueue(request, ADD_VOTE_TO_ISSUE);
    }

    public static void makeRemoveWatchingFromIssueRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.DELETE, url,
                REMOVE_WATCH_FROM_ISSUE, requestListener, null, getHeaderWithContentType(context));
        AppApplication.getInstance().addToRequestQueue(request, REMOVE_WATCH_FROM_ISSUE);
    }

    public static void makeAddWatchingToIssueRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.POST, url,
                ADD_WATCH_TO_ISSUE, requestListener, null, getHeaderWithContentType(context));
        AppApplication.getInstance().addToRequestQueue(request, ADD_WATCH_TO_ISSUE);
    }

    public static void makeGetAllIssueCommentsRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url,
                GET_COMMENTS_FOR_ISSUE, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, GET_COMMENTS_FOR_ISSUE);
    }

    public static void makeAddCommentOnIssueRequest(String url, AppRequestListenerJsonObject requestListener, Context context, JSONObject jsonObject) {
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST, url, jsonObject,
                ADD_COMMENT_ON_ISSUE, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, ADD_COMMENT_ON_ISSUE);
    }

    public static void makeDeleteCommentRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.DELETE, url,
                DELETE_COMMENT_FROM_ISSUE, requestListener, null, getHeaderWithContentType(context));
        AppApplication.getInstance().addToRequestQueue(request, DELETE_COMMENT_FROM_ISSUE);
    }

    public static void makeEditCommentRequest(String url, AppRequestListenerJsonObject requestListener, Context context, JSONObject jsonObject) {
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.PUT, url, jsonObject,
                EDIT_COMMENT_ON_ISSUE, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, EDIT_COMMENT_ON_ISSUE);
    }

    public static void makeGetIssueChangelogRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url,
                GET_ISSUE_CHANGELOG, requestListener, null, getHeader(context));
        AppApplication.getInstance().addToRequestQueue(request, GET_ISSUE_CHANGELOG);
    }

    public static HashMap<String, String> getHeader(Context context) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", ZPreferences.getHeaderTokenAuth(context));
        return headers;
    }

    public static HashMap<String, String> getHeaderWithContentType(Context context) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", ZPreferences.getHeaderTokenAuth(context));
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
