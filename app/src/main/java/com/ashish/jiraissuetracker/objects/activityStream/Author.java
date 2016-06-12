
package com.ashish.jiraissuetracker.objects.activityStream;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Author {

    @SerializedName("xmlns:usr")
    @Expose
    private String xmlnsUsr;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("link")
    @Expose
    private List<Link> link = new ArrayList<Link>();
    @SerializedName("usr:username")
    @Expose
    private String usrUsername;
    @SerializedName("activity:object-type")
    @Expose
    private String activityObjectType;

    /**
     * 
     * @return
     *     The xmlnsUsr
     */
    public String getXmlnsUsr() {
        return xmlnsUsr;
    }

    /**
     * 
     * @param xmlnsUsr
     *     The xmlns:usr
     */
    public void setXmlnsUsr(String xmlnsUsr) {
        this.xmlnsUsr = xmlnsUsr;
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
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * 
     * @param uri
     *     The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 
     * @return
     *     The link
     */
    public List<Link> getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(List<Link> link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The usrUsername
     */
    public String getUsrUsername() {
        return usrUsername;
    }

    /**
     * 
     * @param usrUsername
     *     The usr:username
     */
    public void setUsrUsername(String usrUsername) {
        this.usrUsername = usrUsername;
    }

    /**
     * 
     * @return
     *     The activityObjectType
     */
    public String getActivityObjectType() {
        return activityObjectType;
    }

    /**
     * 
     * @param activityObjectType
     *     The activity:object-type
     */
    public void setActivityObjectType(String activityObjectType) {
        this.activityObjectType = activityObjectType;
    }

}
