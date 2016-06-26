package com.ashish.jiraissuetracker.activities;

import android.os.Bundle;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.fragments.FilterIssuesFragment;

/**
 * Created by Ashish on 24/06/16.
 */
public class FilterIssuesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_issues_activity_layout);

        setFilterIssueFragment();
    }

    private void setFilterIssueFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FilterIssuesFragment.newInstance(new Bundle()))
                .commitAllowingStateLoss();
    }
}
