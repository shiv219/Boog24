package com.boog24.modals.getSaloonDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("saloon_data")
@Expose
private SaloonData saloonData;

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

public SaloonData getSaloonData() {
return saloonData;
}

public void setSaloonData(SaloonData saloonData) {
this.saloonData = saloonData;
}

}