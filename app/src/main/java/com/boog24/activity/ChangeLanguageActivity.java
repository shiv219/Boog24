package com.boog24.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.databinding.ActivityChangeLanguageBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.modals.CommonOffset;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.view.ICommonView;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Locale;

public class ChangeLanguageActivity extends BaseActivity implements ICommonView {

    ActivityChangeLanguageBinding binding;
    String selected = "de";
    GetCommonDataPresenter getCommonDataPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_language);
        binding.setActivity(this);

        selected = Prefs.getString(Constants.SharedPreferences_Langauge, "");

        if (selected.equalsIgnoreCase("en")) {
            binding.rltGerman.setBackgroundColor(getResources().getColor(R.color.white));
            binding.rltEng.setBackground(getResources().getDrawable(R.drawable.rounded_border_green));
        } else {
            binding.rltEng.setBackgroundColor(getResources().getColor(R.color.white));
            binding.rltGerman.setBackground(getResources().getDrawable(R.drawable.rounded_border_green));
        }
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getCommonDataPresenter = new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);


    }


    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnNext:

                if (selected.equalsIgnoreCase("en"))
                    Prefs.putString(Constants.SharedPreferences_Langauge, "en");
                else
                    Prefs.putString(Constants.SharedPreferences_Langauge, "gr");
                if (!Prefs.getString(Constants.SharedPreferences_loginKey, "").equals(""))
                    getCommonDataPresenter.changeLanguageApi(this);

                    setLocale(selected.equals("en") ? selected : "de");
                break;

            case R.id.rltEng:
                selected = "en";
//                setLocale("en");
                binding.rltGerman.setBackgroundColor(getResources().getColor(R.color.white));
                binding.rltEng.setBackground(getResources().getDrawable(R.drawable.rounded_border_green));
                break;

            case R.id.rltGerman:
                selected="de";
//                setLocale("de");
                binding.rltEng.setBackgroundColor(getResources().getColor(R.color.white));
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

        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    @Override
    public void onGetDetail(CommonOffset response) {
        if (response != null)
            if (response.getStatus() == 200) {
            } else {
                windowPopUp(this, response.getMessage());
            }
    }

    @Override
    public Context getContext() {
        return null;
    }
}
