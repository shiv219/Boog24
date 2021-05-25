package com.boog24.modals.getWishlist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private List<Datum> data = null;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public Boolean getError() {
return error;
}

public void setError(Boolean error) {
this.error = error;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}

}