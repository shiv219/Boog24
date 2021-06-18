package com.boog24.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.databinding.ActivityLoginBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.CommonOffset;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.view.ICommonView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixplicity.easyprefs.library.Prefs;

public class LoginActivity extends BaseActivity implements ICommonView {

    ActivityLoginBinding binding;
    GetCommonDataPresenter getCommonDataPresenter;
    private String userType = "", DEVICE_ID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);

        getCommonDataPresenter=new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);

        if (Prefs.getString(Constants.SharedPreferences_Langauge,"").equalsIgnoreCase("")){
            Prefs.putString(Constants.SharedPreferences_Langauge,"en");
        }

        if (getIntent().hasExtra("lang")){
            Prefs.putString(Constants.SharedPreferences_Langauge,getIntent().getStringExtra("lang"));
        }

        DEVICE_ID = "Android-" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);



        binding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etEmail.getText().toString().equalsIgnoreCase("")){

                    binding.etEmail.setError(getResources().getString(R.string.pls_enter_email));
                    binding.etEmail.requestFocus();
                }else if (binding.etPassword.getText().toString().equalsIgnoreCase("")){
                    binding.etPassword.setError(getResources().getString(R.string.pls_enter_password));
                    binding.etPassword.requestFocus();
                }else{
                    if (NetworkAlertUtility.isConnectingToInternet(LoginActivity.this)) {
                        getCommonDataPresenter.userSignin(LoginActivity.this,DEVICE_ID, binding.etEmail.getText().toString().trim(),binding.etPassword.getText().toString());
                    } else {
                        NetworkAlertUtility.showNetworkFailureAlert(LoginActivity.this);
                    }
                }

            }
        });
    }





    @Override
    protected void onResume() {
        super.onResume();

        if (Prefs.getString(Constants.SharedPreferences_Langauge,"").equalsIgnoreCase("")){
            Prefs.putString(Constants.SharedPreferences_Langauge,"en");
        }


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String  token = instanceIdResult.getToken();
                Log.e("TAG", "onSuccess: "+token );
                Prefs.putString(Constants.SharedPreferences_FCMID,token);
            }
        });

    }

    @Override
    public void onGetDetail(CommonOffset response) {

        if (response.getStatus()==200){

            Prefs.putString(Constants.SharedPreferences_loginKey, response.getLoginKey());
            Prefs.putString(Constants.SharedPreferences_userId, response.getUserId());
            Prefs.putString(Constants.SharedPreferences_FirstName, response.getUserDetail().getFirstname());
            Prefs.putString(Constants.SharedPreferences_Gender, response.getUserDetail().getGender());
            Prefs.putString(Constants.SharedPreferences_LastName, response.getUserDetail().getLastname());
            Prefs.putString(Constants.SharedPreferences_Mobile, response.getUserDetail().getMobileno());
            Prefs.putString(Constants.SharedPreferences_Email, response.getUserDetail().getEmailid());
            Prefs.putString(Constants.SharedPreferences_full_name, response.getUserDetail().getFullName());
            Prefs.putString(Constants.SharedPreferences_latitude, response.getUserDetail().getLatitude());
            Prefs.putString(Constants.SharedPreferences_longitude, response.getUserDetail().getLongitude());
            Prefs.putString(Constants.SharedPreferences_profileimage, response.getUserDetail().getProfileimage());
            Prefs.putString(Constants.SharedPreferences_country_code, response.getUserDetail().getCountryCode());

            Prefs.putString(Constants.SharedPreferences_notification_status, response.getUserDetail().getUser_notification_status());

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else{
            windowPopUp(this,response.getMessage());
        }
    }

    @Override
    public Context getContext() {
        return null;
    }
}
