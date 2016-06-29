package com.ashish.jiraissuetracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.FilterIssuesActivity;
import com.ashish.jiraissuetracker.adapters.SelectResolutionsFragmentListAdapter;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.interfaces.FilterIssueinterface;
import com.ashish.jiraissuetracker.objects.issueDetail.Resolution;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.requests.AppUrls;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashish on 29/06/16.
 */
public class SelectResolutionsFragment extends BaseFragment implements View.OnClickListener, AppRequestListener {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SelectResolutionsFragmentListAdapter adapter;
    TextView done;

    FilterIssueinterface issueinterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FilterIssuesActivity) {
            issueinterface = (FilterIssueinterface) context;
        }
    }

    public static SelectResolutionsFragment newInstance(Bundle b) {
        SelectResolutionsFragment frg = new SelectResolutionsFragment();
        frg.setArguments(b);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.select_project_fragment_layout, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.issue_type_select_list);
        setProgressAndErrorLayoutVariables();
        done = (TextView) rootView.findViewById(R.id.cancel_fragment);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        done.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        requestUrl = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getAllResolutionsUrl();

        AppRequests.makeGetAllResolutionsRequest(requestUrl, this, getActivity());
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_RESOLUTIONS)) {
            showProgressLayout();
            hideErrorLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_RESOLUTIONS)) {
            hideProgressLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_RESOLUTIONS)) {
            hideErrorLayout();
            hideProgressLayout();

            Type listType = new TypeToken<ArrayList<Resolution>>() {
            }.getType();
            List<Resolution> mData = new Gson().fromJson(response, listType);

            mData = filterAdapterDataForDuplicates(mData);

            setAdapterData(mData);
        }
    }

    private List<Resolution> filterAdapterDataForDuplicates(List<Resolution> mData) {
        List<Resolution> newList = new ArrayList<>();
        List<String> temp = new ArrayList<>();

        for (Resolution resolution : mData) {
            if (!temp.contains(resolution.getName())) {
                temp.add(resolution.getName());
                newList.add(resolution);
            }
        }

        return newList;
    }

    private void setAdapterData(List<Resolution> object) {
        adapter = new SelectResolutionsFragmentListAdapter(getActivity(), object, issueinterface.getSelectedResolution());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_fragment:
                try {
                    issueinterface.setSelectedResolution(adapter.getSelectedItemsString());
                    issueinterface.setFilterDataAgain();
                    getActivity().onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
