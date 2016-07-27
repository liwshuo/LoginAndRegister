package com.liwshuo.domain;

/**
 * Created by lishuo on 16/7/24.
 */

public class User {
    private String sessionToken;
    private String updatedAt;
    private String objectId;
    private String username;
    private String password;
    private String createdAt;
    private Boolean emailVerified;
    private Boolean mobilePhoneVerified;

    /**
     *
     * @return
     * The sessionToken
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     *
     * @param sessionToken
     * The sessionToken
     */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     *
     * @param objectId
     * The objectId
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The emailVerified
     */
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    /**
     *
     * @param emailVerified
     * The emailVerified
     */
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     *
     * @return
     * The mobilePhoneVerified
     */
    public Boolean getMobilePhoneVerified() {
        return mobilePhoneVerified;
    }

    /**
     *
     * @param mobilePhoneVerified
     * The mobilePhoneVerified
     */
    public void setMobilePhoneVerified(Boolean mobilePhoneVerified) {
        this.mobilePhoneVerified = mobilePhoneVerified;
    }

}
