
package com.ashish.jiraissuetracker.objects.issueDetail;

import java.util.ArrayList;
import java.util.List;

import com.ashish.jiraissuetracker.objects.getIssueTransitions.Transition;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueDetailObject {

    @SerializedName("expand")
    @Expose
    private String expand;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("fields")
    @Expose
    private Fields fields;
    @SerializedName("transitions")
    @Expose
    private List<Transition> transitions = new ArrayList<Transition>();

    /**
     * @return The expand
     */
    public String getExpand() {
        return expand;
    }

    /**
     * @param expand The expand
     */
    public void setExpand(String expand) {
        this.expand = expand;
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
     * @return The key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key The key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return The fields
     */
    public Fields getFields() {
        return fields;
    }

    /**
     * @param fields The fields
     */
    public void setFields(Fields fields) {
        this.fields = fields;
    }

    /**
     * @return The transitions
     */
    public List<Transition> getTransitions() {
        return transitions;
    }

    /**
     * @param transitions The transitions
     */
    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

}
