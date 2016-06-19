package com.ashish.jiraissuetracker.fragments;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.BaseActivity;
import com.ashish.jiraissuetracker.extras.RequestTags;
import com.ashish.jiraissuetracker.glideImageRequest.GlideRequestManager;
import com.ashish.jiraissuetracker.objects.issueDetail.IssueDetailObject;
import com.ashish.jiraissuetracker.preferences.ZPreferences;
import com.ashish.jiraissuetracker.requests.AppRequests;
import com.ashish.jiraissuetracker.requests.AppUrls;
import com.ashish.jiraissuetracker.serverApi.AppRequestListener;
import com.ashish.jiraissuetracker.utils.TimeUtils;
import com.ashish.jiraissuetracker.utils.TimeUtilsGMT;
import com.ashish.jiraissuetracker.utils.VolleyUtils;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 16/06/16.
 */
public class IssueDetailMainFragment extends BaseFragment implements AppRequestListener, View.OnClickListener {

    String issueId;

    ScrollView scrollView;
    String requestUrl;

    TextView projectName, name, description, showMore, key, type, priority, sprint, status, resolution, assignee, reporter,
            votes, watches, dateCreated, dateUpdated, dateResolved, voteButton, watchButton;
    LinearLayout dateResolvedContainer, assigneeContainer, reporterContainer, statusContainer;
    CircleImageView projectImage, reporterImage, assigneeImage;
    ImageView typeImage, priorityImage, statusImage;

    IssueDetailObject mData;

    boolean isVoteRequestRunning, isWatchRequestRunning;

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
        dateResolvedContainer = (LinearLayout) rootView.findViewById(R.id.issue_detail_dateResolvedContainer);
        assigneeContainer = (LinearLayout) rootView.findViewById(R.id.issue_detail_assigneeContainer);
        reporterContainer = (LinearLayout) rootView.findViewById(R.id.issue_detail_reporterContainer);
        watchButton = (TextView) rootView.findViewById(R.id.issue_detail_watch_button);
        voteButton = (TextView) rootView.findViewById(R.id.issue_detail_vote_button);
        statusContainer = (LinearLayout) rootView.findViewById(R.id.issue_detail_statusContainer);

