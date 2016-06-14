
package com.ashish.jiraissuetracker.objects.projectListing;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.ashish.jiraissuetracker.objects.issues.AvatarUrls;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectListingObject implements Parcelable {

    @SerializedName("expand")
    @Expose
    private String expand;
    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("lead")
    @Expose
    private Lead lead;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatarUrls")
    @Expose
    private AvatarUrls avatarUrls;
    @SerializedName("projectKeys")
    @Expose
    private List<String> projectKeys = new ArrayList<String>();
    @SerializedName("projectTypeKey")
    @Expose
    private String projectTypeKey;

    protected ProjectListingObject(Parcel in) {
        expand = in.readString();
        self = in.readString();
        id = in.readString();
        key = in.readString();
        description = in.readString();
        lead = in.readParcelable(Lead.class.getClassLoader());
        name = in.readString();
        avatarUrls = in.readParcelable(AvatarUrls.class.getClassLoader());
        projectKeys = in.createStringArrayList();
        projectTypeKey = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expand);
        dest.writeString(self);
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(description);
        dest.writeParcelable(lead, flags);
        dest.writeString(name);
        dest.writeParcelable(avatarUrls, flags);
        dest.writeStringList(projectKeys);
        dest.writeString(projectTypeKey);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectListingObject> CREATOR = new Creator<ProjectListingObject>() {
        @Override
        public ProjectListingObject createFromParcel(Parcel in) {
            return new ProjectListingObject(in);
        }

        @Override
        public ProjectListingObject[] newArray(int size) {
            return new ProjectListingObject[size];
        }
    };

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
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The lead
     */
    public Lead getLead() {
        return lead;
    }

    /**
     * @param lead The lead
     */
    public void setLead(Lead lead) {
        this.lead = lead;
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
     * @return The avatarUrls
     */
    public AvatarUrls getAvatarUrls() {
        return avatarUrls;
    }

    /**
     * @param avatarUrls The avatarUrls
     */
    public void setAvatarUrls(AvatarUrls avatarUrls) {
        this.avatarUrls = avatarUrls;
    }

    /**
     * @return The projectKeys
     */
    public List<String> getProjectKeys() {
        return projectKeys;
    }

    /**
     * @param projectKeys The projectKeys
     */
    public void setProjectKeys(List<String> projectKeys) {
        this.projectKeys = projectKeys;
    }

    /**
     * @return The projectTypeKey
     */
    public String getProjectTypeKey() {
        return projectTypeKey;
    }

    /**
     * @param projectTypeKey The projectTypeKey
     */
    public void setProjectTypeKey(String projectTypeKey) {
        this.projectTypeKey = projectTypeKey;
    }

}
