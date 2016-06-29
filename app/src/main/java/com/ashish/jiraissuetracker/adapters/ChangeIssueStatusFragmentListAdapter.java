package com.ashish.jiraissuetracker.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.BaseActivity;
import com.ashish.jiraissuetracker.broadcasts.LocalBroadcastMaker;
import com.ashish.jiraissuetracker.requests.AppUrls;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.getIssueTransitions.Transition;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListenerJsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Ashish on 11/06/16.
 */
public class ChangeIssueStatusFragmentListAdapter extends BaseAdapter implements AppRequestListenerJsonObject {

    List<Transition> mData;
    String currentStatus, issueId;
    Context context;
    String newStatus;

    LayoutInflater layoutInflater;
    MyClickListener clickListener;
    ProgressDialog progressDialog;

    public ChangeIssueStatusFragmentListAdapter(String issueId, List<Transition> mData, String currentStatus, Context context) {
        if (context == null) {
            return;
        }

        this.mData = mData;
        this.currentStatus = currentStatus;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        clickListener = new MyClickListener();
        this.issueId = issueId;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.change_issue_status_fragment_list_item_layout, viewGroup, false);

        TextView text = (TextView) view.findViewById(R.id.change_issue_text);
        ImageView tick = (ImageView) view.findViewById(R.id.change_issue_tick);

        if (mData.get(i).getName().equalsIgnoreCase(currentStatus)) {
            tick.setVisibility(View.VISIBLE);
        } else {
            tick.setVisibility(View.GONE);
        }

        text.setTag(i);
        text.setOnClickListener(clickListener);

        text.setText(mData.get(i).getName());

        return view;
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.POST_TRANSITIONS_FOR_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = ProgressDialog.show(context, "Please Wait", "Changing Issue Status", true, false);
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.POST_TRANSITIONS_FOR_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();

            ((BaseActivity) context).makeToast("Unable to change issue status. Please check internet and try again.");
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, JSONObject response) {
        if (requestTag.equalsIgnoreCase(RequestTags.POST_TRANSITIONS_FOR_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();

            ((BaseActivity) context).makeToast("Issue status changed successfully.");
            LocalBroadcastMaker.makeBroadcastForIssueStatusChange(issueId, newStatus, context);
            ((BaseActivity) context).onBackPressed();
        }
    }

    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            if (!mData.get(pos).getName().equalsIgnoreCase(currentStatus)) {
                changeIssueStatus(pos);
            }
        }
    }

    private void changeIssueStatus(int pos) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject transitionObject = new JSONObject();
            transitionObject.put("id", mData.get(pos).getId());
            jsonObject.put("transition", transitionObject);

            newStatus = mData.get(pos).getName();

            String url = ZPreferences.getBaseUrl(context) + AppUrls.getIssueTransitionsPost(issueId);
            AppRequests.makePostIssueTransitionsRequest(url, this, context, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
