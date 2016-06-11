package com.ashish.jiraissuetracker.serverApi;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ashish.jiraissuetracker.utils.DebugUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class CustomStringRequest extends StringRequest {

    AppRequestListener appRequestListener;
    HashMap<String, String> params;
    HashMap<String, String> headers;
    String tag;
    String url;

    long requestStartTime;

    public CustomStringRequest(int method, String url, String tag,
                               AppRequestListener appRequestListener,
                               HashMap<String, String> params, HashMap<String, String> headers) {
        super(method, url, null, null);
        this.appRequestListener = appRequestListener;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.url = url;

        DebugUtils.logRequests("Request Started. URL = " + url);

        if (DebugUtils.showTags) {
            requestStartTime = System.currentTimeMillis();
        }

        appRequestListener.onRequestStarted(tag);
    }

    @Override
    protected void deliverResponse(String response) {
        if (DebugUtils.showTags) {
            long requestEndTime = System.currentTimeMillis();
            double difference = (requestEndTime - requestStartTime) / 1000;
            DebugUtils.logRequests("Request Complete in " + difference + "s. URL = " + url);
        } else {
            DebugUtils.logRequests("Request Complete. URL = " + url);
        }
        DebugUtils.printToSystem(response);
        appRequestListener.onRequestCompleted(tag, response);
    }

    @Override
    public void deliverError(VolleyError error) {
        if (DebugUtils.showTags) {
            long requestEndTime = System.currentTimeMillis();
            float difference = (requestEndTime - requestStartTime) / 1000;
            DebugUtils.logRequests("Request Failed in " + difference + "s. URL = " + url);
        } else {
            DebugUtils.logRequests("Request Failed. URL = " + url);
        }
        appRequestListener.onRequestFailed(tag, error);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : Collections.<String, String>emptyMap();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : Collections.<String, String>emptyMap();
    }
}
