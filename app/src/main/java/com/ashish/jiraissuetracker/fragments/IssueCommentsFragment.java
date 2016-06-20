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
import com.ashish.jiraissuetracker.adapters.IssuesCommentsListAdapter;
import com.ashish.jiraissuetracker.adapters.IssuesFragmentListAdapter;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.issueComments.Comment;
import com.ashish.jiraissuetracker.objects.issueComments.IssueCommentsObject;
import com.ashish.jiraissuetracker.objects.issues.SearchListingResponseObject;
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
public class IssueCommentsFragment extends BaseFragment implements AppRequestListener, View.OnClickListener, AppRequestListenerJsonObject {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    int startAt = 0;
    int pageSize = 20;
    boolean isMoreAllowed = true;
    boolean isRequestRunning;

    String issueId;
    IssuesCommentsListAdapter adapter;

    LinearLayout sendComment;
    ProgressDialog progressDialog;
    EditText commentEditText;

    public static IssueCommentsFragment newInstance(Bundle e) {
        IssueCommentsFragment frg = new IssueCommentsFragment();
        frg.setArguments(e);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.comments_fragment_layout, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        sendComment = (LinearLayout) rootView.findViewById(R.id.send_comment_to_server);
        commentEditText = (EditText) rootView.findViewById(R.id.comment_box);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        issueId = getArguments().getString("issueid");

        sendComment.setOnClickListener(this);

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
            requestUrl = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getIssueCommentsUrl(issueId, startAt, pageSize);

            AppRequests.makeGetAllIssueCommentsRequest(requestUrl, this, getActivity());
        }
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_COMMENTS_FOR_ISSUE)) {
            isRequestRunning = true;
            hideErrorLayout();
            showProgressLayout();
        } else if (requestTag.equalsIgnoreCase(RequestTags.ADD_COMMENT_ON_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();

            progressDialog = ProgressDialog.show(getActivity(), "Add Comment", "Please wait while posting your comment", true, false);
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_COMMENTS_FOR_ISSUE)) {
            isRequestRunning = false;
            showErrorLayout();
            hideProgressLayout();

            try {
                IssueCommentsObject issuesData = (IssueCommentsObject) VolleyUtils.getResponseFromCache(IssueCommentsObject.class, requestUrl);
                setAdapterData(issuesData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestTag.equalsIgnoreCase(RequestTags.ADD_COMMENT_ON_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();

            ((BaseActivity) getActivity()).makeToast("Unable to comment. Please check that you have required access to post comment on this issue and check your internet settings and try again.");
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, JSONObject response) {
        if (requestTag.equalsIgnoreCase(RequestTags.ADD_COMMENT_ON_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();

            if (getActivity() != null) {
                commentEditText.setText("");
                ((BaseActivity) getActivity()).makeToast("Successfully posted comment");
                UIUtils.hideSoftKeyboard(getActivity());
            }

            Comment comment = (Comment) VolleyUtils.getResponseObject(response, Comment.class);
            if (adapter != null && comment != null) {
                adapter.addToTop(comment);
                recyclerView.scrollToPosition(0);
            }
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_COMMENTS_FOR_ISSUE)) {
            isRequestRunning = false;
            IssueCommentsObject issuesData = (IssueCommentsObject) VolleyUtils.getResponseObject(response, IssueCommentsObject.class);
            setAdapterData(issuesData);
        }
    }

    private void setAdapterData(IssueCommentsObject issuesData) {
        hideProgressLayout();
        hideErrorLayout();

        try {
            if (issuesData.getComments() != null) {
                if (issuesData.getComments().size() < pageSize) {
                    isMoreAllowed = false;
                } else {
                    isMoreAllowed = true;
                    startAt = startAt + pageSize;
                }
            }

            if (adapter == null) {
                adapter = new IssuesCommentsListAdapter(issuesData.getComments(), getActivity(), isMoreAllowed);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.addData(issuesData.getComments(), isMoreAllowed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_comment_to_server:
                if (commentEditText.getText().toString().trim().length() > 0) {
                    sendCommentToServer(commentEditText.getText().toString().trim());
                } else {
                    ((BaseActivity) getActivity()).makeToast("Please enter comment first.");
                }
                break;
        }
    }

    private void sendCommentToServer(String comment) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("body", comment);

            String url = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getAddCommentOnIssuePostUrl(issueId);
            AppRequests.makeAddCommentOnIssueRequest(url, this, getActivity(), jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
