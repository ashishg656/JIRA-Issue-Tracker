package com.ashish.jiraissuetracker.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.adapters.ActivityStreamFragmentListAdapter;
import com.ashish.jiraissuetracker.adapters.IssuesFragmentListAdapter;
import com.ashish.jiraissuetracker.extras.AppUrls;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.activityStream.ActivityStreamObject;
import com.ashish.jiraissuetracker.objects.activityStream.ActivityStreamObjectNonArray;
import com.ashish.jiraissuetracker.objects.activityStream.Entry;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.DebugUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashish on 12/06/16.
 */
public class ActivityStreamFragment extends BaseFragment implements AppRequestListener {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ActivityStreamFragmentListAdapter adapter;

    int startAt = 0;
    int pageSize = 20;
    boolean isMoreAllowed = true;

    public static ActivityStreamFragment newInstance(Bundle e) {
        ActivityStreamFragment frg = new ActivityStreamFragment();
        frg.setArguments(e);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.issues_fragment_layout, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        setProgressAndErrorLayoutVariables();

        loadData();
    }

    void loadData() {
        if (isMoreAllowed) {
            requestUrl = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getActivityStreamUrl(startAt, pageSize);

            AppRequests.makeActivityStreamRequest(requestUrl, this, getActivity());
        }
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {
            hideErrorLayout();
            showProgressLayout();
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {
            hideProgressLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.ACTIVITY_STREAM)) {
            getDataFromXml(response);
        }
    }

    private void getDataFromXml(String response) {
        JSONObject jsonObj = null;
        try {
            DebugUtils.log("Starting XML to Json Process");
            jsonObj = XML.toJSONObject(response);
            try {
                ActivityStreamObject object = new Gson().fromJson(String.valueOf(jsonObj), ActivityStreamObject.class);
                setAdapterData(object);
            } catch (Exception e) {
                try {
                    ActivityStreamObjectNonArray object = new Gson().fromJson(String.valueOf(jsonObj), ActivityStreamObjectNonArray.class);
                    DebugUtils.log("Got Data from single XML ActivityStreamObjectNonArray");
                    setAdapterData(object);
                } catch (Exception e1) {
                    e1.printStackTrace();

                    hideErrorLayout();
                    hideProgressLayout();
                }
            }
            DebugUtils.log("Finished XML to Json Conversion and mapped Json to ActivityStreamObject");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAdapterData(ActivityStreamObjectNonArray object) {
        isMoreAllowed = false;

        List<Entry> listEntry = new ArrayList<>();
        listEntry.add(object.getFeed().getEntry());
        if (adapter == null) {
            adapter = new ActivityStreamFragmentListAdapter(listEntry, getActivity(), isMoreAllowed);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.addData(listEntry, isMoreAllowed);
        }
    }

    private void setAdapterData(ActivityStreamObject object) {
        if (object != null && object.getFeed() != null && object.getFeed().getEntry() != null) {
            if (object.getFeed().getEntry().size() < pageSize) {
                isMoreAllowed = false;
            } else {
                isMoreAllowed = true;
            }
        }

        if (adapter == null) {
            adapter = new ActivityStreamFragmentListAdapter(object.getFeed().getEntry(), getActivity(), isMoreAllowed);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.addData(object.getFeed().getEntry(), isMoreAllowed);
        }
    }
}
