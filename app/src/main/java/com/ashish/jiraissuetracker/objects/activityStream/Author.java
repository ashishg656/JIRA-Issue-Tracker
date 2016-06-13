
package com.ashish.jiraissuetracker.objects.activityStream;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Author {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("usr:username")
    @Expose
    private String usrUsername;


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
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }


    /**
     * @return The usrUsername
     */
    public String getUsrUsername() {
        return usrUsername;
    }

    /**
     * @param usrUsername The usr:username
     */
    public void setUsrUsername(String usrUsername) {
        this.usrUsername = usrUsername;
    }


}
