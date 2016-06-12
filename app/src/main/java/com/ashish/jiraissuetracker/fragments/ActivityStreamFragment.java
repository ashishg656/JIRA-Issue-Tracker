package com.ashish.jiraissuetracker.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.extras.AppUrls;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;

/**
 * Created by Ashish on 12/06/16.
 */
public class ActivityStreamFragment extends BaseFragment implements AppRequestListener {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    int startAt = 0;
    int pageSize = 20;
    boolean isMoreAllowed = true;

    public static ActivityStreamFragment newInstance(Bundle e) {
        ActivityStreamFragment frg = new ActivityStreamFragment();
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

        setProgressAndErrorLayoutVariables();

        loadData();
    }

    void loadData() {
        if (isMoreAllowed) {
            requestUrl = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getActivityStreamUrl(startAt, pageSize);

            AppRequests.makeActivityStreamRequest(requestUrl, this, getActivity());
        }
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {

        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {

        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {

        }
    }
}
