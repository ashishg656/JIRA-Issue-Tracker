package com.ashish.jiraissuetracker.objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashish on 05/06/16.
 */
public class LoginObject {

    String self, key, name, emailAddress, displayName, timeZone, expand;
    boolean active;
    UserAvatars avatarUrls;

    public static LoginObject parseLoginObject(String response) {
        try {
            LoginObject loginObject = new LoginObject();

            JSONObject rootObject = new JSONObject(response);
            loginObject.setSelf(rootObject.getString("self"));
            loginObject.setKey(rootObject.getString("key"));
            loginObject.setName(rootObject.getString("name"));
            loginObject.setEmailAddress(rootObject.getString("emailAddress"));
            loginObject.setDisplayName(rootObject.getString("displayName"));
            loginObject.setTimeZone(rootObject.getString("timeZone"));
            loginObject.setExpand(rootObject.getString("expand"));
            loginObject.setActive(rootObject.getBoolean("active"));

            JSONObject avatarObject = rootObject.getJSONObject("avatarUrls");
            UserAvatars userAvatars = new UserAvatars(avatarObject);
            loginObject.setAvatarUrls(userAvatars);

            return loginObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class UserAvatars {
        String size48, size32, size16, size24;

        public UserAvatars(JSONObject avatarObject) {
            try {
                this.size16 = avatarObject.getString("16x16");
                this.size24 = avatarObject.getString("24x24");
                this.size32 = avatarObject.getString("32x32");
                this.size48 = avatarObject.getString("48x48");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String getSize48() {
            return size48;
        }

        public void setSize48(String size48) {
            this.size48 = size48;
        }

        public String getSize32() {
            return size32;
        }

        public void setSize32(String size32) {
            this.size32 = size32;
        }

        public String getSize16() {
            return size16;
        }

        public void setSize16(String size16) {
            this.size16 = size16;
        }

        public String getSize24() {
            return size24;
        }

        public void setSize24(String size24) {
            this.size24 = size24;
        }
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserAvatars getAvatarUrls() {
        return avatarUrls;
    }

    public void setAvatarUrls(UserAvatars avatarUrls) {
        this.avatarUrls = avatarUrls;
    }
}
