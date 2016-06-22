package com.ashish.jiraissuetracker.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.fragments.ActivityStreamFragment;
import com.ashish.jiraissuetracker.fragments.IssueDetailActivityStreamFragment;
import com.ashish.jiraissuetracker.fragments.IssueDetailChangelogFragment;
import com.ashish.jiraissuetracker.fragments.IssueDetailCommentsFragment;
import com.ashish.jiraissuetracker.fragments.IssueDetailMainFragment;
import com.ashish.jiraissuetracker.fragments.IssuesFragment;
import com.ashish.jiraissuetracker.fragments.ProjectListingFragment;

import java.util.HashMap;

/**
 * Created by Ashish on 16/06/16.
 */
public class IssueDetailActivity extends BaseActivityNavigationDrawer {

    ViewPager viewPager;
    TabLayout tabLayout;
    MyPagerAdapter adapter;
    AppBarLayout appBarLayout;
    int deviceHeight, deviceWidth;

    AlertDialog alertDialog;
    ProgressDialog progressDialog;

    HashMap<Integer, Fragment> fragmentHashMap;

    String issueId, issueKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);

        fragmentHashMap = new HashMap<>();

        deviceHeight = getResources().getDisplayMetrics().heightPixels;
        deviceWidth = getResources().getDisplayMetrics().widthPixels;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.pager_launch);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("JIRA Issue Tracker");

        setNavigationDrawerVariables();
        setDrawerItemClickListener();

        viewPager.setOffscreenPageLimit(3);

        issueId = getIntent().getExtras().getString("issueid");
        issueKey = getIntent().getExtras().getString("issuekey");

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void switchToFragment1IssueDetail() {
        try {
            viewPager.setCurrentItem(0, true);
            ((IssueDetailMainFragment) fragmentHashMap.get(0)).scrollRecyclerViewToTop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Bundle bundle = new Bundle();

            bundle.putString("issueid", issueId);
            bundle.putString("issuekey", issueKey);

            Fragment fragment = null;
            if (pos == 0)
                fragment = IssueDetailMainFragment.newInstance(bundle);
            else if (pos == 1)
                fragment = IssueDetailCommentsFragment.newInstance(bundle);
            else if (pos == 2)
                fragment = IssueDetailActivityStreamFragment.newInstance(bundle);
            else
                fragment = IssueDetailChangelogFragment.newInstance(bundle);

            fragmentHashMap.put(pos, fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Issue Details";
            } else if (position == 1) {
                return "Comments";
            } else if (position == 2) {
                return "Activity Stream";
            } else {
                return "Changelog";
            }
        }
    }
}
