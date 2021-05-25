package com.boog24.modals.getBookingDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingDetails {

@SerializedName("order_id")
@Expose
private String orderId;
@SerializedName("appointment_date")
@Expose
private String appointmentDate;
@SerializedName("order_status")
@Expose
private String orderStatus;
@SerializedName("booking_id")
@Expose
private String bookingId;
@SerializedName("total_amount")
@Expose
private String totalAmount;
@SerializedName("order_time")
@Expose
private String orderTime;
@SerializedName("salon_id")
@Expose
private String salonId;
@SerializedName("salon_name")
@Expose
private String salonName;

    public String getSalon_address() {
        return salon_address;
    }

    public void setSalon_address(String salon_address) {
        this.salon_address = salon_address;
    }

    @SerializedName("worker_id")
@Expose
private String workerId;
    @SerializedName("salon_address")
    @Expose
    private String salon_address;
@SerializedName("worker_name")
@Expose
private String workerName;
@SerializedName("salon_image")
@Expose
private String salonImage;
@SerializedName("salon_services")
@Expose
private List<SalonService> salonServices = null;

public String getOrderId() {
return orderId;
}

public void setOrderId(String orderId) {
this.orderId = orderId;
}

public String getAppointmentDate() {
return appointmentDate;
}

public void setAppointmentDate(String appointmentDate) {
this.appointmentDate = appointmentDate;
}

public String getOrderStatus() {
return orderStatus;
}

public void setOrderStatus(String orderStatus) {
this.orderStatus = orderStatus;
}

public String getBookingId() {
return bookingId;
}

public void setBookingId(String bookingId) {
this.bookingId = bookingId;
}

public String getTotalAmount() {
return totalAmount;
}

public void setTotalAmount(String totalAmount) {
this.totalAmount = totalAmount;
}

public String getOrderTime() {
return orderTime;
}

public void setOrderTime(String orderTime) {
this.orderTime = orderTime;
}

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

public String getWorkerId() {
return workerId;
}

public void setWorkerId(String workerId) {
this.workerId = workerId;
}

public String getWorkerName() {
return workerName;
}

public void setWorkerName(String workerName) {
this.workerName = workerName;
}

public String getSalonImage() {
return salonImage;
}

public void setSalonImage(String salonImage) {
this.salonImage = salonImage;
}

public List<SalonService> getSalonServices() {
return salonServices;
}

public void setSalonServices(List<SalonService> salonServices) {
this.salonServices = salonServices;
}

}