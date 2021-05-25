package com.boog24.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("mobileno")
    @Expose
    private String mobileno;
    @SerializedName("latitude")
    @Expose
    private String latitude;

    public String getUser_notification_status() {
        return user_notification_status;
    }

    public void setUser_notification_status(String user_notification_status) {
        this.user_notification_status = user_notification_status;
    }

    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("emailid")
    @Expose
    private String emailid;
    @SerializedName("profileimage")
    @Expose
    private String profileimage;
    @SerializedName("user_notification_status")
    @Expose
    private String user_notification_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

}