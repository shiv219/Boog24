package com.boog24.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.adapter.CustomerTypeAdapter;
import com.boog24.adapter.EmployeeServicesAdapter;
import com.boog24.adapter.TimeSlotAdapter;
import com.boog24.databinding.ActivityChooseEmployessBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.CommonOffset;
import com.boog24.modals.employeeList.EmployeeDatum;
import com.boog24.modals.employeeList.Result;
import com.boog24.modals.getSaloonDetail.SalonService;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.presenter.GetEmployeesListPresenter;
import com.boog24.presenter.GetTimeSlotsPresenter;
import com.boog24.view.ICommonView;
import com.boog24.view.IGetEmployeeView;
import com.boog24.view.IGetTimeSlotsView;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ChooseEmployeesActivity extends BaseActivity implements IGetEmployeeView, ICommonView , IGetTimeSlotsView {
    String salonId;
    String from;
    ActivityChooseEmployessBinding binding;
    SalonDetailActivity salonDetailActivity;
//    GetEmployeesListPresenter getEmployeesListPresenter;
    String employeeId="";
    GetCommonDataPresenter getCommonDataPresenter;
    GetTimeSlotsPresenter getTimeSlotsPresenter;
    JSONArray serviceIds =new JSONArray();
    String currentDateandTime;
    private Boolean spinnerTouched = false;
    private String more_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_employess);
        binding.setActivity(this);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        getEmployeesListPresenter=new GetEmployeesListPresenter();
//        getEmployeesListPresenter.setView(this);

        getCommonDataPresenter=new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);

        getTimeSlotsPresenter = new GetTimeSlotsPresenter();
        getTimeSlotsPresenter.setView(this);

        setUserType();
        salonId = getIntent().getStringExtra("salonId");
        from = getIntent().getStringExtra("from");

