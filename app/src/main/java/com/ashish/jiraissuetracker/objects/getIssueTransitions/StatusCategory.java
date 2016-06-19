
package com.ashish.jiraissuetracker.objects.getIssueTransitions;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusCategory implements Parcelable {

    @SerializedName("self")
    @Expose
    private String self;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("colorName")
    @Expose
    private String colorName;
    @SerializedName("name")
    @Expose
    private String name;

    protected StatusCategory(Parcel in) {
        self = in.readString();
        id = in.readInt();
        key = in.readString();
        colorName = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(self);
        dest.writeInt(id);
        dest.writeString(key);
        dest.writeString(colorName);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StatusCategory> CREATOR = new Creator<StatusCategory>() {
        @Override
        public StatusCategory createFromParcel(Parcel in) {
            return new StatusCategory(in);
        }

        @Override
        public StatusCategory[] newArray(int size) {
            return new StatusCategory[size];
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
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
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
     * @return The colorName
     */
    public String getColorName() {
        return colorName;
    }

    /**
     * @param colorName The colorName
     */
    public void setColorName(String colorName) {
        this.colorName = colorName;
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

}
