package com.ashish.jiraissuetracker.requests;

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
        return AppUrls.SEARCH_ISSUES_URL + "=assignee=" + assignee + "+order+by+updatedDate&startAt=" + startAt + "&maxResults=" + pageSize;
    }

    public static String getIssueTransitions(String issueId) {
        return "rest/api/2/issue/" + issueId + "/transitions?expand=transitions.fields";
    }

    public static String getIssueTransitionsPost(String issueId) {
        return "rest/api/2/issue/" + issueId + "/transitions";
    }

    public static String getActivityStreamUrl(long startAt, int pageSize, String lastUpdated) {
        if (lastUpdated == null) {
            return "activity?startAt=" + startAt + "&maxResults=" + pageSize;
        }
        return "activity?startAt=" + startAt + "&maxResults=" + pageSize + "&streams=update-date+BEFORE+" + lastUpdated;
    }

    public static String getActivityStreamForUserUrl(long startAt, int pageSize, String userName, String lastUpdated) {
        if (lastUpdated == null) {
            return "activity?" + "streams=user+IS+" + userName + "&startAt=" + startAt + "&maxResults=" + pageSize;
        }
        return "activity?" + "streams=user+IS+" + userName + "&startAt=" + startAt + "&maxResults=" + pageSize
                + "&streams=update-date+BEFORE+" + lastUpdated;
    }

    public static String getActivityStreamForProject(long startAt, int pageSize, String projectId, String lastUpdated) {
        if (lastUpdated == null) {
            return "activity?" + "streams=key+IS+" + projectId + "&startAt=" + startAt + "&maxResults=" + pageSize;
        }
        return "activity?" + "streams=key+IS+" + projectId + "&startAt=" + startAt + "&maxResults=" + pageSize
                + "&streams=update-date+BEFORE+" + lastUpdated;
    }

    public static String getUserProfileUrl(String userName) {
        return "rest/api/2/user?username=" + userName;
    }

    public static String getAllProjectsUrl() {
        return "rest/api/2/project?expand=description,lead,url,projectKeys";
    }

    public static String getProjectDetailsUrl(String projectid) {
        return "rest/api/2/project/" + projectid + "?expand=description,lead,url,projectKeys";
    }

    public static String getIssuesForProjectUrl(String projectKey, long startAt, int pageSize) {
        return AppUrls.SEARCH_ISSUES_URL + "=project='" + projectKey + "'+order+by+updatedDate&startAt=" + startAt + "&maxResults=" + pageSize;
    }

    public static String getUserProfileUrl() {
        return LOGIN_URL;
    }
}
