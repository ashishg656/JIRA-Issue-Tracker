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
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.fragments.IssuesFragment;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.serverApi.ImageRequestManager;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 05/06/16.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    MyPagerAdapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView navigationDrawerUserName, navigationDrawerEmail;
    CircleImageView navigationDrawerImageUser;
    FrameLayout navigationDrawerHeaderLayout;
    AppBarLayout appBarLayout;
    int deviceHeight, deviceWidth;

    AlertDialog alertDialog;
    ProgressDialog progressDialog;

    HashMap<Integer, Fragment> fragmentHashMap;

    @Override
    void broadcastForIssueStatusChangeReceived(Intent intent) {

    }

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
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout);

        View header = navigationView.getHeaderView(0);

        navigationDrawerEmail = (TextView) header.findViewById(R.id.navdraweremail);
        navigationDrawerImageUser = (CircleImageView) header.findViewById(R.id.avatar);
        navigationDrawerUserName = (TextView) header.findViewById(R.id.navdrawerusername);
        navigationDrawerHeaderLayout = (FrameLayout) header.findViewById(R.id.navigationdrawerheader);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("JIRA Issue Tracker");

        navigationDrawerEmail.setText(ZPreferences.getUserEmail(this));
        navigationDrawerUserName.setText(ZPreferences.getUserName(this));
        ImageRequestManager.requestImage(navigationDrawerImageUser, ZPreferences.getUserImageURL(this));

        navigationDrawerHeaderLayout.setOnClickListener(this);

        setDrawerActionBarToggle();
        setDrawerItemClickListener();
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setDrawerActionBarToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.z_open_drawer, R.string.z_close_drawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setItemIconTintList(null);
    }

    private void setDrawerItemClickListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.navigationdrawerheader:

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

    class MyPagerAdapter extends FragmentStatePagerAdapter {

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
                fragment = IssuesFragment.newInstance(bundle);
            else if (pos == 3)
                fragment = IssuesFragment.newInstance(bundle);
            else
                fragment = IssuesFragment.newInstance(bundle);

            fragmentHashMap.put(pos, fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "My Issues";
            } else if (position == 1) {
                return "Activity Stream";
            } else if (position == 3) {
                return "Boards";
            } else {
                return "Dashboards";
            }
        }
    }
}
