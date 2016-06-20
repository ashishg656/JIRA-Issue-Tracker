package com.ashish.jiraissuetracker.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.BaseActivity;
import com.ashish.jiraissuetracker.extras.AppConstants;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.objects.issueComments.Comment;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.requests.AppUrls;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.serverApi.ImageRequestManager;
import com.ashish.jiraissuetracker.utils.TimeUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 10/06/16.
 */
public class IssuesCommentsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AppConstants, AppRequestListener {

    List<Comment> mData;
    Context context;
    boolean isMoreAllowed;
    MyClickListener clickListener;
    int greenColor, redColor;
    String issueId;

    CommentHolder deleteCurrentHolder;
    ProgressDialog progressDialog;

    public IssuesCommentsListAdapter(List<Comment> mData, Context context, boolean isMoreAllowed, String issueId) {
        this.mData = mData;
        this.context = context;
        this.isMoreAllowed = isMoreAllowed;
        clickListener = new MyClickListener();
        this.issueId = issueId;

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

            holder.delete.setTag(holder);
            holder.delete.setOnClickListener(clickListener);
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

    public void addToTop(Comment comment) {
        if (mData != null) {
            mData.add(0, comment);
            notifyItemInserted(0);
        }
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
                case R.id.delete_comment:
                    CommentHolder holder = (CommentHolder) view.getTag();
                    showAlertDialogForDeleteComment(holder);
                    break;
            }
        }
    }

    private void showAlertDialogForDeleteComment(final CommentHolder position) {
        deleteCurrentHolder = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(context).setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String url = ZPreferences.getBaseUrl(context) + AppUrls.getDeleteCommentUrl(issueId, mData.get(deleteCurrentHolder.getAdapterPosition()).getId());
                AppRequests.makeDeleteCommentRequest(url, IssuesCommentsListAdapter.this, context);
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setMessage("Are you sure you want to delete this comment?")
                .setTitle("Delete comment");

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.DELETE_COMMENT_FROM_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();

            progressDialog = ProgressDialog.show(context, "Deleting comment", "Please wait while deleting comment", true, false);
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.DELETE_COMMENT_FROM_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();

            ((BaseActivity) context).makeToast("Unable to delete comment. Please check internet settings and try again.");
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.DELETE_COMMENT_FROM_ISSUE)) {
            if (progressDialog != null)
                progressDialog.dismiss();

            ((BaseActivity) context).makeToast("Successfully deleted comment.");
            mData.remove(deleteCurrentHolder.getAdapterPosition());
            notifyItemRemoved(deleteCurrentHolder.getAdapterPosition());
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