        projectImage = (CircleImageView) rootView.findViewById(R.id.issue_detail_projectImage);
        reporterImage = (CircleImageView) rootView.findViewById(R.id.issue_detail_reporterImage);
        assigneeImage = (CircleImageView) rootView.findViewById(R.id.issue_detail_assigneeImage);
        typeImage = (ImageView) rootView.findViewById(R.id.issue_detail_typeImage);
        priorityImage = (ImageView) rootView.findViewById(R.id.issue_detail_priorityIMage);
        statusImage = (ImageView) rootView.findViewById(R.id.issue_detail_statusImage);

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
        } else if (requestTag.equalsIgnoreCase(RequestTags.ADD_VOTE_TO_ISSUE)) {
            isVoteRequestRunning = true;
            changeIssueVoteStatus(true);
        } else if (requestTag.equalsIgnoreCase(RequestTags.REMOVE_VOTE_FROM_ISSUE)) {
            isVoteRequestRunning = true;
            changeIssueVoteStatus(false);
        } else if (requestTag.equalsIgnoreCase(RequestTags.ADD_WATCH_TO_ISSUE)) {
            isWatchRequestRunning = true;
            changeIssueWatchStatus(true);
        } else if (requestTag.equalsIgnoreCase(RequestTags.REMOVE_WATCH_FROM_ISSUE)) {
            isWatchRequestRunning = true;
            changeIssueWatchStatus(false);
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error) {
        if (requestTag.equalsIgnoreCase(RequestTags.ISSUE_DETAIL_REQUEST)) {
            hideProgressLayout();
            showErrorLayout();
        } else if (requestTag.equalsIgnoreCase(RequestTags.ADD_VOTE_TO_ISSUE)) {
            isVoteRequestRunning = false;
            changeIssueVoteStatus(false);
            ((BaseActivity) getActivity()).makeToast(getActivity().getResources().getString(R.string.failed_to_vote_issue));
        } else if (requestTag.equalsIgnoreCase(RequestTags.REMOVE_VOTE_FROM_ISSUE)) {
            isVoteRequestRunning = false;
            changeIssueVoteStatus(true);
            ((BaseActivity) getActivity()).makeToast(getActivity().getResources().getString(R.string.failed_to_vote_issue));
        } else if (requestTag.equalsIgnoreCase(RequestTags.ADD_WATCH_TO_ISSUE)) {
            isWatchRequestRunning = false;
            changeIssueWatchStatus(false);
            ((BaseActivity) getActivity()).makeToast(getActivity().getResources().getString(R.string.failed_to_watch_issue));
        } else if (requestTag.equalsIgnoreCase(RequestTags.REMOVE_WATCH_FROM_ISSUE)) {
            isWatchRequestRunning = false;
            changeIssueWatchStatus(true);
            ((BaseActivity) getActivity()).makeToast(getActivity().getResources().getString(R.string.failed_to_watch_issue));
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.ISSUE_DETAIL_REQUEST)) {
            mData = (IssueDetailObject) VolleyUtils.getResponseObject(response, IssueDetailObject.class);
            fillScrollViewData();
        } else if (requestTag.equalsIgnoreCase(RequestTags.ADD_VOTE_TO_ISSUE)) {
            isVoteRequestRunning = false;
        } else if (requestTag.equalsIgnoreCase(RequestTags.REMOVE_VOTE_FROM_ISSUE)) {
            isVoteRequestRunning = false;
        } else if (requestTag.equalsIgnoreCase(RequestTags.ADD_WATCH_TO_ISSUE)) {
            isWatchRequestRunning = false;
        } else if (requestTag.equalsIgnoreCase(RequestTags.REMOVE_WATCH_FROM_ISSUE)) {
            isWatchRequestRunning = false;
        }
    }

    private void fillScrollViewData() {
        hideErrorLayout();
        hideProgressLayout();

        projectImage.setOnClickListener(this);
        projectName.setOnClickListener(this);
        assigneeContainer.setOnClickListener(this);
        reporterContainer.setOnClickListener(this);
        voteButton.setOnClickListener(this);
        watchButton.setOnClickListener(this);
        statusContainer.setOnClickListener(this);

        scrollView.setVisibility(View.VISIBLE);

        key.setText(mData.getKey());
        name.setText(mData.getFields().getSummary());
        description.setText(mData.getFields().getDescription());

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

        type.setText(mData.getFields().getIssuetype().getName());
        Uri uri = Uri.parse(mData.getFields().getIssuetype().getIconUrl());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(typeImage);

        uri = Uri.parse(mData.getFields().getPriority().getIconUrl());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(priorityImage);
        priority.setText(mData.getFields().getPriority().getName());

        projectName.setText("Project : " + mData.getFields().getProject().getName());
        uri = Uri.parse(mData.getFields().getProject().getAvatarUrls().get48x48());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(projectImage);

        status.setText(mData.getFields().getStatus().getName());
        statusImage.setVisibility(View.GONE);
//        if (mData.getFields().getStatus().getIconUrl() != null && mData.getFields().getStatus().getIconUrl().length() > 0) {
//            statusImage.setVisibility(View.VISIBLE);
//            uri = Uri.parse(mData.getFields().getStatus().getIconUrl());
//            requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .load(uri)
//                    .into(statusImage);
//        } else {
//            statusImage.setVisibility(View.GONE);
//        }

        if (mData.getFields().getResolution() != null && mData.getFields().getResolution().getName() != null) {
            resolution.setText(mData.getFields().getResolution().getName());
            dateResolved.setText(TimeUtilsGMT.getIssueDetailTime(mData.getFields().getResolutiondate()));
            dateResolvedContainer.setVisibility(View.VISIBLE);
        } else {
            resolution.setText("Unresolved");
            dateResolvedContainer.setVisibility(View.GONE);
        }

        dateCreated.setText(TimeUtilsGMT.getIssueDetailTime(mData.getFields().getCreated()));
        dateUpdated.setText(TimeUtilsGMT.getIssueDetailTime(mData.getFields().getUpdated()));

        try {
            assignee.setText(mData.getFields().getAssignee().getDisplayName());
            uri = Uri.parse(mData.getFields().getAssignee().getAvatarUrls().get48x48());
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE).load(uri)
                    .into(assigneeImage);
        } catch (Exception e) {
            assignee.setText("Unassigned");
        }

        try {
            reporter.setText(mData.getFields().getReporter().getDisplayName());
            uri = Uri.parse(mData.getFields().getReporter().getAvatarUrls().get48x48());
            requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE).load(uri)
                    .into(reporterImage);
        } catch (Exception e) {
            reporter.setText("Unassigned");
        }

        if (mData.getFields().getReporter().getName().equals(ZPreferences.getUserProfileID(getActivity()))) {
            voteButton.setVisibility(View.GONE);
        } else {
            voteButton.setVisibility(View.VISIBLE);
        }

        setDataForWatchAndVoteButtons();
    }

    private void setDataForWatchAndVoteButtons() {
        if (mData.getFields().getVotes().getHasVoted()) {
            voteButton.setText(getActivity().getResources().getString(R.string.issue_detail_unvote_issue));
            voteButton.setTextColor(getActivity().getResources().getColor(R.color.red_color_primary));
        } else {
            voteButton.setText(getActivity().getResources().getString(R.string.issue_detail_vote_issue));
            voteButton.setTextColor(getActivity().getResources().getColor(R.color.green_color_primary));
        }

        if (mData.getFields().getWatches().getIsWatching()) {
            watchButton.setText(getActivity().getResources().getString(R.string.issue_detail_unwatch_issue));
            watchButton.setTextColor(getActivity().getResources().getColor(R.color.red_color_primary));
        } else {
            watchButton.setText(getActivity().getResources().getString(R.string.issue_detail_watch_issue));
            watchButton.setTextColor(getActivity().getResources().getColor(R.color.green_color_primary));
        }

        watches.setText(mData.getFields().getWatches().getWatchCount() + "");
        votes.setText(mData.getFields().getVotes().getVotes() + "");
    }

    void changeIssueVoteStatus(boolean success) {
        int change = 0;
        if (success) {
            change = 1;
        } else {
            change = -1;
        }
        mData.getFields().getVotes().setVotes(mData.getFields().getVotes().getVotes() + change);
        mData.getFields().getVotes().setHasVoted(!mData.getFields().getVotes().getHasVoted());
        setDataForWatchAndVoteButtons();
    }

    void changeIssueWatchStatus(boolean success) {
        int change = 0;
        if (success) {
            change = 1;
        } else {
            change = -1;
        }
        mData.getFields().getWatches().setWatchCount(mData.getFields().getWatches().getWatchCount() + change);
        mData.getFields().getWatches().setIsWatching(!mData.getFields().getWatches().getIsWatching());
        setDataForWatchAndVoteButtons();
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
            case R.id.issue_detail_projectImage:
                ((BaseActivity) getActivity()).openProjectDetailActivity(mData.getFields().getProject().getKey());
                break;
            case R.id.issue_detail_projectName:
                ((BaseActivity) getActivity()).openProjectDetailActivity(mData.getFields().getProject().getKey());
                break;
            case R.id.issue_detail_assigneeContainer:
                ((BaseActivity) getActivity()).openUserProfileActivity(mData.getFields().getAssignee().getName());
                break;
            case R.id.issue_detail_reporterContainer:
                ((BaseActivity) getActivity()).openUserProfileActivity(mData.getFields().getReporter().getName());
                break;
            case R.id.issue_detail_vote_button:
                if (true) {
                    String url = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getVoteIssueUrl(issueId);
                    if (mData.getFields().getVotes().getHasVoted()) {
                        AppRequests.makeRemoveVoteFromIssueRequest(url, this, getActivity());
                    } else {
                        AppRequests.makeAddVoteToIssueRequest(url, this, getActivity());
                    }
                }
                break;
            case R.id.issue_detail_watch_button:
                if (true) {
                    String url = ZPreferences.getBaseUrl(getActivity()) + AppUrls.getWatchIssueUrl(ZPreferences.getUserProfileID(getActivity()), issueId);
                    if (mData.getFields().getWatches().getIsWatching()) {
                        AppRequests.makeRemoveWatchingFromIssueRequest(url, this, getActivity());
                    } else {
                        AppRequests.makeAddWatchingToIssueRequest(url, this, getActivity());
                    }
                }
                break;
            case R.id.issue_detail_statusContainer:
                ((BaseActivity) getActivity()).changeFragmentToChangeIssueStatusFragment(mData.getTransitions(), mData.getFields().getStatus().getName(), issueId);
                break;
        }
    }
}
