
package com.ashish.jiraissuetracker.objects.activityStream;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityStreamObjectNonArray {

    @SerializedName("feed")
    @Expose
    private FeedNonArray feed;

    /**
     *
     * @return
     *     The feed
     */
    public FeedNonArray getFeed() {
        return feed;
    }

    /**
     *
     * @param feed
     *     The feed
     */
    public void setFeed(FeedNonArray feed) {
        this.feed = feed;
    }

}
