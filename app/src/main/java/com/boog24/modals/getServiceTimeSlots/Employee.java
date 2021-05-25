package com.boog24.modals.getServiceTimeSlots;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {

@SerializedName("employe_id")
@Expose
private String employeId;
@SerializedName("employe_name")
@Expose
private String employeName;
    @SerializedName("is_selected")
    @Expose
    private String is_selected;

    public String getIs_selected() {
        return is_selected;
    }

    public void setIs_selected(String is_selected) {
        this.is_selected = is_selected;
    }

    public String getEmployeId() {
return employeId;
}

public void setEmployeId(String employeId) {
this.employeId = employeId;
}

public String getEmployeName() {
return employeName;
}

public void setEmployeName(String employeName) {
this.employeName = employeName;
}

}