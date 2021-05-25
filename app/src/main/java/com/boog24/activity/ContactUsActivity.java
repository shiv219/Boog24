package com.boog24.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.boog24.R;
import com.boog24.databinding.ActivityAboutusBinding;
import com.boog24.databinding.ActivityContactUsBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.CommonOffset;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.view.ICommonView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class ContactUsActivity extends BaseActivity implements ICommonView {
    GetCommonDataPresenter getCommonDataPresenter;
    MainActivity mainActivity;
    ActivityContactUsBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);
        binding.setActivity(this);

        getCommonDataPresenter=new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);

        if (NetworkAlertUtility.isConnectingToInternet(ContactUsActivity.this)) {
            getCommonDataPresenter.getContactUsDetails(ContactUsActivity.this);
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(ContactUsActivity.this);
        }
        mainActivity=new MainActivity();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ContactUsActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
//                mainActivity.viewPager.setCurrentItem(1);
            }
        });


    }

    @Override
    public void onGetDetail(CommonOffset response) {

        if (response.getStatus()==200){
            binding.tvEmail.setText(response.getContact_us_email());
            binding.tvPhone.setText(response.getContact_us_phone_number());
        }
    }

    @Override
    public Context getContext() {
        return null;
    }
}

