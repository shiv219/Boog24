package com.boog24.modals.getSaloonDetail;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalonService implements Serializable {

@SerializedName("service_sub_category")
@Expose
private String serviceSubCategory;
@SerializedName("sub_category_name")
@Expose
private String subCategoryName;
@SerializedName("services")
@Expose
private List<Service> services = null;

public String getServiceSubCategory() {
return serviceSubCategory;
}

public void setServiceSubCategory(String serviceSubCategory) {
this.serviceSubCategory = serviceSubCategory;
}

public String getSubCategoryName() {
return subCategoryName;
}

public void setSubCategoryName(String subCategoryName) {
this.subCategoryName = subCategoryName;
}

public List<Service> getServices() {
return services;
}

public void setServices(List<Service> services) {
this.services = services;
}

}