package com.boog24.modals.homescreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerDatum {

@SerializedName("banner_id")
@Expose
private String bannerId;
@SerializedName("banner_image")
@Expose
private String bannerImage;

public String getBannerId() {
return bannerId;
}

public void setBannerId(String bannerId) {
this.bannerId = bannerId;
}

public String getBannerImage() {
return bannerImage;
}

public void setBannerImage(String bannerImage) {
this.bannerImage = bannerImage;
}

}