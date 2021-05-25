package com.boog24.modals.updateProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("user_data")
@Expose
private UserData userData;

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

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public UserData getUserData() {
return userData;
}

public void setUserData(UserData userData) {
this.userData = userData;
}

}