
package com.ashish.jiraissuetracker.objects.issueHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("fieldtype")
    @Expose
    private String fieldtype;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("fromString")
    @Expose
    private String fromString;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("toString")
    @Expose
    private String toString;

    /**
     * @return The field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field The field
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return The fieldtype
     */
    public String getFieldtype() {
        return fieldtype;
    }

    /**
     * @param fieldtype The fieldtype
     */
    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    /**
     * @return The from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from The from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return The fromString
     */
    public String getFromString() {
        return fromString;
    }

    /**
     * @param fromString The fromString
     */
    public void setFromString(String fromString) {
        this.fromString = fromString;
    }

    /**
     * @return The to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to The to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return The toString
     */
    public String getToString() {
        return toString;
    }

    /**
     * @param toString The toString
     */
    public void setToString(String toString) {
        this.toString = toString;
    }

}
