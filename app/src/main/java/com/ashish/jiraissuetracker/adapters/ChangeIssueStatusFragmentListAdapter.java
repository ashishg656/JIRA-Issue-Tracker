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
import com.ashish.jiraissuetracker.extras.AppUrls;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.getAllStatusForProject.Status;
import com.ashish.jiraissuetracker.objects.getIssueTransitions.GetIssueTransitionObject;
import com.ashish.jiraissuetracker.objects.getIssueTransitions.Transition;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.VolleyUtils;

import java.util.List;

/**
 * Created by Ashish on 11/06/16.
 */
public class ChangeIssueStatusFragmentListAdapter extends BaseAdapter implements AppRequestListener {

    List<Status> mData;
    String currentStatus, issueId;
    Context context;
    LayoutInflater layoutInflater;
    MyClickListener clickListener;
    ProgressDialog progressDialog;

    public ChangeIssueStatusFragmentListAdapter(String issueId, List<Status> mData, String currentStatus, Context context) {
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
        if (requestTag.equalsIgnoreCase(RequestTags.GET_TRANSITIONS_FOR_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = ProgressDialog.show(context, "Please Wait", "Getting Issue Transitions", true, false);
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_TRANSITIONS_FOR_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();

            ((BaseActivity) context).makeToast("Unable to change issue status. Please check internet and try again.");
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.GET_TRANSITIONS_FOR_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();

            GetIssueTransitionObject obj = (GetIssueTransitionObject) VolleyUtils.getResponseObject(response, GetIssueTransitionObject.class);
            for (Transition transition : obj.getTransitions()) {

            }
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
        String url = ZPreferences.getBaseUrl(context) + AppUrls.getIssueTransitions(issueId);
        AppRequests.getIssueTransitions(url, this, context);
    }
}
