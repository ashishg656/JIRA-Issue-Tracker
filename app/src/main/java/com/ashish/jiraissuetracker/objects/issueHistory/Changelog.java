
package com.ashish.jiraissuetracker.objects.issueHistory;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Changelog {

    @SerializedName("startAt")
    @Expose
    private Integer startAt;
    @SerializedName("maxResults")
    @Expose
    private Integer maxResults;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("histories")
    @Expose
    private List<History> histories = new ArrayList<History>();

    /**
     * @return The startAt
     */
    public Integer getStartAt() {
        return startAt;
    }

    /**
     * @param startAt The startAt
     */
    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }

    /**
     * @return The maxResults
     */
    public Integer getMaxResults() {
        return maxResults;
    }

    /**
     * @param maxResults The maxResults
     */
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * @return The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return The histories
     */
    public List<History> getHistories() {
        return histories;
    }

    /**
     * @param histories The histories
     */
    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

}
