package com.boog24.modals.getSaloons;

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
@SerializedName("saloon_data")
@Expose
private List<SaloonDatum> saloonData = null;
@SerializedName("total_saloons")
@Expose
private String totalSaloons;

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

public List<SaloonDatum> getSaloonData() {
return saloonData;
}

public void setSaloonData(List<SaloonDatum> saloonData) {
this.saloonData = saloonData;
}

public String getTotalSaloons() {
return totalSaloons;
}

public void setTotalSaloons(String totalSaloons) {
this.totalSaloons = totalSaloons;
}

}