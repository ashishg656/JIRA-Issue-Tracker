package com.ashish.jiraissuetracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.BaseActivity;
import com.ashish.jiraissuetracker.activities.UserProfileActivity;
import com.ashish.jiraissuetracker.extras.AppConstants;
import com.ashish.jiraissuetracker.objects.activityStream.Entry;
import com.ashish.jiraissuetracker.objects.issues.Issue;
import com.ashish.jiraissuetracker.utils.DebugUtils;
import com.ashish.jiraissuetracker.utils.TimeUtils;
import com.ashish.jiraissuetracker.utils.UIUtils;

import java.sql.Time;
import java.util.List;

/**
 * Created by Ashish on 10/06/16.
 */
public class ActivityStreamFragmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AppConstants {

    List<Entry> mData;
    Context context;
    boolean isMoreAllowed;
    MyClickListener clickListener;

    public ActivityStreamFragmentListAdapter(List<Entry> mData, Context context, boolean isMoreAllowed) {
        this.mData = mData;
        this.context = context;
        this.isMoreAllowed = isMoreAllowed;
        clickListener = new MyClickListener();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_RECYCLER_VIEW_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_stream_list_item_layout, parent, false);
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

            Entry entry = mData.get(position);
            setTextViewHTML(holder.text, entry.getTitle().getContent(), entry.getAuthor().getUsrUsername());

            holder.time.setText(TimeUtils.getPostTimeGMTActivityStream(entry.getUpdated()));

            if (entry.getSummary() != null && entry.getSummary().getContent() != null && entry.getSummary().getContent().length() > 0) {
                holder.description.setVisibility(View.VISIBLE);
                String text = entry.getSummary().getContent();
                holder.description.setText(Html.fromHtml(text));
            } else if (entry.getContent() != null && entry.getContent().getContent() != null && entry.getContent().getContent().length() > 0) {
                holder.description.setVisibility(View.VISIBLE);
                String text = entry.getContent().getContent();
                holder.description.setText(Html.fromHtml(text));
            } else {
                holder.description.setVisibility(View.GONE);
            }
        }
    }

    protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span, final String userName) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                DebugUtils.log("URL clicked", span.getURL() + " User : " + userName);

                if (span.getURL().toLowerCase().contains("viewprofile")) {
                    ((BaseActivity) context).openUserProfileActivity(userName);
                } else if (span.getURL().toLowerCase().contains("browse")) {

                }
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    protected void setTextViewHTML(TextView text, String html, String userName) {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for (URLSpan span : urls) {
            makeLinkClickable(strBuilder, span, userName);
        }
        text.setText(strBuilder);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mData.size()) {
            return TYPE_RECYCLER_VIEW_NORMAL;
        } else {
            return TYPE_RECYCLER_VIEW_LOADING;
        }
    }

    public void addData(List<Entry> entries, boolean isMoreAllowed) {
        this.isMoreAllowed = isMoreAllowed;
        mData.addAll(entries);

        notifyDataSetChanged();
    }

    private class IssueHolder extends RecyclerView.ViewHolder {

        TextView text, time, description;

        public IssueHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.activity_stream_text);
            time = (TextView) v.findViewById(R.id.activity_stream_time);
            description = (TextView) v.findViewById(R.id.activity_stream_description);
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
