package com.boog24.extra;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.activity.LoginActivity;
import com.boog24.adapter.MainViewPagerAdapter;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import dmax.dialog.SpotsDialog;


@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    SpotsDialog mProgressDialog;
    ProgressDialog dialog;

    private static final String TAG = BaseActivity.class.getSimpleName();
    protected ActionBar actionBar;
    public ViewPager viewPager;
    MainViewPagerAdapter adapter;
    FragmentManager fm;
    public  boolean serviceBound = false;
    Pair<String, String> temp;
    public String musicUrl ="";
    public String title ="";
    public String selectedBeatId;
    public String likeStatus;
    public JSONArray jsonArray;
    public SeekBar mSeekBar;
    public Timer mTimer;
    public boolean muteAudioType = true;
    public  int musicIndex=0;
    public boolean notToCallViewPager = false;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        actionBar = getSupportActionBar();
    }

    public String tag() {
        return getClass().getSimpleName();
    }

    public void log(String message) {
        Log.d(tag(), message);
    }

    public void toast(Context context, final String message) {
        try {
            if (context!=null && !((Activity) context).isFinishing()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadProgressBar(Context context, String message, boolean cancellable) {

        try {
            if (mProgressDialog == null)
                mProgressDialog = new SpotsDialog(this, message, R.style.SpotCustomDialog);
            if ((context != null && !((Activity) context).isFinishing()) && !mProgressDialog.isShowing())
                mProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void dismissProgressBar(Context context) {
        try {
            if (mProgressDialog != null) {
                if ((context != null && !((Activity) context).isFinishing()) && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                mProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public AlertDialog.Builder getAlertDialogBuilder(String title, String message, boolean cancellable) {
        return new AlertDialog.Builder(this, R.style.AppTheme_AlertDialog)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(cancellable);
    }

    public void enableLoadingBar(Context context, boolean enable, String s) {
        if (enable) {
//            loadProgressBar(context, s, false);
            MyApplication.getInstance().progressON((Activity) context, s);
        } else {
//            dismissProgressBar(context);
            MyApplication.getInstance().progressOFF();
        }

       /* if (!isFinishing() && mProgressDialog != null) {
            mProgressDialog.dismiss();
        }*/
    }

    public void onError(String reason) {
        onError(reason, false);
    }


    public void onError(String reason, final boolean finishOnOk) {
        if (Utils.validateString(reason)) {
            getAlertDialogBuilder(null, reason, false).setPositiveButton(getString(R.string.ok), finishOnOk ? new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            } : null).show();
        } else {
            getAlertDialogBuilder(null, getString(R.string.default_error), false)
                    .setPositiveButton(getString(R.string.ok), finishOnOk ? new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    } : null).show();
        }
    }

    public void showToast(String msg){
        try{
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showSOUT(String msg){
        try{
            System.out.println(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }


    public void dismissDialog() {
        dialog.dismiss();
    }

    public float distance (float lat_a, float lng_a, float lat_b, float lng_b )
    {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public String getCityByuseingLatLng(Context context, float lat, float lng) throws IOException {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
        String cityName = addresses.get(0).getLocality();
        String stateName = addresses.get(0).getAdminArea();
        String countryName = addresses.get(0).getCountryName();
        return addresses.get(0).getAddressLine(0);
    }

    public void showSecurityAlert(String message, final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Info");
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("dummy_value", 0);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    protected View getSnackbarAnchorView() {
        return null;
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.gradiant_shape);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.white));
            window.setBackgroundDrawable(background);

          /*  if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setNavigationBarColor(activity.getResources().getColor(R.color.white));
            }*/
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant1(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.gradient_bg_horizontal);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setBackgroundDrawable(background);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarWhite(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.color.white);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    public void changeStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void windowPopUp(Activity activity, String msg){
        final Dialog myDialog = new Dialog(activity);
        myDialog.setContentView(R.layout.alert_label_editor);
        //  myDialog.setCanceledOnTouchOutside(false);
        TextView popText = myDialog.findViewById(R.id.popText);
        popText.setText(msg);
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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);

            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


    public void windowPopUpForLogin(Activity activity, String msg){
        final Dialog myDialog = new Dialog(activity);
        myDialog.setContentView(R.layout.alert_label_editor);
        myDialog.setCanceledOnTouchOutside(false);
        TextView popText = myDialog.findViewById(R.id.popText);
        popText.setText(msg);
        Button btnCancel = myDialog.findViewById(R.id.btnCancel);
        Button btnLogin = myDialog.findViewById(R.id.btnLogin);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishAffinity();

            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public static String getStringBase64(String myUrl){
        String base64 = "";
        Bitmap bitmap;
        StrictMode.ThreadPolicy policy = null;
        String newUrlString = "";
        try{
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL oldUrl = new URL(myUrl);
            if(myUrl.contains("http://")){
                newUrlString= oldUrl.toString().replace("http", "https");
            }else{
                newUrlString  =myUrl;
            }
            URL newUrl = new URL(newUrlString);
            bitmap = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
            Bitmap converetdImage = getResizedBitmap(bitmap, 200);

            System.out.println("bitmap>>>>>>>>> "+converetdImage);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            converetdImage.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return base64;
    }



    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static Bitmap getResizedBitmap(Bitmap image, int newHeight, int newWidth) {
        int width = image.getWidth();
        int height = image.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }



}

