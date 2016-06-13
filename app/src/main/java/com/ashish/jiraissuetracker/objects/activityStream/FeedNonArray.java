
package com.ashish.jiraissuetracker.objects.activityStream;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FeedNonArray {

    @SerializedName("entry")
    @Expose
    private Entry entry;

    /**
     * @return The entry
     */
    public Entry getEntry() {
        return entry;
    }

    /**
     * @param entry The entry
     */
    public void setEntry(Entry entry) {
        this.entry = entry;
    }

}
