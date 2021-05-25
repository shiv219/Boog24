package com.boog24.modals.myBookings;

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
@SerializedName("data_list")
@Expose
private List<DataList> dataList = null;

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

public List<DataList> getDataList() {
return dataList;
}

public void setDataList(List<DataList> dataList) {
this.dataList = dataList;
}

}