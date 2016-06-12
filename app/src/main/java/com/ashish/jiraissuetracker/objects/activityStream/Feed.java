
package com.ashish.jiraissuetracker.objects.activityStream;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("xmlns")
    @Expose
    private String xmlns;
    @SerializedName("xmlns:atlassian")
    @Expose
    private String xmlnsAtlassian;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("link")
    @Expose
    private Link link;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("atlassian:timezone-offset")
    @Expose
    private String atlassianTimezoneOffset;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("entry")
    @Expose
    private List<Entry> entry = new ArrayList<Entry>();

    /**
     * 
     * @return
     *     The xmlns
     */
    public String getXmlns() {
        return xmlns;
    }

    /**
     * 
     * @param xmlns
     *     The xmlns
     */
    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    /**
     * 
     * @return
     *     The xmlnsAtlassian
     */
    public String getXmlnsAtlassian() {
        return xmlnsAtlassian;
    }

    /**
     * 
     * @param xmlnsAtlassian
     *     The xmlns:atlassian
     */
    public void setXmlnsAtlassian(String xmlnsAtlassian) {
        this.xmlnsAtlassian = xmlnsAtlassian;
    }

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
     *     The atlassianTimezoneOffset
     */
    public String getAtlassianTimezoneOffset() {
        return atlassianTimezoneOffset;
    }

    /**
     * 
     * @param atlassianTimezoneOffset
     *     The atlassian:timezone-offset
     */
    public void setAtlassianTimezoneOffset(String atlassianTimezoneOffset) {
        this.atlassianTimezoneOffset = atlassianTimezoneOffset;
    }

    /**
     * 
     * @return
     *     The updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * 
     * @param updated
     *     The updated
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     * 
     * @return
     *     The entry
     */
    public List<Entry> getEntry() {
        return entry;
    }

    /**
     * 
     * @param entry
     *     The entry
     */
    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

}
