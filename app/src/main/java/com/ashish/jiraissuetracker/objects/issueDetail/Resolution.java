package com.ashish.jiraissuetracker.objects.issueDetail;

/**
 * Created by Ashish on 19/06/16.
 */
public class Resolution {

    String description, name, id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
