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
    List<String> selectedAssignee, selectedReporter, selectedprojects, selectedPriorities, selectedResolution, selectedStatus, selectedType;
    String text, issueKey, labels;

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

    @Override
    public List<String> getSelectedAssignee() {
        return selectedAssignee;
    }

    @Override
    public void setSelectedAssignee(List<String> selectedAssignee) {
        this.selectedAssignee = selectedAssignee;
    }

    @Override
    public List<String> getSelectedReporter() {
        return selectedReporter;
    }

    @Override
    public void setSelectedReporter(List<String> selectedReporter) {
        this.selectedReporter = selectedReporter;
    }

    @Override
    public List<String> getSelectedprojects() {
        return selectedprojects;
    }

    @Override
    public void setSelectedprojects(List<String> selectedprojects) {
        this.selectedprojects = selectedprojects;
    }

    @Override
    public List<String> getSelectedPriorities() {
        return selectedPriorities;
    }

    @Override
    public void setSelectedPriorities(List<String> selectedPriorities) {
        this.selectedPriorities = selectedPriorities;
    }

    @Override
    public List<String> getSelectedResolution() {
        return selectedResolution;
    }

    @Override
    public void setSelectedResolution(List<String> selectedResolution) {
        this.selectedResolution = selectedResolution;
    }

    @Override
    public List<String> getSelectedStatus() {
        return selectedStatus;
    }

    @Override
    public void setSelectedStatus(List<String> selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    @Override
    public List<String> getSelectedType() {
        return selectedType;
    }

    @Override
    public void setSelectedType(List<String> selectedType) {
        this.selectedType = selectedType;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getIssueKey() {
        return issueKey;
    }

    @Override
    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    @Override
    public String getLabels() {
        return labels;
    }

    @Override
    public void setLabels(String labels) {
        this.labels = labels;
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
