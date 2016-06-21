package com.ashish.jiraissuetracker.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.adapters.UserProfileListAdapter;
import com.ashish.jiraissuetracker.requests.AppUrls;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.activityStream.ActivityStreamObject;
import com.ashish.jiraissuetracker.objects.activityStream.ActivityStreamObjectNonArray;
import com.ashish.jiraissuetracker.objects.activityStream.Entry;
import com.ashish.jiraissuetracker.objects.login.LoginObjectResponse;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.DebugUtils;
import com.ashish.jiraissuetracker.utils.TimeUtils;
import com.ashish.jiraissuetracker.utils.VolleyUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashish on 13/06/16.
 */
public class UserProfileActivity extends BaseActivity implements AppRequestListener {

    String userName;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    UserProfileListAdapter adapter;

    int startAt = 0;
    int pageSize = 20;
    boolean isMoreAllowed = true;
    boolean isRequestRunning = false;
    private String lastUpdated = null;
    String requestUrl;
    boolean isUserProfileRequestComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity_layout);

        setProgressAndErrorLayoutVariables();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        userName = getIntent().getExtras().getString("username");

        recyclerView = (RecyclerView) findViewById(R.id.userprofilerecycler);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager != null && adapter != null && isMoreAllowed) {
                    int lastitem = layoutManager.findLastVisibleItemPosition();
                    int totalitems = adapter.getItemCount();
                    int diff = totalitems - lastitem;
                    if (diff < 5 && !isRequestRunning && isMoreAllowed && isUserProfileRequestComplete) {
                        loadActivityStreamData();
                    }
                }
            }
        });

        loadUserProfileData();
    }

    private void loadUserProfileData() {
        requestUrl = ZPreferences.getBaseUrl(this) + AppUrls.getUserProfileUrl(userName);

        AppRequests.makeGetUserProfileRequest(requestUrl, this, this);
    }

    private void loadActivityStreamData() {
        if (isMoreAllowed && isUserProfileRequestComplete) {
            requestUrl = ZPreferences.getBaseUrl(this) + AppUrls.getActivityStreamForUserUrl(startAt, pageSize, userName, lastUpdated);

            AppRequests.makeActivityStreamRequest(requestUrl, this, this);
        }
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {
            isRequestRunning = true;
        } else if (requestTag.equalsIgnoreCase(RequestTags.USER_PROFILE)) {
            hideErrorLayout();
            showProgressLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {
            isRequestRunning = false;
        } else if (requestTag.equalsIgnoreCase(RequestTags.USER_PROFILE)) {
            hideProgressLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {
            isRequestRunning = false;
            getDataFromXml(response);
        } else if (requestTag.equalsIgnoreCase(RequestTags.USER_PROFILE)) {
            hideProgressLayout();
            hideErrorLayout();

            LoginObjectResponse mData = (LoginObjectResponse) VolleyUtils.getResponseObject(response, LoginObjectResponse.class);

            fillDataForUserProfileInRecyclerView(mData);
        }
    }

    private void fillDataForUserProfileInRecyclerView(LoginObjectResponse mData) {
        isUserProfileRequestComplete = true;
        adapter = new UserProfileListAdapter(this, mData, userName);
        recyclerView.setAdapter(adapter);

        loadActivityStreamData();
    }

    private void getDataFromXml(String response) {
        JSONObject jsonObj = null;
        try {
            DebugUtils.log("Starting XML to Json Process");
            jsonObj = XML.toJSONObject(response);
            try {
                ActivityStreamObject object = new Gson().fromJson(String.valueOf(jsonObj), ActivityStreamObject.class);
                addActivityStreamData(object);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    ActivityStreamObjectNonArray object = new Gson().fromJson(String.valueOf(jsonObj), ActivityStreamObjectNonArray.class);
                    DebugUtils.log("Got Data from single XML ActivityStreamObjectNonArray and StartAt = " + startAt);
                    addActivityStreamData(object);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            DebugUtils.log("Finished XML to Json Conversion and mapped Json to ActivityStreamObject");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addActivityStreamData(ActivityStreamObjectNonArray object) {
        isMoreAllowed = false;

        List<Entry> listEntry = new ArrayList<>();
        listEntry.add(object.getFeed().getEntry());
        if (adapter != null) {
            adapter.addData(listEntry, isMoreAllowed);
        }
    }

    private void addActivityStreamData(ActivityStreamObject object) {
        if (object != null && object.getFeed() != null && object.getFeed().getEntry() != null) {
            if (object.getFeed().getEntry().size() < pageSize) {
                isMoreAllowed = false;
            } else {
                isMoreAllowed = true;

                Entry lastEntry = object.getFeed().getEntry().get(object.getFeed().getEntry().size() - 1);
                lastUpdated = TimeUtils.getTimeInMillisFromStringForActivityStreamGMT(lastEntry.getUpdated());
                startAt = startAt + pageSize;
                DebugUtils.log("StartAt for User Profile Activity = " + startAt);
            }
        }

        if (adapter != null && object != null && object.getFeed() != null) {
            adapter.addData(object.getFeed().getEntry(), isMoreAllowed);
        }
    }

    public void scrollRecyclerViewToPosition0() {
        if (recyclerView != null && layoutManager != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }
}
