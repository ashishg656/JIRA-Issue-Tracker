package com.ashish.jiraissuetracker.fragments;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.glideImageRequest.GlideRequestManager;
import com.ashish.jiraissuetracker.objects.issueComments.IssueCommentsObject;
import com.ashish.jiraissuetracker.objects.issueDetail.IssueDetailObject;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.requests.AppUrls;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.TimeUtils;
import com.ashish.jiraissuetracker.utils.VolleyUtils;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 16/06/16.
 */
public class IssueDetailMainFragment extends BaseFragment implements AppRequestListener, View.OnClickListener {

    String issueId;

    ScrollView scrollView;
    String requestUrl;

    TextView projectName, name, description, showMore, key, type, priority, sprint, status, resolution, assignee, reporter, votes, watches, dateCreated, dateUpdated, dateResolved;
    CircleImageView projectImage, reporterImage, assigneeImage;
    ImageView typeImage, priorityImage;

    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public static IssueDetailMainFragment newInstance(Bundle b) {
        IssueDetailMainFragment frg = new IssueDetailMainFragment();
        frg.setArguments(b);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.issue_detail_main_fragment_layout, container, false);

        setProgressAndErrorLayoutVariables();
        scrollView = (ScrollView) rootView.findViewById(R.id.issue_detail_scrollview);

        projectName = (TextView) rootView.findViewById(R.id.issue_detail_projectName);
        name = (TextView) rootView.findViewById(R.id.issue_detail_name);
        description = (TextView) rootView.findViewById(R.id.issue_detail_description);
        showMore = (TextView) rootView.findViewById(R.id.issue_detail_showMore);
        key = (TextView) rootView.findViewById(R.id.issue_detail_key);
        type = (TextView) rootView.findViewById(R.id.issue_detail_type);
        priority = (TextView) rootView.findViewById(R.id.issue_detail_priority);
        sprint = (TextView) rootView.findViewById(R.id.issue_detail_sprint);
        status = (TextView) rootView.findViewById(R.id.issue_detail_status);
        resolution = (TextView) rootView.findViewById(R.id.issue_detail_resolution);
        assignee = (TextView) rootView.findViewById(R.id.issue_detail_assignee);
        reporter = (TextView) rootView.findViewById(R.id.issue_detail_reporter);
        votes = (TextView) rootView.findViewById(R.id.issue_detail_votes);
        watches = (TextView) rootView.findViewById(R.id.issue_detail_watches);
        dateCreated = (TextView) rootView.findViewById(R.id.issue_detail_dateCreated);
        dateUpdated = (TextView) rootView.findViewById(R.id.issue_detail_dateUpdated);
        dateResolved = (TextView) rootView.findViewById(R.id.issue_detail_dateResolved);

        projectImage = (CircleImageView) rootView.findViewById(R.id.issue_detail_projectImage);
        reporterImage = (CircleImageView) rootView.findViewById(R.id.issue_detail_reporterImage);
        assigneeImage = (CircleImageView) rootView.findViewById(R.id.issue_detail_assigneeImage);
        typeImage = (ImageView) rootView.findViewById(R.id.issue_detail_typeImage);
        priorityImage = (ImageView) rootView.findViewById(R.id.issue_detail_priorityIMage);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        issueId = getArguments().getString("issueid");
        requestBuilder = GlideRequestManager.getRequestBuilder(getActivity(), R.drawable.test_user);

        loadData();
    }

    void loadData() {
        requestUrl = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getIssueDetailUrl(issueId);

        AppRequests.makeGetIssueDetailRequest(requestUrl, this, getActivity());
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.ISSUE_DETAIL_REQUEST)) {
            showProgressLayout();
            hideErrorLayout();
            scrollView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.ISSUE_DETAIL_REQUEST)) {
            hideProgressLayout();
            showErrorLayout();
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.ISSUE_DETAIL_REQUEST)) {
            IssueDetailObject object = (IssueDetailObject) VolleyUtils.getResponseObject(response, IssueDetailObject.class);
            setAdapterData(object);
        }
    }

    private void setAdapterData(IssueDetailObject object) {
        hideErrorLayout();
        hideProgressLayout();

        scrollView.setVisibility(View.VISIBLE);

        key.setText(object.getKey());
        name.setText(object.getFields().getSummary());
        description.setText(object.getFields().getDescription());

        description.post(new Runnable() {
            @Override
            public void run() {
                int count = description.getLineCount();
                if (count < 4) {
                    showMore.setVisibility(View.GONE);
                } else {
                    showMore.setVisibility(View.VISIBLE);
                }
            }
        });

        showMore.setOnClickListener(this);

        type.setText(object.getFields().getIssuetype().getName());
        Uri uri = Uri.parse(object.getFields().getIssuetype().getIconUrl());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(typeImage);

        uri = Uri.parse(object.getFields().getPriority().getIconUrl());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(priorityImage);
        priority.setText(object.getFields().getPriority().getName());

        projectName.setText("Project : " + object.getFields().getProject().getName());
        uri = Uri.parse(object.getFields().getProject().getAvatarUrls().get48x48());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(projectImage);

        status.setText(object.getFields().getStatus().getName());
        if (object.getFields().getResolution() != null && object.getFields().getResolution().getName() != null) {
            resolution.setText(object.getFields().getResolution().getName());
            dateResolved.setText(TimeUtils.getPostTimeGMTActivityStream(object.getFields().getResolutiondate()));
        } else {
            resolution.setText("Unresolved");
        }

        watches.setText(object.getFields().getWatches().getWatchCount() + "");
        votes.setText(object.getFields().getVotes().getVotes() + "");

        try {
            assignee.setText(object.getFields().getAssignee().getDisplayName());
            uri = Uri.parse(object.getFields().getAssignee().getAvatarUrls().get48x48());
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE).load(uri)
                    .into(assigneeImage);
        } catch (Exception e) {
            assignee.setText("Unassigned");
        }

        try {
            reporter.setText(object.getFields().getReporter().getDisplayName());
            uri = Uri.parse(object.getFields().getReporter().getAvatarUrls().get48x48());
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE).load(uri)
                    .into(reporterImage);
        } catch (Exception e) {
            reporter.setText("Unassigned");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.issue_detail_showMore:
                if (TextViewCompat.getMaxLines(description) == 4) {
                    description.setMaxLines(Integer.MAX_VALUE);
                    showMore.setText("Show less");
                } else {
                    description.setMaxLines(4);
                    showMore.setText("Show more");
                }
                break;
        }
    }
}
