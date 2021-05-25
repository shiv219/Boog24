package com.boog24.modals.getBookingDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalonService {

@SerializedName("order_detail_id")
@Expose
private String orderDetailId;
@SerializedName("service_id")
@Expose
private String serviceId;
@SerializedName("service_name")
@Expose
private String serviceName;
@SerializedName("service_price")
@Expose
private String servicePrice;
@SerializedName("hour")
@Expose
private String hour;
@SerializedName("minutes")
@Expose
private String minutes;

public String getOrderDetailId() {
return orderDetailId;
}

public void setOrderDetailId(String orderDetailId) {
this.orderDetailId = orderDetailId;
}

public String getServiceId() {
return serviceId;
}

public void setServiceId(String serviceId) {
this.serviceId = serviceId;
}

public String getServiceName() {
return serviceName;
}

public void setServiceName(String serviceName) {
this.serviceName = serviceName;
}

public String getServicePrice() {
return servicePrice;
}

public void setServicePrice(String servicePrice) {
this.servicePrice = servicePrice;
}

public String getHour() {
return hour;
}

public void setHour(String hour) {
this.hour = hour;
}

public String getMinutes() {
return minutes;
}

public void setMinutes(String minutes) {
this.minutes = minutes;
}

}