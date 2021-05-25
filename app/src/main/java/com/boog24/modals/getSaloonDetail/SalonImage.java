package com.boog24.modals.getSaloonDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalonImage {

@SerializedName("salon_image")
@Expose
private String salonImage;

public String getSalonImage() {
return salonImage;
}

public void setSalonImage(String salonImage) {
this.salonImage = salonImage;
}

}