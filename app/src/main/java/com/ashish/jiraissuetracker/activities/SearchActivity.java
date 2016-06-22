package com.ashish.jiraissuetracker.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ashish.jiraissuetracker.R;

/**
 * Created by Ashish on 23/06/16.
 */
public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
