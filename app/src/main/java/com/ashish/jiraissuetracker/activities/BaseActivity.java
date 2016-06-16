package com.ashish.jiraissuetracker.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.extras.LocalBroadcastTypes;
import com.ashish.jiraissuetracker.fragments.ChangeIssueStatusFragment;
import com.ashish.jiraissuetracker.objects.projectListing.ProjectListingObject;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.serverApi.ImageRequestManager;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 04/06/16.
 */
public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    Toast toast;

    public void makeToast(String message) {
        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(this, message, toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void changeFragmentToChangeIssueStatusFragment(Bundle b) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ChangeIssueStatusFragment.newInstance(b), "IssueChange")
                .addToBackStack("IssueChange")
                .commitAllowingStateLoss();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setProgressAndErrorLayoutVariables() {

    }

    public void showProgressLayout() {

    }

    public void hideProgressLayout() {

    }

    public void showErrorLayout() {

    }

    public void hideErrorLayout() {

    }

    public void openUserProfileActivity(String userName) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("username", userName);
        startActivity(intent);
    }

    public void openProjectDetailActivity(ProjectListingObject projectKey) {
        Intent intent = new Intent(this, ProjectDetailActivity.class);
        intent.putExtra("projectobj", projectKey);
        startActivity(intent);
    }

    public void openIssueDetailActivity(String issueId, String issueKey) {
        Intent intent = new Intent(this, IssueDetailActivity.class);
        intent.putExtra("issueid", issueId);
        intent.putExtra("issuekey", issueKey);
        startActivity(intent);
    }
}