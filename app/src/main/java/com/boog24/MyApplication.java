package com.boog24;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDialog;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.boog24.api.ApiService;
import com.boog24.api.CustomInterceptor;
import com.boog24.custom.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyApplication extends MultiDexApplication {

    private ApiService apiService;
    public static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication instance;


    public static SessionManager sessionManager;
    public static MyApplication getInstance(){
        if(instance== null){
            instance = new MyApplication();
        }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
//        AppEventsLogger.activateApp(this);
        printHashKey();
        instance = this;


        new Prefs.Builder()
                .setContext(this)
                .setMode(MODE_PRIVATE)
                .setPrefsName(Constants.MEDIA_PREF)
                .setUseDefaultSharedPreference(true)
                .build();

        createApiService();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    public void printHashKey(){
        // Add code to print out the key hash
          try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public ApiService createApiService() {

        Gson gson = new GsonBuilder().create();

        File httpCacheDirectory = new File(getCacheDir(), "cache_file");
        Cache cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .connectionPool(new ConnectionPool(0, 5 * 60 * 1000, TimeUnit.SECONDS))
                .addInterceptor(new CustomInterceptor(getInstance(), Locale.getDefault().getLanguage(), getAppVersion()))
                .cache(cache)
                .build();

        //init retrofit
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(ApiService.class);
        return apiService;
    }
    public ApiService getApiService() {
        return apiService;
    }
    private String getAppVersion() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "1.0";
        }
    }

    public SessionManager getSession() {
        if (sessionManager == null) {
            sessionManager = new SessionManager(getApplicationContext());
        }
        return sessionManager;
    }


    AppCompatDialog progressDialog;

    public void progressON(Activity activity, String message) {

        if (activity == null || activity.isFinishing()) {
            return;
        }


        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {

            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_loading);
            progressDialog.show();

        }


        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }


    }

    public void progressSET(String message) {

        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }


        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }

    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception ex) {
            }
        }
    }

   /* private BroadcastReceiver pushBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            String from = intent.getStringExtra("from");
            Log.i(TAG, "Receiving message: " + message + ", from " + from);
        }
    };*/


//LocalBroadcastManager.getInstance(this).registerReceiver(pushBroadcastReceiver, new IntentFilter("new-push-event"));

}