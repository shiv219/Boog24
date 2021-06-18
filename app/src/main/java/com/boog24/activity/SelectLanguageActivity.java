package com.boog24.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.databinding.ActivitySelectLanguageBinding;
import com.boog24.extra.BaseActivity;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Locale;

public class SelectLanguageActivity extends BaseActivity {

    ActivitySelectLanguageBinding binding;
    String selected = "de";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_language);
        binding.setActivity(this);

        selected = Prefs.getString(Constants.SharedPreferences_Langauge, "");

        if (selected.equalsIgnoreCase("en")) {
            binding.rltGerman.setBackgroundColor(getResources().getColor(R.color.white));
            binding.rltEng.setBackground(getResources().getDrawable(R.drawable.rounded_border_green));
        } else {
            binding.rltEng.setBackgroundColor(getResources().getColor(R.color.white));
            binding.rltGerman.setBackground(getResources().getDrawable(R.drawable.rounded_border_green));
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnNext:
                setLocale(selected);
                if (selected.equalsIgnoreCase("en"))
                    Prefs.putString(Constants.SharedPreferences_Langauge,"en");
                else
                    Prefs.putString(Constants.SharedPreferences_Langauge,"gr");
                Intent i = new Intent(SelectLanguageActivity.this, IntroActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                break;

            case R.id.rltEng:
                selected="en";
                binding.rltEng.setBackground(getResources().getDrawable(R.drawable.rounded_border_green));
                binding.rltGerman.setBackground(getResources().getDrawable(R.drawable.rounded_border_white));
                break;

            case R.id.rltGerman:
                selected="de";
                binding.rltEng.setBackground(getResources().getDrawable(R.drawable.rounded_border_white));
                binding.rltGerman.setBackground(getResources().getDrawable(R.drawable.rounded_border_green));
                break;
        }
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

//        Intent intent=new Intent(this,SplaceActivity.class);
//        startActivity(intent);
//        finish();
    }
}
