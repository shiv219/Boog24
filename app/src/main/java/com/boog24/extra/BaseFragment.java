package com.boog24.extra;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.activity.LoginActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import dmax.dialog.SpotsDialog;


@SuppressLint("Registered")
public class BaseFragment extends Fragment {
    SpotsDialog mProgressDialog;
    ProgressDialog dialog;


    private static final String TAG = BaseActivity.class.getSimpleName();
    protected ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_common, container, false);
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
                mProgressDialog = new SpotsDialog(getActivity(), message, R.style.SpotCustomDialog);
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
        return new AlertDialog.Builder(getActivity(), R.style.AppTheme_AlertDialog)
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

    }

    public void onError(String reason) {
        onError(reason, false);
    }


    public void onError(String reason, final boolean finishOnOk) {
        if (Utils.validateString(reason)) {
            getAlertDialogBuilder(null, reason, false).setPositiveButton(getString(R.string.ok), finishOnOk ? new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    finish();
                }
            } : null).show();
        } else {
            getAlertDialogBuilder(null, getString(R.string.default_error), false)
                    .setPositiveButton(getString(R.string.ok), finishOnOk ? new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            finish();
                        }
                    } : null).show();
        }
    }

    public void showToast(String msg){
        try{
            Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
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

    public void windowPopUp(Activity activity, String msg){
        final Dialog myDialog = new Dialog(activity);
        myDialog.setContentView(R.layout.alert_label_editor);
          myDialog.setCanceledOnTouchOutside(false);
        TextView popText = myDialog.findViewById(R.id.popText);
        popText.setText(msg);
        Button btnCancel = myDialog.findViewById(R.id.btnCancel);
        Button btnLogin = myDialog.findViewById(R.id.btnLogin);

        btnCancel.setText(R.string.ok);
        btnLogin.setVisibility(View.GONE);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, LoginActivity.class);
//                startActivity(intent);
//                activity.finish();
//
//            }
//        });
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
                startActivity(intent);
                activity.finish();

            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
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
    return cityName;
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.gradient_bg_horizontal);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
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


}

/*
public abstract class BaseActivity extends AppCompatActivity {

    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showToast(String msg){
        try{
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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

}
*/
