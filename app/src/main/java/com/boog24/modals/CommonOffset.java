
package com.boog24.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommonOffset {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("contact_us_email")
    @Expose
    private String contact_us_email;

    public String getContact_us_email() {
        return contact_us_email;
    }

    public void setContact_us_email(String contact_us_email) {
        this.contact_us_email = contact_us_email;
    }

    public String getContact_us_phone_number() {
        return contact_us_phone_number;
    }

    public void setContact_us_phone_number(String contact_us_phone_number) {
        this.contact_us_phone_number = contact_us_phone_number;
    }

    @SerializedName("contact_us_phone_number")
    @Expose
    private String contact_us_phone_number;
    @SerializedName("user_detail")
    @Expose
    private UserDetail userDetail;
    @SerializedName("login_key")
    @Expose
    private String loginKey;

    public CmsPage getCmsPage() {
        return cmsPage;
    }

    public void setCmsPage(CmsPage cmsPage) {
        this.cmsPage = cmsPage;
    }

    @SerializedName("cms_page")
    @Expose
    private CmsPage cmsPage;

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    @SerializedName("profileimage")
    @Expose
    private String profileimage;
    @SerializedName("category_data")
    @Expose
    private List<CategoryDatum> categoryData = null;
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }
    public List<CategoryDatum> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(List<CategoryDatum> categoryData) {
        this.categoryData = categoryData;
    }
}

