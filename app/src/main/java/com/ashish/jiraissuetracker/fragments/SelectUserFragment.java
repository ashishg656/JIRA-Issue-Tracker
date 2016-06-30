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
import com.ashish.jiraissuetracker.adapters.SelectUserFragmentListAdapter;
import com.ashish.jiraissuetracker.extras.AppConstants;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.interfaces.FilterIssueinterface;
import com.ashish.jiraissuetracker.objects.issueComments.Author;
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
 * Created by Ashish on 26/06/16.
 */
public class SelectUserFragment extends BaseFragment implements AppRequestListener, View.OnClickListener {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SelectUserFragmentListAdapter adapter;
    TextView done;

    FilterIssueinterface issueinterface;

    // whether looking for assignee or reporter
    int type;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FilterIssuesActivity) {
            issueinterface = (FilterIssueinterface) context;
        }
    }

    public static SelectUserFragment newInstance(Bundle b) {
        SelectUserFragment frg = new SelectUserFragment();
        frg.setArguments(b);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.select_user_fragment_layout, container, false);

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

        type = getArguments().getInt("type");

        loadData();
    }

    private void loadData() {
        String url = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getAllUsersUrl();
        AppRequests.makeGetAllUsersRequest(url, this, getActivity());
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_USERS)) {
            showProgressLayout();
            hideErrorLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_USERS)) {
            showErrorLayout();
            hideProgressLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_ALL_USERS)) {
            hideErrorLayout();
            hideProgressLayout();

            Type listType = new TypeToken<ArrayList<Author>>() {
            }.getType();
            List<Author> mData = new Gson().fromJson(response, listType);

            setAdapterData(mData);
        }
    }

    private void setAdapterData(List<Author> mData) {
        Author unassigned = new Author();
        unassigned = unassigned.getUnassignedAuthor(unassigned);
        mData.add(0, unassigned);

        Author selfAuthor = new Author();
        selfAuthor = selfAuthor.getSelfAuthor(selfAuthor, getActivity());
        mData.add(1, selfAuthor);

        if (type == AppConstants.FILTER_USER_SELECT_ASSIGNEE) {
            adapter = new SelectUserFragmentListAdapter(getActivity(), mData, issueinterface.getSelectedAssignee());
        } else if (type == AppConstants.FILTER_USER_SELECT_REPORTER) {
            adapter = new SelectUserFragmentListAdapter(getActivity(), mData, issueinterface.getSelectedReporter());
        }

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_fragment:
                try {
                    if (type == AppConstants.FILTER_USER_SELECT_ASSIGNEE) {
                        issueinterface.setFilterDataAgain(AppConstants.FILTER_SET_ASSIGNEE, adapter.getSelectedItemsString());
                    } else if (type == AppConstants.FILTER_USER_SELECT_REPORTER) {
                        issueinterface.setFilterDataAgain(AppConstants.FILTER_SET_REPORTER, adapter.getSelectedItemsString());
                    }
                    getActivity().onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
