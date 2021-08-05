package com.boog24.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.adapter.EmployeeServicesAdapter;
import com.boog24.databinding.ActivityBookingDetailBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.extra.Utils;
import com.boog24.modals.CommonOffset;
import com.boog24.modals.getSaloonDetail.SalonService;
import com.boog24.presenter.BookingDetailFragmentPresenter;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.view.IBookingView;
import com.boog24.view.ICommonView;
import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class BookingDetailActivity extends BaseActivity implements View.OnClickListener, IBookingView, ICommonView {

    ActivityBookingDetailBinding binding;
    GetCommonDataPresenter getCommonDataPresenter;
    BookingDetailFragmentPresenter getMyBookingsPresenter;
    String type = "";
    String orderId, salonId;
    String from, pre;
    public ArrayList<SalonService> arrayList = new ArrayList<>();
    JSONArray jsonArray = new JSONArray();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_detail);
        binding.setActivity(this);

        getCommonDataPresenter = new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);

        getMyBookingsPresenter=new BookingDetailFragmentPresenter();
        getMyBookingsPresenter.setView(this);

        binding.tvCancel.setOnClickListener(this);
        binding.tvUpdateBooking.setOnClickListener(this);
        binding.tvAdd.setOnClickListener(this);

        type=getIntent().getStringExtra("type");
        orderId=getIntent().getStringExtra("orderId");
        salonId=getIntent().getStringExtra("salonId");
        from = getIntent().getStringExtra("from");
        pre = getIntent().getStringExtra("pre");


        if (type.equalsIgnoreCase("upcoming")){
            binding.tvAdd.setVisibility(View.GONE);
            binding.tvCancel.setVisibility(View.VISIBLE);
        }else if (type.equalsIgnoreCase("cancelled")){
            binding.tvAdd.setVisibility(View.GONE);
            binding.tvCancel.setVisibility(View.GONE);
        }else{
            binding.tvAdd.setVisibility(View.VISIBLE);
            binding.tvCancel.setVisibility(View.GONE);
//            binding.tvAdd.setText("Add feedback");
        }

        if (from.equals("UPDATE")) {
            binding.tvUpdateBooking.setVisibility(View.VISIBLE);
            binding.tvCancel.setVisibility(View.GONE);
        } else {
            binding.tvUpdateBooking.setVisibility(View.GONE);
            binding.tvCancel.setVisibility(View.VISIBLE);
        }
        if (pre != null) {
            binding.tvCancel.setVisibility(View.GONE);

        }


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (NetworkAlertUtility.isConnectingToInternet(this)) {
            getMyBookingsPresenter.getBooking(this,orderId);
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(this);
        }

//
    }


    @Override
    public void onGetDetail(com.boog24.modals.getBookingDetail.Result response) {
        if (response.getStatus()==200){
            Glide.with(this).load(response.getBookingDetails().getSalonImage()).into(binding.image);
            binding.tvName.setText(response.getBookingDetails().getSalonName());
            binding.tvDate.setText(Utils.convertDateTime(response.getBookingDetails().getAppointmentDate()));
            binding.tvAddress.setText(response.getBookingDetails().getSalon_address());
            binding.tvAmount.setText("€" + Utils.getFormatedDouble(response.getBookingDetails().getTotalAmount()));
            binding.tvWorkerName.setText("Worker Name :" + response.getBookingDetails().getWorkerName());
            if (response.getBookingDetails().isFeedbackAdded()) {
                binding.tvAdd.setVisibility(View.GONE);
            }
            EmployeeServicesAdapter headerAdapter = new EmployeeServicesAdapter(this, response.getBookingDetails().getSalonServices(), "booking");
            binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            binding.recyclerview.setAdapter(headerAdapter);
            binding.recyclerview.setNestedScrollingEnabled(true);
            for (int i = 0; i < response.getBookingDetails().getSalonServices().size(); i++) {
                jsonArray = new JSONArray();
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", response.getBookingDetails().getSalonServices().get(i).getServiceName());
                    jsonObject.put("price", response.getBookingDetails().getSalonServices().get(i).getServicePrice());
                    jsonObject.put("hour", response.getBookingDetails().getSalonServices().get(i).getHour());
                    jsonObject.put("minutes", response.getBookingDetails().getSalonServices().get(i).getMinutes());
                    jsonObject.put("service_id", response.getBookingDetails().getSalonServices().get(i).getServiceId());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MyApplication.getInstance().getSession().setData(jsonArray.toString());
            }

        }else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        } else {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Failure")
                    .setMessage(response.getMessage())
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show().setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onGetDetail(CommonOffset response) {
        if (response.getStatus()==200){

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
                    finish();
//                    if (NetworkAlertUtility.isConnectingToInternet(BookingDetailActivity.this)) {
//                        getMyBookingsPresenter.userSignin(BookingDetailActivity.this,getIntent().getStringExtra("type"));
//                    } else {
//                        NetworkAlertUtility.showNetworkFailureAlert(BookingDetailActivity.this);
//                    }
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvAdd:
                final Dialog myDialog = new Dialog(this);
                myDialog.setContentView(R.layout.rating_dialog);
                //  myDialog.setCanceledOnTouchOutside(false);
                RatingBar ratingBar = myDialog.findViewById(R.id.ratingBar);
                ratingBar.setStepSize(1);
                EditText edtFeedback = myDialog.findViewById(R.id.edtFeedback);
                Button btnAdd = myDialog.findViewById(R.id.btnAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ratingBar.getRating() > 0) {
                            myDialog.dismiss();
                            if (NetworkAlertUtility.isConnectingToInternet(BookingDetailActivity.this)) {
                                getCommonDataPresenter.addFeedBack(BookingDetailActivity.this, orderId, salonId, String.valueOf(ratingBar.getRating()), edtFeedback.getText().toString());
                            } else {
                                NetworkAlertUtility.showNetworkFailureAlert(BookingDetailActivity.this);
                            }
                        } else {
                            Toast.makeText(BookingDetailActivity.this, getString(R.string.please_give_rating_first), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
                break;
            case R.id.tvCancel:
                if (NetworkAlertUtility.isConnectingToInternet(BookingDetailActivity.this)) {
                    getCommonDataPresenter.cancelBooking(BookingDetailActivity.this,orderId);
                } else {
                    NetworkAlertUtility.showNetworkFailureAlert(BookingDetailActivity.this);
                }
                break;
            case R.id.tvUpdateBooking :
                if (jsonArray.length() > 0) {
                    Intent intent = new Intent(this, ChooseEmployeesActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) arrayList);
                    intent.putExtra("BUNDLE", args);
                    intent.putExtra("salonId", salonId);
                    intent.putExtra("array", jsonArray.toString());
                    intent.putExtra("from", from);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please Select at least one Service", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
