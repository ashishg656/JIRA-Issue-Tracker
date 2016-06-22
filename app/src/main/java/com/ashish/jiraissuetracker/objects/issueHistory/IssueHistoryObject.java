
package com.ashish.jiraissuetracker.objects.issueHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueHistoryObject {

    @SerializedName("changelog")
    @Expose
    private Changelog changelog;

    /**
     * 
     * @return
     *     The changelog
     */
    public Changelog getChangelog() {
        return changelog;
    }

    /**
     * 
     * @param changelog
     *     The changelog
     */
    public void setChangelog(Changelog changelog) {
        this.changelog = changelog;
    }

}
