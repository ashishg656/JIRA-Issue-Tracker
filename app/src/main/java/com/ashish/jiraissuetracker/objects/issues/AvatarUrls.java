
package com.ashish.jiraissuetracker.objects.issues;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvatarUrls implements Parcelable {

    @SerializedName("48x48")
    @Expose
    private String _48x48;
    @SerializedName("24x24")
    @Expose
    private String _24x24;
    @SerializedName("16x16")
    @Expose
    private String _16x16;
    @SerializedName("32x32")
    @Expose
    private String _32x32;

    protected AvatarUrls(Parcel in) {
        _48x48 = in.readString();
        _24x24 = in.readString();
        _16x16 = in.readString();
        _32x32 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_48x48);
        dest.writeString(_24x24);
        dest.writeString(_16x16);
        dest.writeString(_32x32);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AvatarUrls> CREATOR = new Creator<AvatarUrls>() {
        @Override
        public AvatarUrls createFromParcel(Parcel in) {
            return new AvatarUrls(in);
        }

        @Override
        public AvatarUrls[] newArray(int size) {
            return new AvatarUrls[size];
        }
    };

    /**
     * @return The _48x48
     */
    public String get48x48() {
        return _48x48;
    }

    /**
     * @param _48x48 The 48x48
     */
    public void set48x48(String _48x48) {
        this._48x48 = _48x48;
    }

    /**
     * @return The _24x24
     */
    public String get24x24() {
        return _24x24;
    }

    /**
     * @param _24x24 The 24x24
     */
    public void set24x24(String _24x24) {
        this._24x24 = _24x24;
    }

    /**
     * @return The _16x16
     */
    public String get16x16() {
        return _16x16;
    }

    /**
     * @param _16x16 The 16x16
     */
    public void set16x16(String _16x16) {
        this._16x16 = _16x16;
    }

    /**
     * @return The _32x32
     */
    public String get32x32() {
        return _32x32;
    }

    /**
     * @param _32x32 The 32x32
     */
    public void set32x32(String _32x32) {
        this._32x32 = _32x32;
    }

}
