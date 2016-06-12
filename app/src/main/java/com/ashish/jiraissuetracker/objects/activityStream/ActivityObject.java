
package com.ashish.jiraissuetracker.objects.activityStream;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityObject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("summary")
    @Expose
    private Summary summary;
    @SerializedName("link")
    @Expose
    private Link link;
    @SerializedName("activity:object-type")
    @Expose
    private String activityObjectType;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The title
     */
    public Title getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The summary
     */
    public Summary getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The link
     */
    public Link getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(Link link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The activityObjectType
     */
    public String getActivityObjectType() {
        return activityObjectType;
    }

    /**
     * 
     * @param activityObjectType
     *     The activity:object-type
     */
    public void setActivityObjectType(String activityObjectType) {
        this.activityObjectType = activityObjectType;
    }

}
