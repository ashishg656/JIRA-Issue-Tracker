
package com.ashish.jiraissuetracker.objects.issues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Type {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("inward")
    @Expose
    private String inward;
    @SerializedName("outward")
    @Expose
    private String outward;
    @SerializedName("self")
    @Expose
    private String self;

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
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The inward
     */
    public String getInward() {
        return inward;
    }

    /**
     * 
     * @param inward
     *     The inward
     */
    public void setInward(String inward) {
        this.inward = inward;
    }

    /**
     * 
     * @return
     *     The outward
     */
    public String getOutward() {
        return outward;
    }

    /**
     * 
     * @param outward
     *     The outward
     */
    public void setOutward(String outward) {
        this.outward = outward;
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

}
