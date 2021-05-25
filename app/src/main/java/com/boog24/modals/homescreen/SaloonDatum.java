package com.boog24.modals.homescreen;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaloonDatum {

@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("user_email")
@Expose
private String userEmail;
@SerializedName("saloon_name")
@Expose
private String saloonName;
@SerializedName("saloon_title")
@Expose
private String saloonTitle;
@SerializedName("saloon_address")
@Expose
private String saloonAddress;
@SerializedName("saloon__info")
@Expose
private String saloonInfo;
@SerializedName("wishlist_status")
@Expose
private String wishlistStatus;
@SerializedName("saloon_image")
@Expose
private String saloonImage;
@SerializedName("saloon_ratings")
@Expose
private String saloonRatings;
@SerializedName("saloon_ratings_users")
@Expose
private String saloonRatingsUsers;
@SerializedName("reviews")
@Expose
private List<Review> reviews = null;

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getUserEmail() {
return userEmail;
}

public void setUserEmail(String userEmail) {
this.userEmail = userEmail;
}

public String getSaloonName() {
return saloonName;
}

public void setSaloonName(String saloonName) {
this.saloonName = saloonName;
}

public String getSaloonTitle() {
return saloonTitle;
}

public void setSaloonTitle(String saloonTitle) {
this.saloonTitle = saloonTitle;
}

public String getSaloonAddress() {
return saloonAddress;
}

public void setSaloonAddress(String saloonAddress) {
this.saloonAddress = saloonAddress;
}

public String getSaloonInfo() {
return saloonInfo;
}

public void setSaloonInfo(String saloonInfo) {
this.saloonInfo = saloonInfo;
}

public String getWishlistStatus() {
return wishlistStatus;
}

public void setWishlistStatus(String wishlistStatus) {
this.wishlistStatus = wishlistStatus;
}

public String getSaloonImage() {
return saloonImage;
}

public void setSaloonImage(String saloonImage) {
this.saloonImage = saloonImage;
}

public String getSaloonRatings() {
return saloonRatings;
}

public void setSaloonRatings(String saloonRatings) {
this.saloonRatings = saloonRatings;
}

public String getSaloonRatingsUsers() {
return saloonRatingsUsers;
}

public void setSaloonRatingsUsers(String saloonRatingsUsers) {
this.saloonRatingsUsers = saloonRatingsUsers;
}

public List<Review> getReviews() {
return reviews;
}

public void setReviews(List<Review> reviews) {
this.reviews = reviews;
}

}