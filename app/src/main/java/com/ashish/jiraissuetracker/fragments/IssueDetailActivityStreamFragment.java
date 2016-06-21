package com.ashish.jiraissuetracker.fragments;

import android.os.Bundle;

import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.requests.AppUrls;

/**
 * Created by Ashish on 21/06/16.
 */
public class IssueDetailActivityStreamFragment extends ActivityStreamFragment {

    public static IssueDetailActivityStreamFragment newInstance(Bundle e) {
        IssueDetailActivityStreamFragment frg = new IssueDetailActivityStreamFragment();
        frg.setArguments(e);
        return frg;
    }

    void loadData() {
        if (isMoreAllowed) {
            requestUrl = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getActivityStreamForIssue(startAt, pageSize, issueKeyBundle, lastUpdated);

            AppRequests.makeActivityStreamRequest(requestUrl, this, getActivity());
        }
    }

    public void scrollRecyclerViewToTop() {
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }
}
