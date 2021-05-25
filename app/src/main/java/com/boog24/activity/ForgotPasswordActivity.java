package com.boog24.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.databinding.ActivityForgotPasswordBinding;
import com.boog24.databinding.ActivityLoginBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.CommonOffset;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.view.ICommonView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class ForgotPasswordActivity extends BaseActivity implements ICommonView {

    ActivityForgotPasswordBinding binding;
    GetCommonDataPresenter getCommonDataPresenter;
    private String userType = "", DEVICE_ID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        binding.setActivity(this);

        getCommonDataPresenter = new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);


        DEVICE_ID = "Android-" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etEmail.getText().toString().equalsIgnoreCase("")){
                    binding.etEmail.setError(getResources().getString(R.string.pls_enter_email));
                    binding.etEmail.requestFocus();
                }else{
                    if (NetworkAlertUtility.isConnectingToInternet(ForgotPasswordActivity.this)) {
                        getCommonDataPresenter.forgotPassword(ForgotPasswordActivity.this, binding.etEmail.getText().toString().trim());
                    } else {
                        NetworkAlertUtility.showNetworkFailureAlert(ForgotPasswordActivity.this);
                    }
                }
            }
        });

    }

    @Override
    public void onGetDetail(CommonOffset response) {
        if (response.getStatus()==200) {
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
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }  else{
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
