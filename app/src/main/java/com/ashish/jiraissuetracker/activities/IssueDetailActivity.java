package com.ashish.jiraissuetracker.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.fragments.ActivityStreamFragment;
import com.ashish.jiraissuetracker.fragments.IssueCommentsFragment;
import com.ashish.jiraissuetracker.fragments.IssueDetailMainFragment;
import com.ashish.jiraissuetracker.fragments.IssuesFragment;
import com.ashish.jiraissuetracker.fragments.ProjectListingFragment;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

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

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Bundle bundle = new Bundle();

            bundle.putString("issueid", issueId);

            Fragment fragment = null;
            if (pos == 0)
                fragment = IssueDetailMainFragment.newInstance(bundle);
            else if (pos == 1)
                fragment = IssueCommentsFragment.newInstance(bundle);
            else if (pos == 2)
                fragment = ProjectListingFragment.newInstance(bundle);
            else
                fragment = IssuesFragment.newInstance(bundle);

            fragmentHashMap.put(pos, fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Issue Details";
            } else if (position == 1) {
                return "Comments";
            } else if (position == 2) {
                return "Projects";
            } else {
                return "Dashboards";
            }
        }
    }
}
