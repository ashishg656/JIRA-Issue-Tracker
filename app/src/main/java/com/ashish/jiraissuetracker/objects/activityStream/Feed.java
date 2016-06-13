
package com.ashish.jiraissuetracker.objects.activityStream;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("entry")
    @Expose
    private List<Entry> entry = new ArrayList<Entry>();

    
    /**
     * @return The entry
     */
    public List<Entry> getEntry() {
        return entry;
    }

    /**
     * @param entry The entry
     */
    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

}
