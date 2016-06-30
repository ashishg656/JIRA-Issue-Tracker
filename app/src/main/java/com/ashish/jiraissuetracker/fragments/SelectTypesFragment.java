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
import com.ashish.jiraissuetracker.adapters.SelectTypesFragmentListAdapter;
import com.ashish.jiraissuetracker.extras.AppConstants;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.interfaces.FilterIssueinterface;
import com.ashish.jiraissuetracker.objects.issues.Issuetype;
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
public class SelectTypesFragment extends BaseFragment implements View.OnClickListener, AppRequestListener {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SelectTypesFragmentListAdapter adapter;
    TextView done;

    FilterIssueinterface issueinterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FilterIssuesActivity) {
            issueinterface = (FilterIssueinterface) context;
        }
    }

    public static SelectTypesFragment newInstance(Bundle b) {
        SelectTypesFragment frg = new SelectTypesFragment();
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
        requestUrl = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getAllIssueTypesUrl();

        AppRequests.makeGetAllIssueTypesRequest(requestUrl, this, getActivity());
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_ISSUE_TYPES)) {
            showProgressLayout();
            hideErrorLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_ISSUE_TYPES)) {
            hideProgressLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_ISSUE_TYPES)) {
            hideErrorLayout();
            hideProgressLayout();

            Type listType = new TypeToken<ArrayList<Issuetype>>() {
            }.getType();
            List<Issuetype> mData = new Gson().fromJson(response, listType);

            setAdapterData(mData);
        }
    }

    private void setAdapterData(List<Issuetype> object) {
        adapter = new SelectTypesFragmentListAdapter(getActivity(), object, issueinterface.getSelectedType());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_fragment:
                try {
                    issueinterface.setFilterDataAgain(AppConstants.FILTER_SET_TYPE, adapter.getSelectedItemsString());
                    getActivity().onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
