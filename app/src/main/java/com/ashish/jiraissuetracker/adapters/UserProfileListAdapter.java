package com.ashish.jiraissuetracker.adapters;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.BaseActivity;
import com.ashish.jiraissuetracker.activities.UserProfileActivity;
import com.ashish.jiraissuetracker.extras.AppConstants;
import com.ashish.jiraissuetracker.glideImageRequest.GlideRequestManager;
import com.ashish.jiraissuetracker.objects.activityStream.Entry;
import com.ashish.jiraissuetracker.objects.login.LoginObjectResponse;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.utils.DebugUtils;
import com.ashish.jiraissuetracker.utils.TimeUtils;
import com.ashish.jiraissuetracker.utils.TimeUtilsGMT;
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
public class UserProfileListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AppConstants {

    Context context;
    LoginObjectResponse profileDetails;
    List<Entry> activityEntries;
    String userName;

    boolean isMoreAllowed;

    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public UserProfileListAdapter(Context context, LoginObjectResponse mData, String userName) {
        if (context == null) {
            return;
        }

        this.context = context;
        this.userName = userName;
        this.profileDetails = mData;

        requestBuilder = GlideRequestManager.getRequestBuilder(context);
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
        } else if (viewType == TYPE_RECYCLER_VIEW_LOADING_ADDITIONAL_CONTENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.user_profile_loading_activity_stream_list_item_layout, parent, false);
            LoadingHolder holder = new LoadingHolder(view);
            return holder;
        } else if (viewType == TYPE_RECYCLER_VIEW_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.user_profile_list_header_layout, parent, false);
            HeaderHolder holder = new HeaderHolder(view);
            return holder;
        }
        return null;
    }

    public void addData(List<Entry> entry, boolean isMoreAllowed) {
        if (this.activityEntries == null) {
            this.activityEntries = new ArrayList<>();
        }
        this.activityEntries.addAll(entry);
        this.isMoreAllowed = isMoreAllowed;

        notifyDataSetChanged();
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {

        TextView displayName, email, userName;
        CircleImageView imageView;

        public HeaderHolder(View v) {
            super(v);
            displayName = (TextView) v.findViewById(R.id.display_name);
            email = (TextView) v.findViewById(R.id.email);
            userName = (TextView) v.findViewById(R.id.username);
            imageView = (CircleImageView) v.findViewById(R.id.userimagewebview);
        }
    }

    protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                DebugUtils.log("URL clicked", span.getURL());

                String checkUserProfile = ZPreferences.getBaseUrl(context) + "secure/ViewProfile.jspa?name=";
                String checkIssueName = ZPreferences.getBaseUrl(context) + "browse/";
                if (span.getURL().startsWith(checkUserProfile)) {
                    String userName = span.getURL().substring(checkUserProfile.length());
                    DebugUtils.log("username : " + userName);
                    if (userName.equals(UserProfileListAdapter.this.userName)) {
                        ((UserProfileActivity) context).scrollRecyclerViewToPosition0();
                    } else {
                        ((BaseActivity) context).openUserProfileActivity(userName);
                    }
                } else if (span.getURL().startsWith(checkIssueName)) {
                    String issueKey = span.getURL().substring(checkIssueName.length());
                    DebugUtils.log("issueKey : " + issueKey);
                    ((BaseActivity) context).openIssueDetailActivity(issueKey, issueKey);
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
            makeLinkClickable(strBuilder, span);
        }
        text.setText(strBuilder);
        text.setMovementMethod(LinkMovementMethod.getInstance());
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderCom, int position) {
        position = holderCom.getAdapterPosition();
        if (getItemViewType(position) == TYPE_RECYCLER_VIEW_NORMAL) {
            IssueHolder holder = (IssueHolder) holderCom;

            Entry entry = activityEntries.get(position - 1);
            setTextViewHTML(holder.text, entry.getTitle().getContent(), entry.getAuthor().getUsrUsername());

            holder.time.setText(TimeUtilsGMT.getPostTimeGMTActivityStream(entry.getUpdated()));

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
        } else if (getItemViewType(position) == TYPE_RECYCLER_VIEW_HEADER) {
            HeaderHolder holder = (HeaderHolder) holderCom;

            try {
                String url = profileDetails.getAvatarUrls().get48x48();
//                url = url.substring(0, url.length() - 2);
//                url = url + "250";

                Uri uri = Uri.parse(url);
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .load(uri)
                        .into(holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.userName.setText(profileDetails.getName());
            holder.displayName.setText(profileDetails.getDisplayName());
            holder.email.setText(profileDetails.getEmailAddress());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return AppConstants.TYPE_RECYCLER_VIEW_HEADER;
        } else {
            if (activityEntries == null) {
                return AppConstants.TYPE_RECYCLER_VIEW_LOADING_ADDITIONAL_CONTENT;
            } else {
                if (position - 1 < activityEntries.size()) {
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
        if (profileDetails == null) {
            return 0;
        } else {
            size = 1;
            if (activityEntries == null) {
                size = size + 1;
            } else {
                if (isMoreAllowed) {
                    size = size + activityEntries.size() + 1;
                } else {
                    size = size + activityEntries.size();
                }
            }
        }
        return size;
    }
}
