package com.boog24.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.boog24.R;
import com.boog24.adapter.NotificationAdapter;
import com.boog24.adapter.SalonListingAdapter;
import com.boog24.custom.Constants;
import com.boog24.databinding.ActivityNotificationsBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.getNotification.Result;
import com.boog24.presenter.GetNotificationListPresenter;
import com.boog24.view.IGetNotificationListView;
import com.pixplicity.easyprefs.library.Prefs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class NotificationActivity extends BaseActivity implements IGetNotificationListView {

    ActivityNotificationsBinding binding;
    GetNotificationListPresenter getNotificationListPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notifications);
        binding.setActivity(this);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        getNotificationListPresenter = new GetNotificationListPresenter();
        getNotificationListPresenter.setView(this);

        if (NetworkAlertUtility.isConnectingToInternet(NotificationActivity.this)) {
            getNotificationListPresenter.getNotifications(NotificationActivity.this);
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(NotificationActivity.this);
        }


        Prefs.putString(Constants.SharedPreferences_notification_status, "");

    }


    @Override
    public void onGetDetail(Result response) {
        if (response.getStatus()==200){
            NotificationAdapter salonListingAdapter = new NotificationAdapter(this,response.getNotificationList());
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerView.setAdapter(salonListingAdapter);
        }
        else if (response.getStatus() == 406) {
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
}
