package com.ashish.jiraissuetracker.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.BaseActivity;
import com.ashish.jiraissuetracker.activities.HomeActivity;
import com.ashish.jiraissuetracker.extras.AppConstants;
import com.ashish.jiraissuetracker.extras.AppUrls;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.issues.Fields;
import com.ashish.jiraissuetracker.objects.issues.Issue;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.serverApi.ImageRequestManager;
import com.ashish.jiraissuetracker.utils.UIUtils;

import java.util.List;

/**
 * Created by Ashish on 10/06/16.
 */
public class IssuesFragmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AppConstants {

    List<Issue> mData;
    Context context;
    boolean isMoreAllowed;
    MyClickListener clickListener;

    public IssuesFragmentListAdapter(List<Issue> mData, Context context, boolean isMoreAllowed) {
        this.mData = mData;
        this.context = context;
        this.isMoreAllowed = isMoreAllowed;
        clickListener = new MyClickListener();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_RECYCLER_VIEW_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.issues_fragment_list_item_layout, parent, false);
            IssueHolder holder = new IssueHolder(view);
            return holder;
        } else if (viewType == TYPE_RECYCLER_VIEW_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.loading_more_layout, parent, false);
            LoadingHolder holder = new LoadingHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderCom, int position) {
        position = holderCom.getAdapterPosition();
        if (getItemViewType(position) == TYPE_RECYCLER_VIEW_NORMAL) {
            IssueHolder holder = (IssueHolder) holderCom;

            Issue issue = mData.get(position);
            holder.key.setText(issue.getKey());
            holder.summary.setText(issue.getFields().getSummary());
            holder.type.setText(issue.getFields().getIssuetype().getName());
            holder.priority.setText(issue.getFields().getPriority().getName());
            holder.typeImage.setImageResource(UIUtils.loadIssueTypeImageFromIssueTypeString(issue.getFields().getIssuetype().getName(),
                    issue.getFields().getIssuetype().getSubtask()));

            holder.status.setText(issue.getFields().getStatus().getName());
            holder.status.setTag(R.integer.z_tag_position, position);
            holder.status.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mData.size()) {
            return TYPE_RECYCLER_VIEW_NORMAL;
        } else {
            return TYPE_RECYCLER_VIEW_LOADING;
        }
    }

    class IssueHolder extends RecyclerView.ViewHolder {

        TextView key, summary, type, priority, status;
        ImageView typeImage, priorityImage;

        public IssueHolder(View v) {
            super(v);
            key = (TextView) v.findViewById(R.id.issue_key);
            summary = (TextView) v.findViewById(R.id.issue_summary);
            type = (TextView) v.findViewById(R.id.issue_type);
            priority = (TextView) v.findViewById(R.id.issue_priority);
            status = (TextView) v.findViewById(R.id.issue_status);
            typeImage = (ImageView) v.findViewById(R.id.issue_type_image);
            priorityImage = (ImageView) v.findViewById(R.id.issue_priority_image);
        }
    }

    class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(View v) {
            super(v);
        }
    }

    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.issue_status:
                    int position = (int) view.getTag(R.integer.z_tag_position);
                    try {
                        fetchStatusesForProject(position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void fetchStatusesForProject(int position) {
        Bundle b = new Bundle();
        b.putString("projectid", mData.get(position).getFields().getProject().getId());
        b.putString("issuetype", mData.get(position).getFields().getIssuetype().getName());
        b.putString("currentStatus", mData.get(position).getFields().getStatus().getName());
        b.putString("issueid", mData.get(position).getId());

        ((BaseActivity) context).changeFragmentToChangeIssueStatusFragment(b);
    }

    @Override
    public int getItemCount() {
        if (isMoreAllowed) {
            return mData.size() + 1;
        } else {
            return mData.size();
        }
    }
}
