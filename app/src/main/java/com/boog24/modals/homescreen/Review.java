package com.boog24.modals.homescreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

@SerializedName("customer_review_id")
@Expose
private String customerReviewId;
@SerializedName("user_name")
@Expose
private String userName;
@SerializedName("star")
@Expose
private String star;
@SerializedName("description")
@Expose
private String description;
@SerializedName("created_on")
@Expose
private String createdOn;
@SerializedName("profileimage")
@Expose
private String profileimage;

public String getCustomerReviewId() {
return customerReviewId;
}

public void setCustomerReviewId(String customerReviewId) {
this.customerReviewId = customerReviewId;
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public String getStar() {
return star;
}

public void setStar(String star) {
this.star = star;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getCreatedOn() {
return createdOn;
}

public void setCreatedOn(String createdOn) {
this.createdOn = createdOn;
}

public String getProfileimage() {
return profileimage;
}

public void setProfileimage(String profileimage) {
this.profileimage = profileimage;
}

}