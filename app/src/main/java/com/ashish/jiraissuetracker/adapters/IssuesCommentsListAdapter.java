package com.ashish.jiraissuetracker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.BaseActivity;
import com.ashish.jiraissuetracker.extras.AppConstants;
import com.ashish.jiraissuetracker.glideImageRequest.GlideRequestManager;
import com.ashish.jiraissuetracker.objects.issueComments.Comment;
import com.ashish.jiraissuetracker.objects.issues.Issue;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.serverApi.ImageRequestManager;
import com.ashish.jiraissuetracker.utils.TimeUtils;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 10/06/16.
 */
public class IssuesCommentsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AppConstants {

    List<Comment> mData;
    Context context;
    boolean isMoreAllowed;
    MyClickListener clickListener;
    int greenColor, redColor;

    public IssuesCommentsListAdapter(List<Comment> mData, Context context, boolean isMoreAllowed) {
        this.mData = mData;
        this.context = context;
        this.isMoreAllowed = isMoreAllowed;
        clickListener = new MyClickListener();

        //noinspection ResourceType
        greenColor = Color.parseColor(context.getResources().getString(R.color.green_color_primary));
        //noinspection ResourceType
        redColor = Color.parseColor(context.getResources().getString(R.color.red_color_primary));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_RECYCLER_VIEW_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.comments_issue_list_item_layout, parent, false);
            CommentHolder holder = new CommentHolder(view);
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
            CommentHolder holder = (CommentHolder) holderCom;

            Comment comment = mData.get(position);

            holder.comment.setText(comment.getBody());
            holder.time.setText(TimeUtils.getPostTimeGMTActivityStream(comment.getUpdated()));
            ImageRequestManager.requestImage(holder.image, comment.getAuthor().getAvatarUrls().get48x48());
            holder.name.setText(comment.getAuthor().getDisplayName());

            holder.editIcon.setColorFilter(greenColor);
            holder.deleteIcon.setColorFilter(redColor);

            if (ZPreferences.getUserProfileID(context).equals(comment.getAuthor().getName())) {
                holder.actionButtonsLayout.setVisibility(View.VISIBLE);
            } else {
                holder.actionButtonsLayout.setVisibility(View.GONE);
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

    public void addData(List<Comment> issues, boolean isMoreAllowed) {
        this.isMoreAllowed = isMoreAllowed;
        mData.addAll(issues);

        notifyDataSetChanged();
    }

    private class CommentHolder extends RecyclerView.ViewHolder {

        TextView name, time, comment;
        CircleImageView image;
        LinearLayout edit, delete, actionButtonsLayout;
        FrameLayout container;
        ImageView editIcon, deleteIcon;

        public CommentHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.uploadrname);
            time = (TextView) v.findViewById(R.id.time);
            comment = (TextView) v.findViewById(R.id.comment);
            edit = (LinearLayout) v.findViewById(R.id.edit_comment);
            delete = (LinearLayout) v.findViewById(R.id.delete_comment);
            image = (CircleImageView) v.findViewById(R.id.circularimage);
            deleteIcon = (ImageView) v.findViewById(R.id.delete_comment_icon);
            editIcon = (ImageView) v.findViewById(R.id.edit_comment_icon);
            container = (FrameLayout) v.findViewById(R.id.commentlayouttoopenuserprofile);
            actionButtonsLayout = (LinearLayout) v.findViewById(R.id.action_buttons_comments_list);
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

            }
        }
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
