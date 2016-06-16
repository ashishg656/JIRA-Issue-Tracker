package com.ashish.jiraissuetracker.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.serverApi.ImageRequestManager;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 16/06/16.
 */
public class BaseActivityNavigationDrawer extends BaseActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView navigationDrawerUserName, navigationDrawerEmail;
    CircleImageView navigationDrawerImageUser;
    FrameLayout navigationDrawerHeaderLayout;

    void setNavigationDrawerVariables() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        View header = navigationView.getHeaderView(0);

        navigationDrawerEmail = (TextView) header.findViewById(R.id.navdraweremail);
        navigationDrawerImageUser = (CircleImageView) header.findViewById(R.id.avatar);
        navigationDrawerUserName = (TextView) header.findViewById(R.id.navdrawerusername);
        navigationDrawerHeaderLayout = (FrameLayout) header.findViewById(R.id.navigationdrawerheader);

        navigationDrawerEmail.setText(ZPreferences.getUserEmail(this));
        navigationDrawerUserName.setText(ZPreferences.getUserName(this));
        ImageRequestManager.requestImage(navigationDrawerImageUser, ZPreferences.getUserImageURL(this));

        navigationDrawerHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserProfileActivity(ZPreferences.getUserProfileID(BaseActivityNavigationDrawer.this));
            }
        });
    }

    public void setDrawerActionBarToggle() {
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

    public void setDrawerItemClickListener() {
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
}
