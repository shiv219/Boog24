package com.boog24.modals.getServiceTimeSlots;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("employees")
@Expose
private List<Employee> employees = null;
@SerializedName("time_slots")
@Expose

private List<TimeSlot> timeSlots = null;

    @SerializedName("most_free_employee_id")
    @Expose
    private String most_free_employee_id;

    public String getMost_free_employee_id() {
        return most_free_employee_id;
    }

    public void setMost_free_employee_id(String most_free_employee_id) {
        this.most_free_employee_id = most_free_employee_id;
    }

    public List<Employee> getEmployees() {
return employees;
}

public void setEmployees(List<Employee> employees) {
this.employees = employees;
}

public List<TimeSlot> getTimeSlots() {
return timeSlots;
}

public void setTimeSlots(List<TimeSlot> timeSlots) {
this.timeSlots = timeSlots;
}

}