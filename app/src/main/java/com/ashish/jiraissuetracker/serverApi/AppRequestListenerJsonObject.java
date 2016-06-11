package com.ashish.jiraissuetracker.serverApi;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface AppRequestListenerJsonObject {

    public void onRequestStarted(String requestTag);

    public void onRequestFailed(String requestTag, VolleyError error);

    public void onRequestCompleted(String requestTag, JSONObject response);
}
