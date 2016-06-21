package com.ashish.jiraissuetracker.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.adapters.IssuesFragmentListAdapter;
import com.ashish.jiraissuetracker.adapters.ProjectDetailListAdapter;
import com.ashish.jiraissuetracker.broadcasts.LocalBroadcastMaker;
import com.ashish.jiraissuetracker.extras.LocalBroadcastTypes;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.activityStream.ActivityStreamObject;
import com.ashish.jiraissuetracker.objects.activityStream.ActivityStreamObjectNonArray;
import com.ashish.jiraissuetracker.objects.activityStream.Entry;
import com.ashish.jiraissuetracker.objects.issues.SearchListingResponseObject;
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
    String projectId;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProjectDetailListAdapter adapter;

    int startAt = 0;
    int pageSize = 20;
    boolean isMoreAllowed = true;
    boolean isRequestRunning = false;
    String requestUrl;
    boolean isProjectDetailRequestComplete = false;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int type = intent.getIntExtra("type", -1);
                if (type == LocalBroadcastTypes.TYPE_ISSUE_STATUS_CHANGE) {
                    broadcastForIssueStatusChangeReceived(intent);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity_layout);

        try {
            LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                    new IntentFilter(LocalBroadcastMaker.BROADCAST_INTENT_FILTER_EVENT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setProgressAndErrorLayoutVariables();

        if (getIntent().hasExtra("projectobj")) {
            projectListingObject = getIntent().getExtras().getParcelable("projectobj");
        } else if (getIntent().hasExtra("projectid")) {
            projectId = getIntent().getExtras().getString("projectid");
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
                        loadIssuesForProject();
                    }
                }
            }
        });

        loadProjectDetailData();
    }

    private void loadProjectDetailData() {
        if (projectListingObject != null) {
            fillDataForProjectDetailInList();
        } else {
            requestUrl = ZPreferences.getBaseUrl(this) + AppUrls.getProjectDetailsUrl(projectId);

            AppRequests.makeGetProjectDetailsRequest(requestUrl, this, this);
        }
    }

    private void loadIssuesForProject() {
        if (isMoreAllowed && isProjectDetailRequestComplete) {
            requestUrl = ZPreferences.getBaseUrl(this) + AppUrls.getIssuesForProjectUrl(projectListingObject.getKey(), startAt, pageSize);

            AppRequests.makeGetIssuesForProjectRequest(requestUrl, this, this);
        }
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.ISSUES_REQUEST_FOR_PROJECT)) {
            isRequestRunning = true;
        } else if (requestTag.equalsIgnoreCase(RequestTags.GET_PROJECT_DETAILS)) {
            hideErrorLayout();
            showProgressLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.ISSUES_REQUEST_FOR_PROJECT)) {
            isRequestRunning = false;
        } else if (requestTag.equalsIgnoreCase(RequestTags.GET_PROJECT_DETAILS)) {
            hideProgressLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.ISSUES_REQUEST_FOR_PROJECT)) {
            isRequestRunning = false;

            SearchListingResponseObject issuesData = (SearchListingResponseObject) VolleyUtils.getResponseObject(response, SearchListingResponseObject.class);
            addIssuesInList(issuesData);
        } else if (requestTag.equalsIgnoreCase(RequestTags.GET_PROJECT_DETAILS)) {
            hideProgressLayout();
            hideErrorLayout();

            projectListingObject = (ProjectListingObject) VolleyUtils.getResponseObject(response, ProjectListingObject.class);

            fillDataForProjectDetailInList();
        }
    }

    private void addIssuesInList(SearchListingResponseObject issuesData) {
        try {
            if (issuesData.getIssues() != null) {
                if (issuesData.getIssues().size() < pageSize) {
                    isMoreAllowed = false;
                } else {
                    isMoreAllowed = true;
                    startAt = startAt + pageSize;
                }
            }

            if (adapter != null) {
                adapter.addData(issuesData.getIssues(), isMoreAllowed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillDataForProjectDetailInList() {
        isProjectDetailRequestComplete = true;
        adapter = new ProjectDetailListAdapter(this, projectListingObject);
        recyclerView.setAdapter(adapter);

        loadIssuesForProject();
    }

    @Override
    public void onDestroy() {
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    // broadcasts
    void broadcastForIssueStatusChangeReceived(Intent intent) {
        try {
            String issueid = intent.getStringExtra("issueid");
            String newstatus = intent.getStringExtra("newstatus");

            if (adapter != null) {
                adapter.changeIssueStatus(issueid, newstatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
