package com.boog24.modals.getCitiesList;

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
@SerializedName("city_data")
@Expose
private List<CityDatum> cityData = null;
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

public List<CityDatum> getCityData() {
return cityData;
}

public void setCityData(List<CityDatum> cityData) {
this.cityData = cityData;
}

public List<CategoryDatum> getCategoryData() {
return categoryData;
}

public void setCategoryData(List<CategoryDatum> categoryData) {
this.categoryData = categoryData;
}

}