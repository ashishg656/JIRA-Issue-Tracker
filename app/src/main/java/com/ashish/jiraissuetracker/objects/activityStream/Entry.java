
package com.ashish.jiraissuetracker.objects.activityStream;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entry {

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
    @SerializedName("activity:target")
    @Expose
    private ActivityTarget activityTarget;
    @SerializedName("summary")
    @Expose
    private Title summary;
    @SerializedName("content")
    @Expose
    private Title content;

    public Title getContent() {
        return content;
    }

    public void setContent(Title content) {
        this.content = content;
    }

    public Title getSummary() {
        return summary;
    }

    public void setSummary(Title summary) {
        this.summary = summary;
    }

    public ActivityTarget getActivityTarget() {
        return activityTarget;
    }

    public void setActivityTarget(ActivityTarget activityTarget) {
        this.activityTarget = activityTarget;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
