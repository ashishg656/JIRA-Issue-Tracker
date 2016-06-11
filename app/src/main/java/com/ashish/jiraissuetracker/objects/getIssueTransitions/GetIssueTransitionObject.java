
package com.ashish.jiraissuetracker.objects.getIssueTransitions;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetIssueTransitionObject {

    @SerializedName("expand")
    @Expose
    private String expand;
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
