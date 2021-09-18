package com.boog24.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.databinding.DataBindingUtil;

import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.databinding.ActivitySplaceBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.BiometricAuth;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Locale;

public class SplaceActivity extends BaseActivity {

    private static final int MY_REQUEST_CODE = 1;
    ActivitySplaceBinding binding;
    private static int SPLASH_TIME_OUT = 3000;

    private BiometricPrompt biometricPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changeStatusBarColor(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splace);
        binding.setActivity(this);
        init();
    }

    public void init() {
        biometricPrompt = new BiometricAuth().createBiometricPrompt(this, new AuthSuccess() {
            @Override
            public void onSuccess() {
                moveToMainActivity();
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void OnError() {
                SplaceActivity.this.finish();
            }
        });
        if (Prefs.getString(Constants.SharedPreferences_Langauge, "").equalsIgnoreCase("")) {
            Prefs.putString(Constants.SharedPreferences_Langauge, "gr");
        }

        Log.e("TAG", "onCreate LANGUAGE : " + Prefs.getString(Constants.SharedPreferences_Langauge, ""));

        if (Prefs.getString(Constants.SharedPreferences_Langauge, "").equalsIgnoreCase("en")) {
            setLocale("en");
        } else {
            setLocale("de");
        }


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                Log.e("TAG", "onSuccess: " + token);
                Prefs.putString(Constants.SharedPreferences_FCMID, token);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckUserIsLoginOrNot();
                // This method will be executed once the timer is over
                // Start your app main activity
            }
        }, SPLASH_TIME_OUT);
    }
    private void CheckUserIsLoginOrNot() {
        if (Prefs.getString(Constants.SharedPreferences_loginKey, "").trim().equals("")) {
            Intent i = new Intent(SplaceActivity.this, SelectLanguageActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            SplaceActivity.this.finish();
        } else {
            BiometricPrompt.PromptInfo promptInfo = new BiometricAuth().createPromptInfo(this);
            if (BiometricManager.from(this)
                    .canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
                biometricPrompt.authenticate(promptInfo);
            } else {
            moveToMainActivity();
            }
        }
    }

    private void moveToMainActivity() {
        Intent i = new Intent(SplaceActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        SplaceActivity.this.finish();

    }

    @SuppressLint("NewApi")
    public void setLocale(String lang) {
        Locale locale;
//        Sessions session = new Sessions(context);
        //Log.e("Lan",session.getLanguage());
        locale = new Locale(lang);
        Configuration config = new Configuration(getResources().getConfiguration());
        Locale.setDefault(locale);
        config.setLocale(locale);

        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

    }

   public interface AuthSuccess {
        void onSuccess();
        void onFailure();
        void OnError();
    }

}
