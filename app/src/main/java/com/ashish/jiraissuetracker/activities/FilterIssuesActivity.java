package com.ashish.jiraissuetracker.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.fragments.FilterIssuesFragment;
import com.ashish.jiraissuetracker.fragments.SelectUserFragment;
import com.ashish.jiraissuetracker.interfaces.FilterIssueinterface;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListenerJsonObject;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Ashish on 24/06/16.
 */
public class FilterIssuesActivity extends BaseActivity implements FilterIssueinterface {

    public int selectedSortOrderPosition = 0;
    List<String> selectedAssignee, selectedReporter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_issues_activity_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra("openfilter") && getIntent().getBooleanExtra("openfilter", false)) {
            setFilterIssueFragment();
        }
    }

    private void setFilterIssueFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FilterIssuesFragment.newInstance(new Bundle()))
                .commitAllowingStateLoss();
    }

    @Override
    public int getSelectedSortOrderPosition() {
        return selectedSortOrderPosition;
    }

    @Override
    public void setSelectedSortOrderPosition(int selectedSortOrderPosition) {
        this.selectedSortOrderPosition = selectedSortOrderPosition;
    }

    public List<String> getSelectedAssignee() {
        return selectedAssignee;
    }

    public void setSelectedAssignee(List<String> selectedAssignee) {
        this.selectedAssignee = selectedAssignee;
    }

    public List<String> getSelectedReporter() {
        return selectedReporter;
    }

    public void setSelectedReporter(List<String> selectedReporter) {
        this.selectedReporter = selectedReporter;
    }

    @Override
    public void setFilterDataAgain() {
        try {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof FilterIssuesFragment) {
                    ((FilterIssuesFragment) fragment).setData();
                    return;
                }
            }
        } catch (Exception e) {

        }
    }
}
