package com.ashish.jiraissuetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.jiraissuetracker.R;

/**
 * Created by Ashish on 07/06/16.
 */
public class IssuesFragment extends BaseFragment {

    public static IssuesFragment newInstance(Bundle e) {
        IssuesFragment frg = new IssuesFragment();
        frg.setArguments(e);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.issues_fragment_layout, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
