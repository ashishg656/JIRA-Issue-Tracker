package com.ashish.jiraissuetracker.adapters;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.BaseActivity;
import com.ashish.jiraissuetracker.extras.AppConstants;
import com.ashish.jiraissuetracker.glideImageRequest.GlideRequestManager;
import com.ashish.jiraissuetracker.objects.issues.Issue;
import com.ashish.jiraissuetracker.utils.TimeUtils;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Ashish on 10/06/16.
 */
public class IssuesFragmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AppConstants {

    List<Issue> mData;
    Context context;
    boolean isMoreAllowed;
    MyClickListener clickListener;

    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilderIssueType, requestBuilderIssuePriority;

    public IssuesFragmentListAdapter(List<Issue> mData, Context context, boolean isMoreAllowed) {
        this.mData = mData;
        this.context = context;
        this.isMoreAllowed = isMoreAllowed;
        clickListener = new MyClickListener();

        requestBuilderIssuePriority = GlideRequestManager.getRequestBuilder(context, R.drawable.issue_priority_image);
        requestBuilderIssueType = GlideRequestManager.getRequestBuilder(context, R.drawable.issue_priority_image);
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

            holder.status.setText(issue.getFields().getStatus().getName());
            holder.status.setTag(R.integer.z_tag_position, position);
            holder.status.setOnClickListener(clickListener);

            holder.issueContainer.setTag(R.integer.z_tag_holder, issue.getKey());
            holder.issueContainer.setTag(R.integer.z_tag_position, issue.getId());
            holder.issueContainer.setOnClickListener(clickListener);

            if (issue.getFields().getUpdated() == null) {
                holder.updateTime.setVisibility(View.GONE);
            } else {
                holder.updateTime.setVisibility(View.VISIBLE);
                holder.updateTime.setText("Last updated " + TimeUtils.getPostTimeGMTActivityStream(issue.getFields().getUpdated()));
            }

            try {
                Uri uri = Uri.parse(issue.getFields().getIssuetype().getIconUrl());
                requestBuilderIssueType.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .load(uri)
                        .into(holder.typeImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Uri uri = Uri.parse(issue.getFields().getPriority().getIconUrl());
                requestBuilderIssuePriority.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .load(uri)
                        .into(holder.priorityImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    public void changeIssueStatus(String issueid, String newstatus) {
        if (mData != null) {
            for (int i = 0; 1 < mData.size(); i++) {
                if (mData.get(i).getId().equalsIgnoreCase(issueid)) {
                    mData.get(i).getFields().getStatus().setName(newstatus);
                    notifyItemChanged(i);
                    return;
                }
            }
        }
    }

    public void addData(List<Issue> issues, boolean isMoreAllowed) {
        this.isMoreAllowed = isMoreAllowed;
        mData.addAll(issues);

        notifyDataSetChanged();
    }

    private class IssueHolder extends RecyclerView.ViewHolder {

        TextView key, summary, type, priority, status, updateTime;
        ImageView typeImage, priorityImage;
        FrameLayout issueContainer;

        public IssueHolder(View v) {
            super(v);
            key = (TextView) v.findViewById(R.id.issue_key);
            summary = (TextView) v.findViewById(R.id.issue_summary);
            type = (TextView) v.findViewById(R.id.issue_type);
            priority = (TextView) v.findViewById(R.id.issue_priority);
            status = (TextView) v.findViewById(R.id.issue_status);
            typeImage = (ImageView) v.findViewById(R.id.issue_type_image);
            priorityImage = (ImageView) v.findViewById(R.id.issue_priority_image);
            updateTime = (TextView) v.findViewById(R.id.issue_update_time);
            issueContainer = (FrameLayout) v.findViewById(R.id.issue_container);
        }
    }

    private class LoadingHolder extends RecyclerView.ViewHolder {

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
                case R.id.issue_container:
                    String issueId = (String) view.getTag(R.integer.z_tag_position);
                    String issueKey = (String) view.getTag(R.integer.z_tag_holder);
                    ((BaseActivity) context).openIssueDetailActivity(issueId, issueKey);
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
