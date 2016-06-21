package com.ashish.jiraissuetracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.fragments.ChangeIssueStatusFragment;
import com.ashish.jiraissuetracker.fragments.IssueDetailCommentsFragment;
import com.ashish.jiraissuetracker.objects.getIssueTransitions.Transition;
import com.ashish.jiraissuetracker.objects.projectListing.ProjectListingObject;

import java.util.ArrayList;
import java.util.List;

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

    public void changeFragmentToChangeIssueStatusFragment(String projectId, String issueType, String currentStatus, String issueId) {
        Bundle bundle = new Bundle();
        bundle.putString("projectid", projectId);
        bundle.putString("issuetype", issueType);
        bundle.putString("currentStatus", currentStatus);
        bundle.putString("issueid", issueId);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ChangeIssueStatusFragment.newInstance(bundle), "IssueChange")
                .addToBackStack("IssueChange")
                .commitAllowingStateLoss();
    }

    public void changeFragmentToChangeIssueStatusFragment(List<Transition> transitions, String currentStatus, String issueId) {
        Bundle bundle = new Bundle();
        bundle.putString("currentStatus", currentStatus);
        bundle.putString("issueid", issueId);
        bundle.putParcelableArrayList("transitionslist", new ArrayList<Parcelable>(transitions));

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ChangeIssueStatusFragment.newInstance(bundle), "IssueChange")
                .addToBackStack("IssueChange")
                .commitAllowingStateLoss();
    }

    public void changeFragmentToIssueCommentsFragment(String issueId) {
        Bundle bundle = new Bundle();
        bundle.putString("issueid", issueId);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, IssueDetailCommentsFragment.newInstance(bundle), "IssueComments")
                .addToBackStack("IssueComments")
                .commitAllowingStateLoss();
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

    public void openProjectDetailActivity(String projectKey) {
        Intent intent = new Intent(this, ProjectDetailActivity.class);
        intent.putExtra("projectid", projectKey);
        startActivity(intent);
    }

    public void openIssueDetailActivity(String issueId, String issueKey) {
        Intent intent = new Intent(this, IssueDetailActivity.class);
        intent.putExtra("issueid", issueId);
        intent.putExtra("issuekey", issueKey);
        startActivity(intent);
    }
}