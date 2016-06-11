package com.ashish.jiraissuetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.adapters.ChangeIssueStatusFragmentListAdapter;
import com.ashish.jiraissuetracker.extras.AppUrls;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.getAllStatusForProject.GetAllStatusForProjectObject;
import com.ashish.jiraissuetracker.objects.getAllStatusForProject.Status;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.VolleyUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashish on 11/06/16.
 */
public class ChangeIssueStatusFragment extends BaseFragment implements AppRequestListener, View.OnClickListener {

    ListView listView;
    ChangeIssueStatusFragmentListAdapter adapter;
    TextView cancelButton;

    String projectid, issuetype, currentStatus, issueid;
    GetAllStatusForProjectObject[] mData;

    List<Status> statusList;

    public static ChangeIssueStatusFragment newInstance(Bundle bundle) {
        ChangeIssueStatusFragment frg = new ChangeIssueStatusFragment();
        frg.setArguments(bundle);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.change_issue_status_fragment_layout, container, false);

        listView = (ListView) rootView.findViewById(R.id.issue_type_select_list);
        setProgressAndErrorLayoutVariables();
        cancelButton = (TextView) rootView.findViewById(R.id.cancel_fragment);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        projectid = getArguments().getString("projectid");
        issuetype = getArguments().getString("issuetype");
        currentStatus = getArguments().getString("currentStatus");
        issueid = getArguments().getString("issueid");

        cancelButton.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        String url = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getAllStatusForProject(projectid);

        AppRequests.makeGetAllStatusForProjectRequest(url, this, getActivity());
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_STATUS_FOR_PROJECT)) {
            hideErrorLayout();
            showProgressLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_STATUS_FOR_PROJECT)) {
            hideProgressLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_STATUS_FOR_PROJECT)) {
            hideErrorLayout();
            hideProgressLayout();

            mData = new Gson().fromJson(response, GetAllStatusForProjectObject[].class);
            setAdapterData();
        }
    }

    private void setAdapterData() {
        for (GetAllStatusForProjectObject obj : mData) {
            if (obj.getName().equalsIgnoreCase(issuetype)) {
                statusList = obj.getStatuses();

                adapter = new ChangeIssueStatusFragmentListAdapter(issueid, statusList, currentStatus, getActivity());
                listView.setAdapter(adapter);

                return;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_fragment:
                getActivity().onBackPressed();
                break;
        }
    }
}
