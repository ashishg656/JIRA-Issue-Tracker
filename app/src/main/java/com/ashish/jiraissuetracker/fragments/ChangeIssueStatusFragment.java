package com.ashish.jiraissuetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.adapters.ChangeIssueStatusFragmentListAdapter;
import com.ashish.jiraissuetracker.objects.getIssueTransitions.Transition;
import com.ashish.jiraissuetracker.requests.AppUrls;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.getIssueTransitions.GetIssueTransitionObject;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.VolleyUtils;

import java.util.List;

/**
 * Created by Ashish on 11/06/16.
 */
public class ChangeIssueStatusFragment extends BaseFragment implements AppRequestListener, View.OnClickListener {

    ListView listView;
    ChangeIssueStatusFragmentListAdapter adapter;
    TextView cancelButton;

    String projectid, issuetype, currentStatus, issueid;

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

        cancelButton.setOnClickListener(this);

        try {
            projectid = getArguments().getString("projectid", "");
            issuetype = getArguments().getString("issuetype", "");
            currentStatus = getArguments().getString("currentStatus", "");
            issueid = getArguments().getString("issueid", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Transition> transitions = getArguments().getParcelableArrayList("transitionslist");
            if (transitions != null && transitions.size() > 0 && issueid != null && issueid.length() > 0) {
                setAdapterData(transitions);
                return;
            }
        } catch (Exception e) {
        }

        loadData();
    }

    private void loadData() {
        String url = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getIssueTransitions(issueid);

        AppRequests.makeGetIssueTransitionsRequest(url, this, getActivity());
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_TRANSITIONS_FOR_ISSUE)) {
            hideErrorLayout();
            showProgressLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_TRANSITIONS_FOR_ISSUE)) {
            hideProgressLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_TRANSITIONS_FOR_ISSUE)) {
            hideErrorLayout();
            hideProgressLayout();

            GetIssueTransitionObject mData = (GetIssueTransitionObject) VolleyUtils.getResponseObject(response, GetIssueTransitionObject.class);
            setAdapterData(mData);
        }
    }

    private void setAdapterData(GetIssueTransitionObject mData) {
        adapter = new ChangeIssueStatusFragmentListAdapter(issueid, mData.getTransitions(), currentStatus, getActivity());
        listView.setAdapter(adapter);
    }

    private void setAdapterData(List<Transition> transitions) {
        adapter = new ChangeIssueStatusFragmentListAdapter(issueid, transitions, currentStatus, getActivity());
        listView.setAdapter(adapter);
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
