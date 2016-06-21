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
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.extras.LocalBroadcastTypes;
import com.ashish.jiraissuetracker.extras.ProgressLayoutAnimation;

/**
 * Created by Ashish on 04/06/16.
 */
public class BaseFragment extends Fragment {

    View rootView;
    String requestUrl;

    View progressDarkCircle, progressLightCircle;
    ImageView progressImage;
    FrameLayout progressLayoutContainer;
    ProgressLayoutAnimation animForProgressLayoutAnimated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setProgressAndErrorLayoutVariables() {
        progressDarkCircle = (View) rootView.findViewById(R.id.dark_circle);
        progressLightCircle = (View) rootView.findViewById(R.id.light_circle);
        progressImage = (ImageView) rootView.findViewById(R.id.image_progress);
        progressLayoutContainer = (FrameLayout) rootView.findViewById(R.id.progresslayutcontainre);
    }

    public void showProgressLayout() {
        animForProgressLayoutAnimated = new ProgressLayoutAnimation(
                progressDarkCircle, progressLightCircle, progressImage,
                progressLayoutContainer, getActivity());
        animForProgressLayoutAnimated.startAnimations();
    }

    public void hideProgressLayout() {
        if (animForProgressLayoutAnimated != null)
            animForProgressLayoutAnimated.stopAnimations();
    }

    public void showErrorLayout() {

    }

    public void hideErrorLayout() {

    }
}
