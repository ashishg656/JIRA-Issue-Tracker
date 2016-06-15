package com.ashish.jiraissuetracker.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.adapters.ProjectDetailListAdapter;
import com.ashish.jiraissuetracker.adapters.UserProfileListAdapter;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.activityStream.ActivityStreamObject;
import com.ashish.jiraissuetracker.objects.activityStream.ActivityStreamObjectNonArray;
import com.ashish.jiraissuetracker.objects.activityStream.Entry;
import com.ashish.jiraissuetracker.objects.login.LoginObjectResponse;
import com.ashish.jiraissuetracker.objects.projectListing.ProjectListingObject;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.requests.AppUrls;
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
 * Created by Ashish on 15/06/16.
 */
public class ProjectDetailActivity extends BaseActivity implements AppRequestListener {

    ProjectListingObject projectListingObject;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProjectDetailListAdapter adapter;

    int startAt = 0;
    int pageSize = 20;
    boolean isMoreAllowed = true;
    boolean isRequestRunning = false;
    private String lastUpdated = null;
    String requestUrl;
    boolean isProjectDetailRequestComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().hasExtra("projectobj")) {
            projectListingObject = getIntent().getExtras().getParcelable("projectobj");
        }

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
                    if (diff < 5 && !isRequestRunning && isMoreAllowed && isProjectDetailRequestComplete) {
                        loadActivityStreamData();
                    }
                }
            }
        });

        loadProjectDetailData();
    }

    private void loadProjectDetailData() {
        if (projectListingObject != null) {
            fillDataForProjectDetailInList(projectListingObject);
        } else {
            requestUrl = ZPreferences.getBaseUrl(this) + AppUrls.getProjectDetailsUrl(projectListingObject.getKey());

            AppRequests.makeGetProjectDetailsRequest(requestUrl, this, this);
        }
    }

    private void loadActivityStreamData() {
        if (isMoreAllowed && isProjectDetailRequestComplete) {
            requestUrl = ZPreferences.getBaseUrl(this) + AppUrls.getActivityStreamForProject(startAt, pageSize,
                    projectListingObject.getKey(), lastUpdated);

            AppRequests.makeActivityStreamRequest(requestUrl, this, this);
        }
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {
            isRequestRunning = true;
        } else if (requestTag.equalsIgnoreCase(RequestTags.GET_PROJECT_DETAILS)) {
            hideErrorLayout();
            showProgressLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {
            isRequestRunning = false;
        } else if (requestTag.equalsIgnoreCase(RequestTags.GET_PROJECT_DETAILS)) {
            hideProgressLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {
            isRequestRunning = false;
            getDataFromXml(response);
        } else if (requestTag.equalsIgnoreCase(RequestTags.GET_PROJECT_DETAILS)) {
            hideProgressLayout();
            hideErrorLayout();

            ProjectListingObject mData = (ProjectListingObject) VolleyUtils.getResponseObject(response, ProjectListingObject.class);

            fillDataForProjectDetailInList(mData);
        }
    }

    private void fillDataForProjectDetailInList(ProjectListingObject mData) {
        this.projectListingObject = mData;
        isProjectDetailRequestComplete = true;
        adapter = new ProjectDetailListAdapter(this, mData);
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
}
