
package com.ashish.jiraissuetracker.objects.getIssueTransitions;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class To implements Parcelable {

    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("iconUrl")
    @Expose
    private String iconUrl;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("statusCategory")
    @Expose
    private StatusCategory statusCategory;

    protected To(Parcel in) {
        self = in.readString();
        description = in.readString();
        iconUrl = in.readString();
        name = in.readString();
        id = in.readString();
        statusCategory = in.readParcelable(StatusCategory.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(self);
        dest.writeString(description);
        dest.writeString(iconUrl);
        dest.writeString(name);
        dest.writeString(id);
        dest.writeParcelable(statusCategory, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<To> CREATOR = new Creator<To>() {
        @Override
        public To createFromParcel(Parcel in) {
            return new To(in);
        }

        @Override
        public To[] newArray(int size) {
            return new To[size];
        }
    };

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
     * @return The iconUrl
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * @param iconUrl The iconUrl
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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
     * @return The statusCategory
     */
    public StatusCategory getStatusCategory() {
        return statusCategory;
    }

    /**
     * @param statusCategory The statusCategory
     */
    public void setStatusCategory(StatusCategory statusCategory) {
        this.statusCategory = statusCategory;
    }

}
