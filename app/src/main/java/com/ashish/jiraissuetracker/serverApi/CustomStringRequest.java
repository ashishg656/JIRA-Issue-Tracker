package com.ashish.jiraissuetracker.serverApi;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class CustomStringRequest extends StringRequest {

    AppRequestListener appRequestListener;
    HashMap<String, String> params;
    HashMap<String, String> headers;
    String tag;

    public CustomStringRequest(int method, String url, String tag,
                               AppRequestListener appRequestListener,
                               HashMap<String, String> params, HashMap<String, String> headers) {
        super(method, url, null, null);
        this.appRequestListener = appRequestListener;
        this.tag = tag;
        this.params = params;
        this.headers = headers;

        appRequestListener.onRequestStarted(tag);
    }

    @Override
    protected void deliverResponse(String response) {
        appRequestListener.onRequestCompleted(tag, response);
    }

    @Override
    public void deliverError(VolleyError error) {
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
