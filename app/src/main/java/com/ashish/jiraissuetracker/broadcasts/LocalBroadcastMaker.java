package com.ashish.jiraissuetracker.broadcasts;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.ashish.jiraissuetracker.extras.LocalBroadcastTypes;

/**
 * Created by Ashish on 11/06/16.
 */
public class LocalBroadcastMaker {

    public static String BROADCAST_INTENT_FILTER_EVENT = "EVENTS";

    public static void makeBroadcastForIssueStatusChange(String issueId, String newStatus, Context context) {
        Intent intent = new Intent(BROADCAST_INTENT_FILTER_EVENT);
        intent.putExtra("type", LocalBroadcastTypes.TYPE_ISSUE_STATUS_CHANGE);
        intent.putExtra("issueid", issueId);
        intent.putExtra("newstatus", newStatus);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
