package com.ashish.jiraissuetracker.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.jiraissuetracker.extras.LocalBroadcastTypes;

/**
 * Created by Ashish on 04/06/16.
 */
public abstract class BaseFragment extends Fragment {

    View rootView;
    String requestUrl;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int type = intent.getIntExtra("type", -1);
                if (type == LocalBroadcastTypes.TYPE_ISSUE_STATUS_CHANGE) {
                    broadcastForIssueStatusChangeReceived(intent);
                }
            }
        }
    };

    abstract void broadcastForIssueStatusChangeReceived(Intent intent);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
