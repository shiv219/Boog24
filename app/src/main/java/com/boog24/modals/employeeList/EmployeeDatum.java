package com.boog24.modals.employeeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeDatum {

    public EmployeeDatum(String employeename, String id) {
        this.employeename = employeename;
        this.id = id;
    }

    @SerializedName("employee_name")
    @Expose
    private String employeename;

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("employee_id")
    @Expose
    private String id;

    @SerializedName("salon_id")
    @Expose
    private String salon_id;

    @SerializedName("mobile_no")
    @Expose
    private String mobile_no;

    @SerializedName("employee_image")
    @Expose
    private String employee_image;
}
