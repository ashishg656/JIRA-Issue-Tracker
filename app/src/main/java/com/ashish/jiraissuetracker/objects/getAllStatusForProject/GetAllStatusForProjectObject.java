
package com.ashish.jiraissuetracker.objects.getAllStatusForProject;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllStatusForProjectObject {

    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subtask")
    @Expose
    private Boolean subtask;
    @SerializedName("statuses")
    @Expose
    private List<Status> statuses = new ArrayList<Status>();

    /**
     * @return The self
     */
    public String getSelf() {
        return self;
    }

    /**
     * @param self The self
     */
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The subtask
     */
    public Boolean getSubtask() {
        return subtask;
    }

    /**
     * @param subtask The subtask
     */
    public void setSubtask(Boolean subtask) {
        this.subtask = subtask;
    }

    /**
     * @return The statuses
     */
    public List<Status> getStatuses() {
        return statuses;
    }

    /**
     * @param statuses The statuses
     */
    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

}
