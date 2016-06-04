package com.ashish.jiraissuetracker.requests;

import android.content.Context;
import android.util.Base64;

import com.android.volley.Request;
import com.ashish.jiraissuetracker.application.AppApplication;
import com.ashish.jiraissuetracker.extras.AppUrls;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.serverApi.CustomStringRequest;

import java.util.HashMap;

/**
 * Created by Ashish on 04/06/16.
 */
public class AppRequests implements RequestTags {

    public static void makeLoginRequest(String urlJira, String userName, String password, AppRequestListener requestListener, Context context) {
        urlJira = "https://" + urlJira + AppUrls.LOGIN_URL;

        String headerToken = userName + ":" + password;
        headerToken = Base64.encodeToString(headerToken.toString().getBytes(), Base64.DEFAULT);
        headerToken = "Basic " + headerToken;

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", headerToken);
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, urlJira, LOGIN_REQUEST, requestListener, null, headers);

        AppApplication.getInstance().addToRequestQueue(request, LOGIN_REQUEST, context);
    }
}
