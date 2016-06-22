package com.ashish.jiraissuetracker.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.BaseActivity;
import com.ashish.jiraissuetracker.adapters.IssueChangelogListAdapter;
import com.ashish.jiraissuetracker.adapters.IssuesCommentsListAdapter;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.issueComments.Comment;
import com.ashish.jiraissuetracker.objects.issueComments.IssueCommentsObject;
import com.ashish.jiraissuetracker.objects.issueHistory.IssueHistoryObject;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.requests.AppUrls;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.serverApi.AppRequestListenerJsonObject;
import com.ashish.jiraissuetracker.utils.UIUtils;
import com.ashish.jiraissuetracker.utils.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Ashish on 20/06/16.
 */
public class IssueDetailChangelogFragment extends BaseFragment implements AppRequestListener, View.OnClickListener {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    String issueId;
    IssueChangelogListAdapter adapter;

    public static IssueDetailChangelogFragment newInstance(Bundle e) {
        IssueDetailChangelogFragment frg = new IssueDetailChangelogFragment();
        frg.setArguments(e);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.issues_fragment_layout, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        setProgressAndErrorLayoutVariables();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        issueId = getArguments().getString("issueid");

        loadData();
    }

    private void loadData() {
        requestUrl = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getIssueChangelogUrl(issueId);
        AppRequests.makeGetIssueChangelogRequest(requestUrl, this, getActivity());
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ISSUE_CHANGELOG)) {
            hideErrorLayout();
            showProgressLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ISSUE_CHANGELOG)) {
            showErrorLayout();
            hideProgressLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ISSUE_CHANGELOG)) {
            IssueHistoryObject history = (IssueHistoryObject) VolleyUtils.getResponseObject(response, IssueHistoryObject.class);
            setAdapterData(history);
        }
    }

    private void setAdapterData(IssueHistoryObject data) {
        hideProgressLayout();
        hideErrorLayout();

        try {
            adapter = new IssueChangelogListAdapter(getActivity(), data.getChangelog().getHistories());
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
