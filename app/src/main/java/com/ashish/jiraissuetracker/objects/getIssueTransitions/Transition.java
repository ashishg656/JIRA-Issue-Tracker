
package com.ashish.jiraissuetracker.objects.getIssueTransitions;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transition implements Parcelable {

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

    protected Transition(Parcel in) {
        id = in.readString();
        name = in.readString();
        hasScreen = in.readByte() != 0;
        to = in.readParcelable(To.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeByte((byte) (hasScreen ? 1 : 0));
        dest.writeParcelable(to, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Transition> CREATOR = new Creator<Transition>() {
        @Override
        public Transition createFromParcel(Parcel in) {
            return new Transition(in);
        }

        @Override
        public Transition[] newArray(int size) {
            return new Transition[size];
        }
    };

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


}
