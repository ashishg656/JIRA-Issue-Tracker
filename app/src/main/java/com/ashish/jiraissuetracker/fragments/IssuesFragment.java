package com.ashish.jiraissuetracker.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.adapters.IssuesFragmentListAdapter;
import com.ashish.jiraissuetracker.broadcasts.LocalBroadcastMaker;
import com.ashish.jiraissuetracker.extras.AppUrls;
import com.ashish.jiraissuetracker.extras.LocalBroadcastTypes;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.issues.SearchListingResponseObject;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.VolleyUtils;

/**
 * Created by Ashish on 07/06/16.
 */
public class IssuesFragment extends BaseFragment implements AppRequestListener, RequestTags {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    int startAt = 0;
    int pageSize = 20;
    boolean isMoreAllowed = true;
    boolean isRequestRunning;
    IssuesFragmentListAdapter adapter;

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

    public static IssuesFragment newInstance(Bundle e) {
        IssuesFragment frg = new IssuesFragment();
        frg.setArguments(e);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.issues_fragment_layout, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager != null && adapter != null && isMoreAllowed) {
                    int lastitem = layoutManager.findLastVisibleItemPosition();
                    int totalitems = adapter.getItemCount();
                    int diff = totalitems - lastitem;
                    if (diff < 5 && !isRequestRunning && isMoreAllowed) {
                        loadData();
                    }
                }
            }
        });

        setProgressAndErrorLayoutVariables();

        loadData();
    }

    private void loadData() {
        if (isMoreAllowed) {
            requestUrl = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getSearchIssuesUrl(ZPreferences.getUserProfileID(getActivity()), startAt, pageSize);

            AppRequests.makeIssuesRequest(requestUrl, this, getActivity());
        }
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(ISSUES_REQUEST)) {
            isRequestRunning = true;
            hideErrorLayout();
            showProgressLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(ISSUES_REQUEST)) {
            isRequestRunning = false;
            showErrorLayout();
            hideProgressLayout();

            try {
                SearchListingResponseObject issuesData = (SearchListingResponseObject) VolleyUtils.getResponseFromCache(SearchListingResponseObject.class, requestUrl);
                setAdapterData(issuesData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(ISSUES_REQUEST)) {
            isRequestRunning = false;
            SearchListingResponseObject issuesData = (SearchListingResponseObject) VolleyUtils.getResponseObject(response, SearchListingResponseObject.class);

            setAdapterData(issuesData);
        }
    }

    private void setAdapterData(SearchListingResponseObject issuesData) {
        hideProgressLayout();
        hideErrorLayout();

        try {
            if (issuesData.getIssues() != null) {
                if (issuesData.getIssues().size() < pageSize) {
                    isMoreAllowed = false;
                } else {
                    isMoreAllowed = true;
                    startAt = startAt + pageSize;
                }
            }

            if (adapter == null) {
                adapter = new IssuesFragmentListAdapter(issuesData.getIssues(), getActivity(), isMoreAllowed);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.addData(issuesData.getIssues(), isMoreAllowed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        try {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver,
                    new IntentFilter(LocalBroadcastMaker.BROADCAST_INTENT_FILTER_EVENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        try {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();
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
