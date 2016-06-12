package com.ashish.jiraissuetracker.extras;

import com.ashish.jiraissuetracker.preferences.ZPreferences;

/**
 * Created by Ashish on 10/05/16.
 */
public class AppUrls {

    // all projects url or login url
    public static final String LOGIN_URL = "rest/api/2/myself";
    public static final String SEARCH_ISSUES_URL = "rest/api/2/search?jql";

    public static String getAllStatusForProject(String projectId) {
        return "rest/api/2/project/" + projectId + "/statuses";
    }

    public static String getSearchIssuesUrl(String assignee, long startAt, int pageSize) {
        return AppUrls.SEARCH_ISSUES_URL + "=assignee=" + assignee + "&startAt=" + startAt + "&maxResults=" + pageSize;
    }

    public static String getIssueTransitions(String issueId) {
        return "rest/api/2/issue/" + issueId + "/transitions?expand=transitions.fields";
    }

    public static String getIssueTransitionsPost(String issueId) {
        return "rest/api/2/issue/" + issueId + "/transitions";
    }

    public static String getActivityStreamUrl(long startAt, int pageSize) {
        return "activity/?startAt=" + startAt + "&maxResults=" + pageSize;
    }
}
