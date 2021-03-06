package com.ashish.jiraissuetracker.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.adapters.IssuesFragmentListAdapter;
import com.ashish.jiraissuetracker.broadcasts.LocalBroadcastMaker;
import com.ashish.jiraissuetracker.extras.LocalBroadcastTypes;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.interfaces.FilterIssueinterface;
import com.ashish.jiraissuetracker.objects.issues.SearchListingResponseObject;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.requests.AppUrls;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.serverApi.AppRequestListenerJsonObject;
import com.ashish.jiraissuetracker.utils.DebugUtils;
import com.ashish.jiraissuetracker.utils.UIUtils;
import com.ashish.jiraissuetracker.utils.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Ashish on 07/06/16.
 */
public class IssuesJqlUrlFragment extends BaseFragment implements AppRequestListener, RequestTags, AppRequestListenerJsonObject {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    FrameLayout recyclerViewContainer;

    int startAt = 0;
    int pageSize = 20;
    boolean isMoreAllowed = true;
    boolean isRequestRunning;
    IssuesFragmentListAdapter adapter;

    FilterIssueinterface issueinterface;

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

    public static IssuesJqlUrlFragment newInstance(Bundle e) {
        IssuesJqlUrlFragment frg = new IssuesJqlUrlFragment();
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

        recyclerViewContainer.setPadding(0, getActivity().getResources().getDimensionPixelSize(R.dimen.z_margin_6dp), 0, 0);

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

        loadData();
    }

    private void loadData() {
        if (getArguments().containsKey("from_jql") && getArguments().getBoolean("from_jql")) {
            loadDataUsingJqlPost();
            return;
        }
    }

    private void loadDataUsingJqlPost() {
        if (isMoreAllowed) {
            StringBuilder builder = new StringBuilder();

            if (issueinterface.getText() != null && issueinterface.getText().trim().length() > 0) {
                builder.append(" AND text ~ \"" + issueinterface.getText() + "\"");
            }

            if (issueinterface.getLabels() != null && issueinterface.getLabels().trim().length() > 0) {
                builder.append(" AND labels = \"" + issueinterface.getLabels() + "\"");
            }

            if (issueinterface.getIssueKey() != null && issueinterface.getIssueKey().trim().length() > 0) {
                builder.append(" AND issueKey = " + issueinterface.getIssueKey());
            }

            if (issueinterface.getSelectedAssignee() != null && issueinterface.getSelectedAssignee().size() > 0) {
                builder.append(" AND assignee IN (\"");
                builder.append(TextUtils.join("\",\"", issueinterface.getSelectedAssignee()));
                builder.append("\")");
            }

            if (issueinterface.getSelectedReporter() != null && issueinterface.getSelectedReporter().size() > 0) {
                builder.append(" AND reporter IN (\"");
                builder.append(TextUtils.join("\",\"", issueinterface.getSelectedReporter()));
                builder.append("\")");
            }

            if (issueinterface.getSelectedprojects() != null && issueinterface.getSelectedprojects().size() > 0) {
                builder.append(" AND project IN (\"");
                builder.append(TextUtils.join("\",\"", issueinterface.getSelectedprojects()));
                builder.append("\")");
            }

            if (issueinterface.getSelectedPriorities() != null && issueinterface.getSelectedPriorities().size() > 0) {
                builder.append(" AND priority IN (\"");
                builder.append(TextUtils.join("\",\"", issueinterface.getSelectedPriorities()));
                builder.append("\")");
            }

            if (issueinterface.getSelectedResolution() != null && issueinterface.getSelectedResolution().size() > 0) {
                builder.append(" AND resolution IN (\"");
                builder.append(TextUtils.join("\",\"", issueinterface.getSelectedResolution()));
                builder.append("\")");
            }

            if (issueinterface.getSelectedStatus() != null && issueinterface.getSelectedStatus().size() > 0) {
                builder.append(" AND status IN (\"");
                builder.append(TextUtils.join("\",\"", issueinterface.getSelectedStatus()));
                builder.append("\")");
            }

            if (issueinterface.getSelectedType() != null && issueinterface.getSelectedType().size() > 0) {
                builder.append(" AND type IN (\"");
                builder.append(TextUtils.join("\",\"", issueinterface.getSelectedType()));
                builder.append("\")");
            }

            builder.replace(0, 5, "");
            String[] items = getActivity().getResources().getStringArray(R.array.sort_order_options);
            builder.append(" order by " + items[issueinterface.getSelectedSortOrderPosition()]);

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("jql", builder);
                jsonObject.put("startAt", startAt);
                jsonObject.put("maxResults", pageSize);

                requestUrl = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getSearchIssuesRequestWithPostDataUrl();
                AppRequests.makeSearchIssuesRequestWithPostData(requestUrl, this, getActivity(), jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(SEARCH_BY_POST_DATA_CUSTOM_RESULTS)) {
            isRequestRunning = true;
            if (adapter == null) {
                hideErrorLayout();
                showProgressLayout();
            }
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(SEARCH_BY_POST_DATA_CUSTOM_RESULTS)) {
            try {
                DebugUtils.log(new String(error.networkResponse.data));
            } catch (Exception e) {
                e.printStackTrace();
            }

            isRequestRunning = false;
            if (adapter == null) {
                showErrorLayout();
                hideProgressLayout();
            }
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, JSONObject response) {
        if (requestTag.equalsIgnoreCase(SEARCH_BY_POST_DATA_CUSTOM_RESULTS)) {
            isRequestRunning = false;
            SearchListingResponseObject issuesData = (SearchListingResponseObject) VolleyUtils.getResponseObject(response, SearchListingResponseObject.class);

            setAdapterData(issuesData);
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {

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
    public void onAttach(Context context) {
        try {
            LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver,
                    new IntentFilter(LocalBroadcastMaker.BROADCAST_INTENT_FILTER_EVENT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (context != null && context instanceof FilterIssueinterface) {
                issueinterface = (FilterIssueinterface) context;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        try {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
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
