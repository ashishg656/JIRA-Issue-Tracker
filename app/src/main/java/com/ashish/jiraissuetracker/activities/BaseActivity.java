package com.ashish.jiraissuetracker.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.extras.LocalBroadcastTypes;
import com.ashish.jiraissuetracker.fragments.ChangeIssueStatusFragment;

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
}