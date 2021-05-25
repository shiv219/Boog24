package com.boog24.modals.getNotification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationList {

@SerializedName("notification_image")
@Expose
private String notificationImage;
@SerializedName("notification_message ")
@Expose
private String notificationMessage;
@SerializedName("notification_time ")
@Expose
private String notificationTime;
@SerializedName("salon_id")
@Expose
private String salonId;
@SerializedName("order_id")
@Expose
private String orderId;
@SerializedName("action")
@Expose
private String Action;
@SerializedName("user_notification_id")
@Expose
private String userNotificationId;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("order_status")
    @Expose
    private String type;
    public String getNotificationImage() { return notificationImage; }

public void setNotificationImage(String notificationImage) {
this.notificationImage = notificationImage;
}

public String getNotificationMessage() {
return notificationMessage;
}

public void setNotificationMessage(String notificationMessage) {
this.notificationMessage = notificationMessage;
}

public String getNotificationTime() {
return notificationTime;
}

public void setNotificationTime(String notificationTime) {
this.notificationTime = notificationTime;
}

public String getSalonId() {
return salonId;
}

public void setSalonId(String salonId) {
this.salonId = salonId;
}

public String getOrderId() {
return orderId;
}

public void setOrderId(String orderId) { this.orderId = orderId; }

public String getAction() { return Action; }

   public void setAction(String action) { this.Action = action; }

    public String getUserNotificationId() { return userNotificationId; }

    public void setUserNotificationId(String userNotificationId) { this.userNotificationId = userNotificationId; }

}