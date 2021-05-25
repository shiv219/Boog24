package com.boog24.modals.getServiceTimeSlots;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlot {

@SerializedName("employee_timing_id")
@Expose
private String employeeTimingId;
@SerializedName("from_time")
@Expose
private String fromTime;
@SerializedName("to_time")
@Expose
private String toTime;
@SerializedName("is_available")
@Expose
private String isAvailable;

public String getEmployeeTimingId() {
return employeeTimingId;
}

public void setEmployeeTimingId(String employeeTimingId) {
this.employeeTimingId = employeeTimingId;
}

public String getFromTime() {
return fromTime;
}

public void setFromTime(String fromTime) {
this.fromTime = fromTime;
}

public String getToTime() {
return toTime;
}

public void setToTime(String toTime) {
this.toTime = toTime;
}

public String getIsAvailable() {
return isAvailable;
}

public void setIsAvailable(String isAvailable) {
this.isAvailable = isAvailable;
}

}