//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        binding.etDate.setText(currentDateandTime);


        try {
            JSONArray selectedArray = new JSONArray(MyApplication.getInstance().getSession().getData());

            for (int i=0;i<selectedArray.length();i++){
                serviceIds.put(Integer.parseInt(selectedArray.getJSONObject(i).getString("service_id")));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("TAG", "onCreate:SERVICE IDS "+serviceIds.toString() );

        if (NetworkAlertUtility.isConnectingToInternet(ChooseEmployeesActivity.this)) {
//            getEmployeesListPresenter.getEmployee(ChooseEmployeesActivity.this,salonId);
            getTimeSlotsPresenter.getTimeSlots(ChooseEmployeesActivity.this,employeeId,currentDateandTime,salonId,serviceIds.toString());
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(ChooseEmployeesActivity.this);
        }



        binding.ProSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                EmployeeDatum mSelected = (EmployeeDatum) parent.getItemAtPosition(pos);
                Log.i("Id:", mSelected.getEmployeename());
                if (!mSelected.getId().equalsIgnoreCase("-1")) {
                    if (spinnerTouched) {
                        employeeId = mSelected.getId();
                        binding.etEmployee.setText(mSelected.getEmployeename());
                        if (NetworkAlertUtility.isConnectingToInternet(ChooseEmployeesActivity.this)) {
                            getTimeSlotsPresenter.getTimeSlots(ChooseEmployeesActivity.this, employeeId, currentDateandTime, salonId, serviceIds.toString());
                        } else {
                            NetworkAlertUtility.showNetworkFailureAlert(ChooseEmployeesActivity.this);
                        }
                        spinnerTouched = false;
                    }else{
                        employeeId = mSelected.getId();
                        binding.etEmployee.setText(mSelected.getEmployeename());
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<SalonService> object = (ArrayList<SalonService>) args.getSerializable("ARRAYLIST");


//        for (int i=0;i<object.size();i++){
//            if (object.size()>0 && object.get(i).getServices()!=null && object.get(i).getServices().size()>0) {
//                for (int j = 0; j < object.get(i).getServices().size(); j++) {
//
//                    if (!object.get(i).getServices().get(j).getSelected()) {
//                        if (object.get(i).getServices().size() == 1) {
//                            object.remove(object.get(i));
//                            break;
//                        }
//                        else {
//                            object.remove(object.get(i).getServices().get(j));
//                        }
//                    }
//                }
//            }
//        }

//        if (object.size()>0) {


//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JSONArray jsonArray =new JSONArray();
        try {
            if (!MyApplication.getInstance().getSession().getData().isEmpty()) {
                jsonArray = new JSONArray(MyApplication.getInstance().getSession().getData());

                serviceIds = new JSONArray();
                float amount=0;
                for (int i=0;i<jsonArray.length();i++){
                    serviceIds.put(Integer.parseInt(jsonArray.getJSONObject(i).getString("service_id")));
                    amount= amount+Float.parseFloat(jsonArray.getJSONObject(i).getString("price"));
                }


                binding.tvTotalAmount.setText("€"+String.valueOf(amount));

                EmployeeServicesAdapter headerAdapter = new EmployeeServicesAdapter(this, jsonArray, "employee");
                binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                binding.recyclerview.setAdapter(headerAdapter);
                binding.recyclerview.setNestedScrollingEnabled(true);
            }else{
                EmployeeServicesAdapter headerAdapter = new EmployeeServicesAdapter(this, jsonArray, "employee");
                binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                binding.recyclerview.setAdapter(headerAdapter);
                binding.recyclerview.setNestedScrollingEnabled(true);

                binding.tvTotalAmount.setText("0");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUserType() {
        // Spinner Drop down elements
        List<EmployeeDatum> userList3 = new ArrayList<>();
        EmployeeDatum user3 = new EmployeeDatum(getResources().getString(R.string.select_employee),"-1");
        userList3.add(user3);

        ArrayAdapter<EmployeeDatum> dataAdapter3 = new ArrayAdapter<EmployeeDatum>(this, android.R.layout.simple_spinner_item, userList3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.ProSpinner.setAdapter(dataAdapter3);


    }


    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {

            case R.id.etEmployee:
                spinnerTouched = true;
                binding.ProSpinner.performClick();
                break;

            case R.id.etDate:
                showDateTimePicker();
                break;

            case R.id.tvContinue:
                if (binding.etDate.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(ChooseEmployeesActivity.this, R.string.pls_selectdatetime, Toast.LENGTH_SHORT).show();
                }else if (fromTime.equalsIgnoreCase("")){
                    Toast.makeText(ChooseEmployeesActivity.this, R.string.pls_select_time, Toast.LENGTH_SHORT).show();
                }
                else if (MyApplication.getInstance().getSession().getData().isEmpty()){
                    Toast.makeText(ChooseEmployeesActivity.this, R.string.pls_select_at_least_one_service, Toast.LENGTH_SHORT).show();
                }

                    else {

                    Intent intent = new Intent(this, ContactDetailActivity.class);
                    intent.putExtra("employeeId", employeeId);
                    intent.putExtra("date", binding.etDate.getText().toString());
                    intent.putExtra("salonId", salonId);
                    intent.putExtra("fromTime", fromTime);
                    intent.putExtra("toTime", toTime);
                    intent.putExtra("array", getIntent().getStringExtra("array"));
                    intent.putExtra("from", from);
                    intent.putExtra("more_id", more_id);
                    startActivity(intent);


//                    if (NetworkAlertUtility.isConnectingToInternet(ChooseEmployeesActivity.this)) {
//                        getCommonDataPresenter.checkSalonAvailability(ChooseEmployeesActivity.this,employeeId,binding.etDate.getText().toString(),salonId);
//                    } else {
//                        NetworkAlertUtility.showNetworkFailureAlert(ChooseEmployeesActivity.this);
//                    }
//
                }
                break;
        }

    }
    Calendar date;

    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        DatePickerDialog dialog=   new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);

                binding.etDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" +year );
                currentDateandTime = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;

                if (NetworkAlertUtility.isConnectingToInternet(ChooseEmployeesActivity.this)) {
                                getTimeSlotsPresenter.getTimeSlots(ChooseEmployeesActivity.this,employeeId,currentDateandTime,salonId,serviceIds.toString());
                            } else {
                                NetworkAlertUtility.showNetworkFailureAlert(ChooseEmployeesActivity.this);
                            }
//             TimePickerDialog timePickerDialog=   new TimePickerDialog(ChooseEmployeesActivity.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        Calendar calendar = Calendar.getInstance();
//                            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                            date.set(Calendar.MINUTE, minute);
//                        if(date.getTimeInMillis()>=calendar.getTimeInMillis()) {
//
//                            Log.e("TAG", "The choosen one " + date.getTime());
//                            binding.etDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth + "    " + hourOfDay + ":" + minute);
//                            currentDateandTime = dayOfMonth+"-"+monthOfYear+"-"+year+" "+hourOfDay+":"+minute;
//
//                            if (NetworkAlertUtility.isConnectingToInternet(ChooseEmployeesActivity.this)) {
//                                getTimeSlotsPresenter.getTimeSlots(ChooseEmployeesActivity.this,employeeId,currentDateandTime,salonId,serviceIds.toString());
//                            } else {
//                                NetworkAlertUtility.showNetworkFailureAlert(ChooseEmployeesActivity.this);
//                            }
//
//                        }else{
//                            Toast.makeText(ChooseEmployeesActivity.this, getResources().getString(R.string.invalid_time), Toast.LENGTH_LONG).show();
//
//                        }
                    }
//                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false);
//             timePickerDialog.show();

//            }
        },currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }


    @Override
    public void onGetDetail(Result response) {
        if (response.getStatus()==200){


//        FOR API


        }else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }else{
            binding.etEmployee.setText(getResources().getString(R.string.next_available_employee));
            ArrayList<EmployeeDatum> customerTypeDatumArrayList = new ArrayList<>();
            customerTypeDatumArrayList.add(new EmployeeDatum(getResources().getString(R.string.next_available_employee), "-1"));

            if (response.getData().size()>0) {
                for (int i = 0; i < response.getData().size(); i++) {
                    customerTypeDatumArrayList.add(new EmployeeDatum(response.getData().get(i).getEmployeename(), response.getData().get(i).getId()));
                }
            }
            CustomerTypeAdapter statusAdpter = new CustomerTypeAdapter(ChooseEmployeesActivity.this, R.layout.spinner_item_row,
                    customerTypeDatumArrayList, ChooseEmployeesActivity.this);

//        CUSTOMER_TYPE_ID = response.getResult().getCustomerTypeData().get(0).getCustomerTypeId();
            binding.ProSpinner.setAdapter(statusAdpter);

        }
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onGetDetail(CommonOffset response) {

        if (response.getStatus()==200){
            Intent intent = new Intent(this, ContactDetailActivity.class);
                    intent.putExtra("employeeId", employeeId);
                    intent.putExtra("date", binding.etDate.getText().toString());
                    intent.putExtra("salonId", salonId);
                    intent.putExtra("array", getIntent().getStringExtra("array"));
                    startActivity(intent);
        }else if (response.getStatus()==400){
            windowPopUp(this,response.getMessage());
        }else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
    }

    String fromTime="",toTime="";

    @Override
    public void ongetTimeSLots(com.boog24.modals.getServiceTimeSlots.Result response) {
        if (response.getStatus()==200){

            System.out.println("more   xhxhxhxh  "+response.getData().getMost_free_employee_id().toString());

            more_id = response.getData().getMost_free_employee_id().toString();
            ArrayList<EmployeeDatum> customerTypeDatumArrayList = new ArrayList<>();
//            customerTypeDatumArrayList.add(new EmployeeDatum(getResources().getString(R.string.select_employee), "-1"));

            if (response.getData().getEmployees().size()>0) {
                for (int i = 0; i < response.getData().getEmployees().size(); i++) {
                    customerTypeDatumArrayList.add(new EmployeeDatum(response.getData().getEmployees().get(i).getEmployeName(), response.getData().getEmployees().get(i).getEmployeId()));
                }
            }
            CustomerTypeAdapter statusAdpter = new CustomerTypeAdapter(ChooseEmployeesActivity.this, R.layout.spinner_item_row,
                    customerTypeDatumArrayList, ChooseEmployeesActivity.this);

//        CUSTOMER_TYPE_ID = response.getResult().getCustomerTypeData().get(0).getCustomerTypeId();
            binding.ProSpinner.setAdapter(statusAdpter);
            binding.ProSpinner.setSelected(false);
            int pos = 0;

            for (int i = 0; i < response.getData().getEmployees().size(); i++) {
                if (response.getData().getEmployees().get(i).getIs_selected().equalsIgnoreCase("1")){
                    pos = i;
                }
            }
                binding.ProSpinner.setSelection(pos,false);
            binding.recyclerviewTimeSLot.setVisibility(View.VISIBLE);
            //For Time SLots
            TimeSlotAdapter headerAdapter = new TimeSlotAdapter(ChooseEmployeesActivity.this,response.getData().getTimeSlots(), new TimeSlotAdapter.ItemsClick() {
                @Override
                public void click(int postion) {
//                    timeSLotid = response.getData().getTimeSlots().get(postion).getEmployeeTimingId();
                    fromTime=response.getData().getTimeSlots().get(postion).getFromTime();
                    toTime=response.getData().getTimeSlots().get(postion).getToTime();
                }
            });
            binding.recyclerviewTimeSLot.setLayoutManager(new GridLayoutManager(ChooseEmployeesActivity.this, 3));
            binding.recyclerviewTimeSLot.setAdapter(headerAdapter);
            binding.recyclerviewTimeSLot.setNestedScrollingEnabled(true);



        }else if (response.getStatus()==400){

            binding.recyclerviewTimeSLot.setVisibility(View.GONE);
            binding.etEmployee.setText(getResources().getString(R.string.next_available_employee));

            ArrayList<EmployeeDatum> customerTypeDatumArrayList = new ArrayList<>();
            customerTypeDatumArrayList.add(new EmployeeDatum(getResources().getString(R.string.next_available_employee), "-1"));

            if (response.getData().getEmployees().size()>0) {
                for (int i = 0; i < response.getData().getEmployees().size(); i++) {
                    customerTypeDatumArrayList.add(new EmployeeDatum(response.getData().getEmployees().get(i).getEmployeName(), response.getData().getEmployees().get(i).getEmployeId()));
                }
            }
            CustomerTypeAdapter statusAdpter = new CustomerTypeAdapter(ChooseEmployeesActivity.this, R.layout.spinner_item_row,
                    customerTypeDatumArrayList, ChooseEmployeesActivity.this);

//        CUSTOMER_TYPE_ID = response.getResult().getCustomerTypeData().get(0).getCustomerTypeId();
            binding.ProSpinner.setAdapter(statusAdpter);
//            windowPopUp(this,response.getMessage());
        }else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

    }
}


