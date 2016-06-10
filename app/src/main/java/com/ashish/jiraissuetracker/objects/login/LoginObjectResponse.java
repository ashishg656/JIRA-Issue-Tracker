
package com.ashish.jiraissuetracker.objects.login;

import com.ashish.jiraissuetracker.objects.issues.AvatarUrls;

public class LoginObjectResponse {

    private String self;
    private String key;
    private String name;
    private String emailAddress;
    private AvatarUrls avatarUrls;
    private String displayName;
    private Boolean active;
    private String timeZone;
    private String locale;
    private String expand;

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
     * @return The emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress The emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
     * @return The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName The displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active The active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return The timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @param timeZone The timeZone
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * @return The locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @param locale The locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

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

}
