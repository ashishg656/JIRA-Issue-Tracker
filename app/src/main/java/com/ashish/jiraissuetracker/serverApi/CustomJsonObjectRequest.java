package com.ashish.jiraissuetracker.serverApi;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ashish.jiraissuetracker.utils.DebugUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashish on 11/06/16.
 */
public class CustomJsonObjectRequest extends JsonObjectRequest {

    AppRequestListenerJsonObject appRequestListener;
    HashMap<String, String> params;
    HashMap<String, String> headers;
    String tag;
    String url;

    long requestStartTime;

    public CustomJsonObjectRequest(int method, String url, JSONObject jsonObject, String tag,
                                   AppRequestListenerJsonObject appRequestListener,
                                   HashMap<String, String> params, HashMap<String, String> headers) {
        super(method, url, jsonObject, null, null);
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
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if (jsonString.length() == 0) {
                return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
            }
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        if (DebugUtils.showTags) {
            long requestEndTime = System.currentTimeMillis();
            long difference = (requestEndTime - requestStartTime);
            DebugUtils.logRequests("Request Complete in " + difference + "ms. URL = " + url);
        } else {
            DebugUtils.logRequests("Request Complete. URL = " + url);
        }
        try {
            DebugUtils.printToSystem(response.toString());
        } catch (Exception e) {
            DebugUtils.printToSystem("Empty Response Received");
        }
        appRequestListener.onRequestCompleted(tag, response);
    }

    @Override
    public void deliverError(VolleyError error) {
        if (DebugUtils.showTags) {
            long requestEndTime = System.currentTimeMillis();
            long difference = (requestEndTime - requestStartTime);
            DebugUtils.logRequests("Request Failed in " + difference + ",s. URL = " + url);
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
