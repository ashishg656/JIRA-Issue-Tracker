package com.ashish.jiraissuetracker.objects;

import java.util.List;

/**
 * Created by Ashish on 07/06/16.
 */
public class IssuesObjectList {

    String expand;
    long startAt, maxResults, total;
    List<IssuesObjectSingle> issues;

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public long getStartAt() {
        return startAt;
    }

    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    public long getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(long maxResults) {
        this.maxResults = maxResults;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<IssuesObjectSingle> getIssues() {
        return issues;
    }

    public void setIssues(List<IssuesObjectSingle> issues) {
        this.issues = issues;
    }
}
