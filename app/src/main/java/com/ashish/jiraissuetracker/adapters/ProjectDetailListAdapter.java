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
import com.ashish.jiraissuetracker.objects.projectListing.ProjectListingObject;
import com.ashish.jiraissuetracker.utils.TimeUtils;
import com.ashish.jiraissuetracker.utils.UIUtils;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 14/06/16.
 */
public class ProjectDetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AppConstants {

    Context context;
    ProjectListingObject projectListingObject;
    List<Issue> issuesList;

    boolean isMoreAllowed;
    MyClickListener clickListener;

    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder, requestBuilderIssueType, requestBuilderIssuePriority;

    public ProjectDetailListAdapter(Context context, ProjectListingObject mData) {
        this.context = context;
        this.projectListingObject = mData;
        clickListener = new MyClickListener();

        requestBuilder = GlideRequestManager.getRequestBuilder(context, R.drawable.test_user);
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
        } else if (viewType == TYPE_RECYCLER_VIEW_LOADING_ADDITIONAL_CONTENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.user_profile_loading_activity_stream_list_item_layout, parent, false);
            LoadingAdditionalContentHolder holder = new LoadingAdditionalContentHolder(view);
            return holder;
        } else if (viewType == TYPE_RECYCLER_VIEW_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.project_detail_list_header_layout, parent, false);
            HeaderHolder holder = new HeaderHolder(view);
            return holder;
        }
        return null;
    }

    public void addData(List<Issue> issues, boolean isMoreAllowed) {
        if (this.issuesList == null) {
            this.issuesList = new ArrayList<>();
        }
        this.issuesList.addAll(issues);
        this.isMoreAllowed = isMoreAllowed;

        notifyDataSetChanged();
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {

        TextView name, type, lead, description, key;
        CircleImageView imageView, projectLeadImage;
        FrameLayout projectLeadContainer;

        public HeaderHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.project_name);
            type = (TextView) v.findViewById(R.id.project_type);
            lead = (TextView) v.findViewById(R.id.project_lead);
            description = (TextView) v.findViewById(R.id.project_description);
            key = (TextView) v.findViewById(R.id.project_key);
            imageView = (CircleImageView) v.findViewById(R.id.project_image);
            projectLeadImage = (CircleImageView) v.findViewById(R.id.project_lead_image);
            projectLeadContainer = (FrameLayout) v.findViewById(R.id.project_lead_container);
        }
    }

    private class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(View v) {
            super(v);
        }
    }

    private class LoadingAdditionalContentHolder extends RecyclerView.ViewHolder {

        TextView text;

        public LoadingAdditionalContentHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.loading_more_text_additional);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderCom, int position) {
        position = holderCom.getAdapterPosition();
        if (getItemViewType(position) == TYPE_RECYCLER_VIEW_NORMAL) {
            IssueHolder holder = (IssueHolder) holderCom;

            Issue issue = issuesList.get(position - 1);
            holder.key.setText(issue.getKey());
            holder.summary.setText(issue.getFields().getSummary());
            holder.type.setText(issue.getFields().getIssuetype().getName());
            holder.priority.setText(issue.getFields().getPriority().getName());
            holder.typeImage.setImageResource(UIUtils.loadIssueTypeImageFromIssueTypeString(issue.getFields().getIssuetype().getName(),
                    issue.getFields().getIssuetype().getSubtask()));

            holder.status.setText(issue.getFields().getStatus().getName());
            holder.status.setTag(R.integer.z_tag_position, position - 1);
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
        } else if (getItemViewType(position) == TYPE_RECYCLER_VIEW_HEADER) {
            HeaderHolder holder = (HeaderHolder) holderCom;

            try {
                String url = projectListingObject.getAvatarUrls().get48x48();
//                url = url.substring(0, url.length() - 2);
//                url = url + "250";

                Uri uri = Uri.parse(url);
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .load(uri)
                        .into(holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Uri uri = Uri.parse(projectListingObject.getLead().getAvatarUrls().get48x48());
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .load(uri)
                        .into(holder.projectLeadImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.name.setText(projectListingObject.getName());
            holder.key.setText(projectListingObject.getKey());
            holder.description.setText(projectListingObject.getDescription());
            holder.lead.setText(projectListingObject.getLead().getDisplayName());
            holder.type.setText(projectListingObject.getProjectTypeKey());

            holder.projectLeadContainer.setTag(projectListingObject.getLead().getName());
            holder.projectLeadContainer.setOnClickListener(clickListener);
        } else if (getItemViewType(position) == TYPE_RECYCLER_VIEW_LOADING_ADDITIONAL_CONTENT) {
            LoadingAdditionalContentHolder holder = (LoadingAdditionalContentHolder) holderCom;
            holder.text.setText("Loading issues for project..");
        }
    }

    public void changeIssueStatus(String issueid, String newstatus) {
        if (issuesList != null) {
            for (int i = 0; 1 < issuesList.size(); i++) {
                if (issuesList.get(i).getId().equalsIgnoreCase(issueid)) {
                    issuesList.get(i).getFields().getStatus().setName(newstatus);
                    notifyItemChanged(i + 1);
                    return;
                }
            }
        }
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

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return AppConstants.TYPE_RECYCLER_VIEW_HEADER;
        } else {
            if (issuesList == null) {
                return AppConstants.TYPE_RECYCLER_VIEW_LOADING_ADDITIONAL_CONTENT;
            } else {
                if (position - 1 < issuesList.size()) {
                    return AppConstants.TYPE_RECYCLER_VIEW_NORMAL;
                } else {
                    return AppConstants.TYPE_RECYCLER_VIEW_LOADING;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (projectListingObject == null) {
            return 0;
        } else {
            size = 1;
            if (issuesList == null) {
                size = size + 1;
            } else {
                if (isMoreAllowed) {
                    size = size + issuesList.size() + 1;
                } else {
                    size = size + issuesList.size();
                }
            }
        }
        return size;
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
                case R.id.project_lead_container:
                    String userName = (String) view.getTag();
                    ((BaseActivity) context).openUserProfileActivity(userName);
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
        b.putString("projectid", issuesList.get(position).getFields().getProject().getId());
        b.putString("issuetype", issuesList.get(position).getFields().getIssuetype().getName());
        b.putString("currentStatus", issuesList.get(position).getFields().getStatus().getName());
        b.putString("issueid", issuesList.get(position).getId());

        ((BaseActivity) context).changeFragmentToChangeIssueStatusFragment(b);
    }

}
