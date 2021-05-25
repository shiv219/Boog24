package com.boog24.modals.addWishlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("salon_id")
@Expose
private String salonId;
@SerializedName("salon_name")
@Expose
private String salonName;
@SerializedName("shop_address")
@Expose
private String shopAddress;
@SerializedName("salon_address")
@Expose
private String salonAddress;
@SerializedName("saloon_ratings")
@Expose
private String saloonRatings;
@SerializedName("wishlist_status")
@Expose
private String wishlistStatus;
@SerializedName("salon_image")
@Expose
private String salonImage;

public String getSalonId() {
return salonId;
}

public void setSalonId(String salonId) {
this.salonId = salonId;
}

public String getSalonName() {
return salonName;
}

public void setSalonName(String salonName) {
this.salonName = salonName;
}

public String getShopAddress() {
return shopAddress;
}

public void setShopAddress(String shopAddress) {
this.shopAddress = shopAddress;
}

public String getSalonAddress() {
return salonAddress;
}

public void setSalonAddress(String salonAddress) {
this.salonAddress = salonAddress;
}

public String getSaloonRatings() {
return saloonRatings;
}

public void setSaloonRatings(String saloonRatings) {
this.saloonRatings = saloonRatings;
}

public String getWishlistStatus() {
return wishlistStatus;
}

public void setWishlistStatus(String wishlistStatus) {
this.wishlistStatus = wishlistStatus;
}

public String getSalonImage() {
return salonImage;
}

public void setSalonImage(String salonImage) {
this.salonImage = salonImage;
}

}