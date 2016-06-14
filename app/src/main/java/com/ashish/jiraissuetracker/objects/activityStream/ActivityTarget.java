package com.ashish.jiraissuetracker.objects.activityStream;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityTarget {

    @SerializedName("title")
    @Expose
    private Title title;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}