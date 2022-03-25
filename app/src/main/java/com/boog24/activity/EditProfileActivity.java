package com.boog24.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.databinding.ActivityEditProfileBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.updateProfile.Result;
import com.boog24.presenter.UpdateProfilePresenter;
import com.boog24.util.countryCode.CountryCodeDialog;
import com.boog24.view.IUpdateProfileView;
import com.pixplicity.easyprefs.library.Prefs;

public class EditProfileActivity extends BaseActivity implements IUpdateProfileView {

    ActivityEditProfileBinding binding;
    UpdateProfilePresenter updateProfilePresenter;
    private int countryPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        binding.setActivity(this);

        updateProfilePresenter=new UpdateProfilePresenter();
        updateProfilePresenter.setView(this);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gender = "";
                if(binding.tvGender.getText().toString().equals(getString(R.string.female))){
                    gender = "F";
                }else {
                    gender = "M";
                }
                if (NetworkAlertUtility.isConnectingToInternet(EditProfileActivity.this)) {
                    updateProfilePresenter.userSignin(EditProfileActivity.this, binding.etFirstName.getText().toString().trim(), binding.etLastName.getText().toString(), binding.etPhone.getText().toString(), gender, binding.etCountry.getText().toString());
                } else {
                    NetworkAlertUtility.showNetworkFailureAlert(EditProfileActivity.this);
                }
            }
        });


        binding.etFirstName.setText(Prefs.getString(Constants.SharedPreferences_FirstName,""));
        binding.etLastName.setText(Prefs.getString(Constants.SharedPreferences_LastName,""));
        binding.etEmail.setText(Prefs.getString(Constants.SharedPreferences_Email,""));
        binding.etPhone.setText(Prefs.getString(Constants.SharedPreferences_Mobile, ""));
        binding.etCountry.setText(Prefs.getString(Constants.SharedPreferences_country_code, ""));
        if(Prefs.getString(Constants.SharedPreferences_Gender, "").equals("F")){
            binding.tvGender.setText(getResources().getString(R.string.female));
        }else{
            binding.tvGender.setText(getResources().getString(R.string.male));
        }

        binding.tvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.select_gender_dialog, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                TextView tvMale = dialogView.findViewById(R.id.tvMale);
                TextView tvFemale = dialogView.findViewById(R.id.tvFemale);
                tvMale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        binding.tvGender.setText(getResources().getString(R.string.male));
                    }
                });

                tvFemale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        binding.tvGender.setText(getResources().getString(R.string.female));
                    }
                });
                alertDialog.show();
            }
        });
        binding.etCountry.setOnClickListener(view -> {
            new CountryCodeDialog(countryPosition, (position, countryCode) -> {
                countryPosition = position;
                binding.etCountry.setText(countryCode);

            }).show(getSupportFragmentManager(), CountryCodeDialog.TAG);
        });

    }

    @Override
    public void onGetDetail(Result response) {

        if (response.getStatus()==200){

            Prefs.putString(Constants.SharedPreferences_userId, response.getUserId());
            Prefs.putString(Constants.SharedPreferences_FirstName, response.getUserData().getFirstname());
            Prefs.putString(Constants.SharedPreferences_Gender, response.getUserData().getGender());
            Prefs.putString(Constants.SharedPreferences_LastName, response.getUserData().getLastname());
            Prefs.putString(Constants.SharedPreferences_Mobile, response.getUserData().getMobileno());
            Prefs.putString(Constants.SharedPreferences_Email, response.getUserData().getEmailid());
            Prefs.putString(Constants.SharedPreferences_full_name, response.getUserData().getFullName());
            Prefs.putString(Constants.SharedPreferences_profileimage, response.getUserData().getProfileimage());

            final Dialog myDialog = new Dialog(this);
            myDialog.setContentView(R.layout.alert_label_editor);
            //  myDialog.setCanceledOnTouchOutside(false);
            TextView popText = myDialog.findViewById(R.id.popText);
            popText.setText(response.getMessage());
            Button btnCancel = myDialog.findViewById(R.id.btnCancel);
            Button btnLogin = myDialog.findViewById(R.id.btnLogin);
            btnLogin.setVisibility(View.GONE);
            btnCancel.setText("OK");
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDialog.dismiss();
                    finish();
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
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
