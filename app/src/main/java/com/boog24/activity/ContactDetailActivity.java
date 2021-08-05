package com.boog24.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.adapter.EmployeeServicesAdapter;
import com.boog24.custom.Constants;
import com.boog24.databinding.ActivityContactDetailsBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.extra.Utils;
import com.boog24.modals.bookingAppointment.Result;
import com.boog24.modals.bookingAppointment.SalonService;
import com.boog24.presenter.GetBookingDetailPresenter;
import com.boog24.view.IBookingDetailView;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactDetailActivity extends BaseActivity implements IBookingDetailView {
    float amount=0;
    ActivityContactDetailsBinding binding;
    GetBookingDetailPresenter getBookingDetailPresenter;
    public  ArrayList<SalonService> arrayList=new ArrayList<>();
    String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_details);
        binding.setActivity(this);
        System.out.println("Value of booking is  "+Prefs.getString(Constants.SharedPreferences_notification_status, ""));

        getBookingDetailPresenter=new GetBookingDetailPresenter();
        getBookingDetailPresenter.setView(this);

        from = getIntent().getStringExtra("from");

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.etFirstName.setText(Prefs.getString(Constants.SharedPreferences_FirstName,""));
        binding.etLastName.setText(Prefs.getString(Constants.SharedPreferences_LastName,""));
        binding.etEmail.setText(Prefs.getString(Constants.SharedPreferences_Email,""));
        binding.etPhone.setText(Prefs.getString(Constants.SharedPreferences_Mobile,""));

        try {
            jsonArray=new JSONArray(MyApplication.getInstance().getSession().getData());

            setAmount();
            EmployeeServicesAdapter headerAdapter = new EmployeeServicesAdapter(this, jsonArray,"contact");
            binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            binding.recyclerview.setAdapter(headerAdapter);
            binding.recyclerview.setNestedScrollingEnabled(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(from.equals("UPDATE")){
            binding.tvContinue.setText(getResources().getString(R.string.update_booking));
        }else {
            binding.tvContinue.setText(getResources().getString(R.string.book));
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvContinue:

                if (jsonArray.length()==0){
                    Toast.makeText(this, getResources().getString(R.string.pls_select_at_least_one_service), Toast.LENGTH_SHORT).show();

                }else {

                    ArrayList<String> arrayList=new ArrayList<>();
                    for (int i=0;i<jsonArray.length();i++){
                        try {
                            arrayList.add(jsonArray.getJSONObject(i).getString("service_id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if(from.equals("UPDATE")){
                        if (NetworkAlertUtility.isConnectingToInternet(ContactDetailActivity.this)) {
                            getBookingDetailPresenter.getUpdateBooking(ContactDetailActivity.this,binding.etFirstName.getText().toString(),binding.etLastName.getText().toString(),binding.etEmail.getText().toString(),binding.etPhone.getText().toString(),arrayList.toString(),getIntent().getStringExtra("employeeId"),getIntent().getStringExtra("date"),getIntent().getStringExtra("salonId"),String.valueOf(amount),getIntent().getStringExtra("fromTime"),getIntent().getStringExtra("toTime"),
                                    Prefs.getString(Constants.SharedPreferences_order_id, ""),
                                    Prefs.getString(Constants.SharedPreferences_notification_id, ""),
                                    getIntent().getStringExtra("more_id"));
                        } else {
                            NetworkAlertUtility.showNetworkFailureAlert(ContactDetailActivity.this);
                        }

                    }else{
                        if (NetworkAlertUtility.isConnectingToInternet(ContactDetailActivity.this)) {
                            getBookingDetailPresenter.getBooking(ContactDetailActivity.this,binding.etFirstName.getText().toString(),binding.etLastName.getText().toString(),binding.etEmail.getText().toString(),binding.etPhone.getText().toString(),arrayList.toString(),getIntent().getStringExtra("employeeId"),getIntent().getStringExtra("date"),getIntent().getStringExtra("salonId"),String.valueOf(amount),getIntent().getStringExtra("fromTime"),getIntent().getStringExtra("toTime"), getIntent().getStringExtra("more_id"));
                        } else {
                            NetworkAlertUtility.showNetworkFailureAlert(ContactDetailActivity.this);
                        }

                    }

                }
                break;
        }

    }

    public void setAmount(){

        amount=0;

            if (!MyApplication.getInstance().getSession().getData().isEmpty()) {
                try {
                jsonArray = new JSONArray(MyApplication.getInstance().getSession().getData());
            } catch(JSONException e){
                e.printStackTrace();
            }
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        amount = amount + Float.parseFloat(jsonArray.getJSONObject(i).getString("price"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                binding.tvTotalAmount.setText("€" + Utils.getFormatedDouble(String.valueOf(amount)));
            } else {

                binding.tvTotalAmount.setText("0");
            }
        }else{
                jsonArray=new JSONArray();
            binding.tvTotalAmount.setText("0");
        }
    }

    public void back(){
        Intent intent = new Intent(this, SalonDetailActivity.class);
        intent.putExtra("salonId",getIntent().getStringExtra("salonId"));
        startActivity(intent);
        finish();
    }

    @Override
    public void onGetDetail(Result response) {

        if (response.getStatus()==200){
                    Intent intent = new Intent(this, ConfirmAppointmentActivity.class);
//                    arrayList.addAll(response.getData().getSalonServices());
                    JSONArray jsonArray=new JSONArray(response.getData().getSalonServices());
                    intent.putExtra("array",jsonArray.toString());
                    intent.putExtra("bookingId",response.getData().getBookingId());
                    intent.putExtra("salonName",response.getData().getSalonName());
                    intent.putExtra("address",response.getData().getSalon_address());
                    intent.putExtra("date",response.getData().getAppointmentDate());
                    intent.putExtra("amount",response.getData().getTotalAmount());
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) arrayList);
                    startActivity(intent);

        }else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }else{

            final Dialog myDialog = new Dialog(this);
            myDialog.setContentView(R.layout.alert_label_editor);
            //  myDialog.setCanceledOnTouchOutside(false);
            TextView popText = myDialog.findViewById(R.id.popText);
            popText.setText(response.getMessage());
            Button btnCancel = myDialog.findViewById(R.id.btnCancel);
            Button btnLogin = myDialog.findViewById(R.id.btnLogin);
            btnLogin.setVisibility(View.GONE);
            btnCancel.setText(getResources().getString(R.string.ok));
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDialog.dismiss();
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }
    }

    @Override
    public void onGetUpdateDetail(Result response) {
        if (response.getStatus()==200){
            Intent intent = new Intent(this, ConfirmAppointmentActivity.class);
//                    arrayList.addAll(response.getData().getSalonServices());
            JSONArray jsonArray=new JSONArray(response.getData().getSalonServices());
            intent.putExtra("array",jsonArray.toString());
            intent.putExtra("bookingId",response.getData().getBookingId());
            intent.putExtra("salonName",response.getData().getSalonName());
            intent.putExtra("address",response.getData().getSalon_address());
            intent.putExtra("date",response.getData().getAppointmentDate());
            intent.putExtra("amount",response.getData().getTotalAmount());
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST", (Serializable) arrayList);
            startActivity(intent);
            Prefs.putString(Constants.SharedPreferences_notification_status, "");
        }else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }else{

            final Dialog myDialog = new Dialog(this);
            myDialog.setContentView(R.layout.alert_label_editor);
            //  myDialog.setCanceledOnTouchOutside(false);
            TextView popText = myDialog.findViewById(R.id.popText);
            popText.setText(response.getMessage());
            Button btnCancel = myDialog.findViewById(R.id.btnCancel);
            Button btnLogin = myDialog.findViewById(R.id.btnLogin);
            btnLogin.setVisibility(View.GONE);
            btnCancel.setText(getResources().getString(R.string.ok));
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDialog.dismiss();
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }
    }

    @Override
    public Context getContext() {
        return null;
    }
}
