
package com.ashish.jiraissuetracker.objects.issueDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Progress {

    @SerializedName("progress")
    @Expose
    private Integer progress;
    @SerializedName("total")
    @Expose
    private Integer total;

    /**
     * 
     * @return
     *     The progress
     */
    public Integer getProgress() {
        return progress;
    }

    /**
     * 
     * @param progress
     *     The progress
     */
    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    /**
     * 
     * @return
     *     The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

}
