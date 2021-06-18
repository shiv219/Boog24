package com.boog24.custom;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.ActivityCompat;

public class Constants {
    public static final String MEDIA_PREF = "Boog24";
    public static final long SPLASH_TIME=2500;
    public static final String DEVICE_TYPE="0";
    public static final String BASE_URL = "http://142.4.6.224/~cp/boog24/Userapi/";
    public static final String ACCESS_TOKEN = "boog2412g549";
    public static String SharedPreferences_UserToken = "user_tokens";
    public static final String PROFILE_DATA = "profile_data";
    public static String DEVICE_ID = "";

    public static final String SharedPreferences_Id = "id";
    public static final String SharedPreferences_country_code = "country_code";
    public static final String SharedPreferences_FirstName = "firstName";
    public static final String SharedPreferences_Gender= "gender";
    public static final String SharedPreferences_LastName= "lastName";
    public static final String SharedPreferences_Langauge= "langaugae";
    public static final String SharedPreferences_Email = "emailid";
    public static final String SharedPreferences_Mobile = "mobileno";
    public static final String SharedPreferences_profileimage= "profileimage";
    public static final String SharedPreferences_VerificationStatus= "verificationStatus";
    public static final String SharedPreferences_loginKey = "login_key";
    public static final String SharedPreferences_notification_status = "show_notification_status";
    public static final String SharedPreferences_full_name = "full_name";
    public static final String SharedPreferences_NotiStatus = "noti_status";
    public static final String SharedPreferences_UserType = "userType";
    public static final String SharedPreferences_userId = "user_id";
    public static final String SharedPreferences_latitude = "latitude";
    public static final String SharedPreferences_longitude = "longitude";
    public static final String SharedPreferences_Address = "address";
    public static final String SharedPreferences_BIO= "bio";
    public static final String SharedPreferences_About = "about";
    public static final String SharedPreferences_FCMID = "fcmid";
    public static final String SharedPreferences_RegisterStatus= "RegisterStatus";

    public static final String SharedPreferences_seller = "seller";
    public static final String SharedPreferences_customer_type_id = "customer_type_id";
    public static final String SharedPreferences_notification_id = "notification_id";
    public static final String SharedPreferences_order_id = "order_id";
    public static final String SharedPreferences_update_status = "update_status";



    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void reloadActivity(Activity activity) {
        activity.finish();
        activity.startActivity(activity.getIntent());
    }

    public static String dental(int n){
        return n < 10 ? "0" + n : "" + n;
    }




}
