package com.boog24.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.databinding.ActivityAboutusBinding;
import com.boog24.extra.BaseActivity;
import com.pixplicity.easyprefs.library.Prefs;

public class AboutUsActivity extends BaseActivity {

    ActivityAboutusBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_aboutus);
        binding.setActivity(this);


        if (getIntent().getStringExtra("what").equalsIgnoreCase("impessum")){
            binding.textHeaderALA.setText(getResources().getString(R.string.impessum));
        }else if (getIntent().getStringExtra("what").equalsIgnoreCase("agb")){
            binding.textHeaderALA.setText(getResources().getString(R.string.agb));
        }  else  if (getIntent().getStringExtra("what").equalsIgnoreCase("date")){
            binding.textHeaderALA.setText(getResources().getString(R.string.datenschutz));
        }else  if (getIntent().getStringExtra("what").equalsIgnoreCase("user")){
            binding.textHeaderALA.setText(getResources().getString(R.string.user_generated));
        }


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        WebView webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new WebChromeClient());

        if (getIntent().getStringExtra("what").equalsIgnoreCase("about")) {

            if (Prefs.getString(Constants.SharedPreferences_Langauge, "").equalsIgnoreCase("en"))
                webView.loadUrl(Constants.BASE_WEB_URL + "about-us-view/en"); // http://dev.w3ondemand.com/rydon/
            else
                webView.loadUrl(Constants.BASE_WEB_URL + "about-us-view/gr"); // http://dev.w3ondemand.com/rydon/
        }else if (getIntent().getStringExtra("what").equalsIgnoreCase("impessum")) {

            if (Prefs.getString(Constants.SharedPreferences_Langauge, "").equalsIgnoreCase("en"))
                webView.loadUrl(Constants.BASE_WEB_URL + "impessum-view/en"); // http://dev.w3ondemand.com/rydon/
            else
                webView.loadUrl(Constants.BASE_WEB_URL + "impessum-view/gr"); // http://dev.w3ondemand.com/rydon/
        }else if (getIntent().getStringExtra("what").equalsIgnoreCase("agb")) {

            if (Prefs.getString(Constants.SharedPreferences_Langauge, "").equalsIgnoreCase("en"))
                webView.loadUrl(Constants.BASE_WEB_URL + "agb-view/en"); // http://dev.w3ondemand.com/rydon/
            else
                webView.loadUrl(Constants.BASE_WEB_URL + "agb-view/gr"); // http://dev.w3ondemand.com/rydon/
        }else if (getIntent().getStringExtra("what").equalsIgnoreCase("date")) {

            if (Prefs.getString(Constants.SharedPreferences_Langauge, "").equalsIgnoreCase("en"))
                webView.loadUrl(Constants.BASE_WEB_URL + "datenschutz-view/en"); // http://dev.w3ondemand.com/rydon/
            else
                webView.loadUrl(Constants.BASE_WEB_URL + "datenschutz-view/gr"); // http://dev.w3ondemand.com/rydon/
        }else if (getIntent().getStringExtra("what").equalsIgnoreCase("user")) {

            if (Prefs.getString(Constants.SharedPreferences_Langauge, "").equalsIgnoreCase("en"))
                webView.loadUrl(Constants.BASE_WEB_URL + "user-policy/en"); // http://dev.w3ondemand.com/rydon/
            else
                webView.loadUrl(Constants.BASE_WEB_URL + "user-policy/gr"); // http://dev.w3ondemand.com/rydon/
        }

    }
}
