
package com.ashish.jiraissuetracker.objects.issues;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchListingResponseObject {

    @SerializedName("expand")
    @Expose
    private String expand;
    @SerializedName("startAt")
    @Expose
    private Long startAt;
    @SerializedName("maxResults")
    @Expose
    private Long maxResults;
    @SerializedName("total")
    @Expose
    private Long total;
    @SerializedName("issues")
    @Expose
    private List<Issue> issues = new ArrayList<Issue>();

    /**
     * @return The expand
     */
    public String getExpand() {
        return expand;
    }

    /**
     * @param expand The expand
     */
    public void setExpand(String expand) {
        this.expand = expand;
    }

    /**
     * @return The startAt
     */
    public Long getStartAt() {
        return startAt;
    }

    /**
     * @param startAt The startAt
     */
    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    /**
     * @return The maxResults
     */
    public Long getMaxResults() {
        return maxResults;
    }

    /**
     * @param maxResults The maxResults
     */
    public void setMaxResults(Long maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * @return The total
     */
    public Long getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * @return The issues
     */
    public List<Issue> getIssues() {
        return issues;
    }

    /**
     * @param issues The issues
     */
    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

}
