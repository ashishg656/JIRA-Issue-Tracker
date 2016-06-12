
package com.ashish.jiraissuetracker.objects.activityStream;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entry {

    @SerializedName("xmlns:activity")
    @Expose
    private String xmlnsActivity;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("published")
    @Expose
    private String published;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("link")
    @Expose
    private List<Link> link = new ArrayList<Link>();
    @SerializedName("generator")
    @Expose
    private Generator generator;
    @SerializedName("atlassian:application")
    @Expose
    private String atlassianApplication;
    @SerializedName("activity:verb")
    @Expose
    private List<String> activityVerb = new ArrayList<String>();
    @SerializedName("activity:object")
    @Expose
    private ActivityObject activityObject;
    @SerializedName("atlassian:timezone-offset")
    @Expose
    private String atlassianTimezoneOffset;

    /**
     * 
     * @return
     *     The xmlnsActivity
     */
    public String getXmlnsActivity() {
        return xmlnsActivity;
    }

    /**
     * 
     * @param xmlnsActivity
     *     The xmlns:activity
     */
    public void setXmlnsActivity(String xmlnsActivity) {
        this.xmlnsActivity = xmlnsActivity;
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
     *     The author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The published
     */
    public String getPublished() {
        return published;
    }

    /**
     * 
     * @param published
     *     The published
     */
    public void setPublished(String published) {
        this.published = published;
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
     *     The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The link
     */
    public List<Link> getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(List<Link> link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The generator
     */
    public Generator getGenerator() {
        return generator;
    }

    /**
     * 
     * @param generator
     *     The generator
     */
    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    /**
     * 
     * @return
     *     The atlassianApplication
     */
    public String getAtlassianApplication() {
        return atlassianApplication;
    }

    /**
     * 
     * @param atlassianApplication
     *     The atlassian:application
     */
    public void setAtlassianApplication(String atlassianApplication) {
        this.atlassianApplication = atlassianApplication;
    }

    /**
     * 
     * @return
     *     The activityVerb
     */
    public List<String> getActivityVerb() {
        return activityVerb;
    }

    /**
     * 
     * @param activityVerb
     *     The activity:verb
     */
    public void setActivityVerb(List<String> activityVerb) {
        this.activityVerb = activityVerb;
    }

    /**
     * 
     * @return
     *     The activityObject
     */
    public ActivityObject getActivityObject() {
        return activityObject;
    }

    /**
     * 
     * @param activityObject
     *     The activity:object
     */
    public void setActivityObject(ActivityObject activityObject) {
        this.activityObject = activityObject;
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

}
