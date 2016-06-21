package com.ashish.jiraissuetracker.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.adapters.ProjectListingAdapter;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.projectListing.ProjectListingObject;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.requests.AppUrls;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.VolleyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashish on 15/06/16.
 */
public class ProjectListingFragment extends BaseFragment implements AppRequestListener {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    FrameLayout recyclerViewContainer;

    ProjectListingAdapter adapter;

    public static ProjectListingFragment newInstance(Bundle e) {
        ProjectListingFragment frg = new ProjectListingFragment();
        frg.setArguments(e);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.issues_fragment_layout, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        setProgressAndErrorLayoutVariables();
        recyclerViewContainer = (FrameLayout) rootView.findViewById(R.id.recyclerview_container);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewContainer.setPadding(0, getActivity().getResources().getDimensionPixelSize(R.dimen.z_recycler_padding_top), 0, 0);

        loadData();
    }

    private void loadData() {
        requestUrl = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getAllProjectsUrl();

        AppRequests.makeGetAllProjectsRequest(requestUrl, this, getActivity());
    }


    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_PROJECTS)) {
            showProgressLayout();
            hideErrorLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_PROJECTS)) {
            hideProgressLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_PROJECTS)) {
            hideErrorLayout();
            hideProgressLayout();

            Type listType = new TypeToken<ArrayList<ProjectListingObject>>() {
            }.getType();
            List<ProjectListingObject> mData = new Gson().fromJson(response, listType);

            setAdapterData(mData);
        }
    }

    private void setAdapterData(List<ProjectListingObject> object) {
        adapter = new ProjectListingAdapter(getActivity(), object);
        recyclerView.setAdapter(adapter);
    }
}
