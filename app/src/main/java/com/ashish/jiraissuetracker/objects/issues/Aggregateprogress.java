
package com.ashish.jiraissuetracker.objects.issues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aggregateprogress {

    @SerializedName("progress")
    @Expose
    private Long progress;
    @SerializedName("total")
    @Expose
    private Long total;

    /**
     * @return The progress
     */
    public Long getProgress() {
        return progress;
    }

    /**
     * @param progress The progress
     */
    public void setProgress(Long progress) {
        this.progress = progress;
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

}
