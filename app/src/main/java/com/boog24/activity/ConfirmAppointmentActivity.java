package com.boog24.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.adapter.EmployeeServicesAdapter;
import com.boog24.databinding.ActivityConfirmAppointmentBinding;
import com.boog24.extra.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ConfirmAppointmentActivity extends BaseActivity {

    ActivityConfirmAppointmentBinding binding;
    JSONArray jsonArray;
    MainActivity mainActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_appointment);
        binding.setActivity(this);

        mainActivity=new MainActivity();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

        binding.tvId.setText(getIntent().getStringExtra("bookingId"));
        binding.tvName.setText(getIntent().getStringExtra("salonName"));
        binding.tvAddress.setText(getIntent().getStringExtra("address"));
        binding.tvDate.setText(getIntent().getStringExtra("date"));
        binding.tvAmount.setText("€"+getIntent().getStringExtra("amount"));

        try {
            jsonArray=new JSONArray(MyApplication.getInstance().getSession().getData());


        EmployeeServicesAdapter headerAdapter = new EmployeeServicesAdapter(this,jsonArray,"confirm");
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerview.setAdapter(headerAdapter);
        binding.recyclerview.setNestedScrollingEnabled(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyApplication.getInstance().getSession().setData("");

                // This method will be executed once the timer is over
                // Start your app main activity
            }
        }, 8000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ConfirmAppointmentActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
