
package com.ashish.jiraissuetracker.objects.issues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Issuelink {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("type")
    @Expose
    private Type type;
    @SerializedName("outwardIssue")
    @Expose
    private OutwardIssue outwardIssue;

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
     *     The self
     */
    public String getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The type
     */
    public Type getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The outwardIssue
     */
    public OutwardIssue getOutwardIssue() {
        return outwardIssue;
    }

    /**
     * 
     * @param outwardIssue
     *     The outwardIssue
     */
    public void setOutwardIssue(OutwardIssue outwardIssue) {
        this.outwardIssue = outwardIssue;
    }

}
