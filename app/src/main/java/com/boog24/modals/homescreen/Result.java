package com.boog24.modals.homescreen;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("category_data")
@Expose
private List<CategoryDatum> categoryData = null;
    @SerializedName("banner_data")
    @Expose
    private List<BannerDatum> bannerData = null;
@SerializedName("saloon_data")
@Expose
private List<SaloonDatum> saloonData = null;

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

public List<CategoryDatum> getCategoryData() {
return categoryData;
}

public void setCategoryData(List<CategoryDatum> categoryData) {
this.categoryData = categoryData;
}

    public List<BannerDatum> getBannerData() {
        return bannerData;
    }

    public void setBannerData(List<BannerDatum> bannerData) {
        this.bannerData = bannerData;
    }

public List<SaloonDatum> getSaloonData() {
return saloonData;
}

public void setSaloonData(List<SaloonDatum> saloonData) {
this.saloonData = saloonData;
}

}