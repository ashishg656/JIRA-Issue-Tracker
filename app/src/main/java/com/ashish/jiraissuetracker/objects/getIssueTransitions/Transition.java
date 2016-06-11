
package com.ashish.jiraissuetracker.objects.getIssueTransitions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transition {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("to")
    @Expose
    private To to;
    @SerializedName("hasScreen")
    @Expose
    private Boolean hasScreen;
    @SerializedName("fields")
    @Expose
    private Fields fields;

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
     * @return The to
     */
    public To getTo() {
        return to;
    }

    /**
     * @param to The to
     */
    public void setTo(To to) {
        this.to = to;
    }

    /**
     * @return The hasScreen
     */
    public Boolean getHasScreen() {
        return hasScreen;
    }

    /**
     * @param hasScreen The hasScreen
     */
    public void setHasScreen(Boolean hasScreen) {
        this.hasScreen = hasScreen;
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

}
