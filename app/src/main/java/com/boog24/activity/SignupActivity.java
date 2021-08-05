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
import com.boog24.databinding.ActivitySignupBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.CommonOffset;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.util.DialogUtils;
import com.boog24.util.countryCode.CountryCodeDialog;
import com.boog24.view.ICommonView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends BaseActivity implements ICommonView {

    ActivitySignupBinding binding;
    GetCommonDataPresenter getCommonDataPresenter;
    private String gender_value = "M";
    Boolean dialogShown = false;
    private int countryPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        binding.setActivity(this);

        getCommonDataPresenter = new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);

        binding.etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!dialogShown) {
                DialogUtils.showMszDialog(SignupActivity.this, getResources().getString(R.string.pls_enter_validate_password));
                dialogShown = true;
            }
        });


        binding.btnLogin.setOnClickListener(view -> {
            if (binding.etFirstName.getText().toString().equalsIgnoreCase("")) {

                binding.etFirstName.setError(getResources().getString(R.string.pls_enter_firstname));
                binding.etFirstName.requestFocus();
            } else if (binding.etLastName.getText().toString().equalsIgnoreCase("")) {

                binding.etLastName.setError(getResources().getString(R.string.pls_enter_lastname));
                binding.etLastName.requestFocus();
            } else if (binding.etEmail.getText().toString().equalsIgnoreCase("")) {

                binding.etEmail.setError(getResources().getString(R.string.pls_enter_email));
                binding.etEmail.requestFocus();
            } else if (binding.etCountry.getText().toString().equalsIgnoreCase("")) {

                binding.etCountry.setError(getResources().getString(R.string.pls_select_country));
                binding.etCountry.requestFocus();
            } else if (binding.etPhone.getText().toString().equalsIgnoreCase("")) {

                binding.etPhone.setError(getResources().getString(R.string.pls_enter_phone));
                binding.etPhone.requestFocus();
            }/* else if (binding.etPassword.getText().toString().equalsIgnoreCase("")) {
                binding.etPassword.setError(getResources().getString(R.string.pls_enter_password));
                binding.etPassword.requestFocus();
            }*/ else if (!isValidPassword(binding.etPassword.getText().toString().trim())) {
                binding.etPassword.setError(getResources().getString(R.string.pls_enter_validate_password));
                binding.etPassword.requestFocus();
            } else if (binding.etconfirmPassword.getText().toString().equalsIgnoreCase("")) {

                binding.etconfirmPassword.setError(getResources().getString(R.string.pls_enter_confrmpass));
                binding.etconfirmPassword.requestFocus();
            } /*else if (!isValidPassword(binding.etconfirmPassword.getText().toString().trim())) {
                binding.etconfirmPassword.setError(getResources().getString(R.string.pls_enter_validate_password));
                binding.etconfirmPassword.requestFocus();
            }*/ else if (!binding.etPassword.getText().toString().equalsIgnoreCase(binding.etconfirmPassword.getText().toString())) {
                binding.etconfirmPassword.setError(getResources().getString(R.string.pls_enter_correctpassword));
                binding.etconfirmPassword.requestFocus();
            } else if (!binding.checkbox.isChecked()) {
                windowPopUp(SignupActivity.this, getResources().getString(R.string.pls_accept_terms));
            } else {
                if (NetworkAlertUtility.isConnectingToInternet(SignupActivity.this)) {
                    getCommonDataPresenter.userSignup(SignupActivity.this, binding.etEmail.getText().toString().trim(), binding.etFirstName.getText().toString().trim(), binding.etLastName.getText().toString().trim(), binding.etPhone.getText().toString().trim(), binding.etPassword.getText().toString(), binding.etconfirmPassword.getText().toString(), gender_value, binding.etCountry.getText().toString());
                } else {
                    NetworkAlertUtility.showNetworkFailureAlert(SignupActivity.this);
                }
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        binding.etGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.select_gender_dialog, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                TextView tvMale = dialogView.findViewById(R.id.tvMale);
                TextView tvFemale = dialogView.findViewById(R.id.tvFemale);
                tvMale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender_value = "M";
                        alertDialog.dismiss();
                        binding.etGender.setText(getResources().getString(R.string.male));
                    }
                });

                tvFemale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gender_value = "F";
                        alertDialog.dismiss();
                        binding.etGender.setText(getResources().getString(R.string.female));
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

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
//        final String PASSWORD_PATTERN = "^(?=.{7,})(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+!=<>{}()|;:.,~?-]).*$";
        final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[@#$%^&+!=<>{}()|;:.,~?-]).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    @Override
    public void onGetDetail(CommonOffset response) {
        if (response.getStatus() == 200) {
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
        } else {
            windowPopUp(this, response.getMessage());
        }
    }

    @Override
    public Context getContext() {
        return null;
    }

}

