package com.ashish.jiraissuetracker.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.fragments.ActivityStreamFragment;
import com.ashish.jiraissuetracker.fragments.IssuesFragment;
import com.ashish.jiraissuetracker.fragments.ProjectListingFragment;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.serverApi.ImageRequestManager;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 05/06/16.
 */
public class HomeActivity extends BaseActivityNavigationDrawer implements View.OnClickListener, ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    MyPagerAdapter adapter;
    AppBarLayout appBarLayout;
    int deviceHeight, deviceWidth;

    AlertDialog alertDialog;
    ProgressDialog progressDialog;

    HashMap<Integer, Fragment> fragmentHashMap;

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

        findViewById(R.id.FloatingActionButton).setOnClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("JIRA Issue Tracker");

        setNavigationDrawerVariables();
        setDrawerActionBarToggle();
        setDrawerItemClickListener();

        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.FloatingActionButton:
                ((BaseActivity)this).openFilterIssuesActivity(true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_search_by_text){
            openSearchByTextActivity();   
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Bundle bundle = new Bundle();

            Fragment fragment = null;
            if (pos == 0)
                fragment = IssuesFragment.newInstance(bundle);
            else if (pos == 1)
                fragment = ActivityStreamFragment.newInstance(bundle);
            else if (pos == 2)
                fragment = ProjectListingFragment.newInstance(bundle);
            else
                fragment = IssuesFragment.newInstance(bundle);

            fragmentHashMap.put(pos, fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "My Issues";
            } else if (position == 1) {
                return "Activity Stream";
            } else if (position == 2) {
                return "Projects";
            } else {
                return "Dashboards";
            }
        }
    }
}
