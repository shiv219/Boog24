package com.boog24.extra;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {


    public static String getFormatedDouble(String amount) {
        return new DecimalFormat("#.00").format(Double.parseDouble(amount));
    }


    public static String convertDateTime(String paramString) {
        try {
            Date localDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(paramString);
            String str = new SimpleDateFormat("dd-MM-yyyy   hh:mm").format(localDate);
            return str;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "";
    }

    public static String convertDate(String paramString) {
        try {
            Date localDate = new SimpleDateFormat("yyyy-MM-dd").parse(paramString);
            String str = new SimpleDateFormat("dd-MM-yyyy").format(localDate);
            return str;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "";
    }

    public static void customLog(String paramString) {
    }

    public static void customLogd(String paramString) {
    }

    public static void customToast(Context paramContext, String paramString) {
        Toast.makeText(paramContext, paramString, Toast.LENGTH_SHORT).show();
    }

    public static int dpToPx(int paramInt, Context paramContext) {
        DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
        return Math.round(paramInt * (localDisplayMetrics.xdpi / 160.0F));
    }


    public static String getFormatedDate(long paramLong, String paramString) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTimeInMillis(paramLong);
        return localSimpleDateFormat.format(localCalendar.getTime());
    }


    public static void storeState(SharedPreferences paramSharedPreferences, String paramString, int paramInt) {
        SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
        localEditor.putInt(paramString, paramInt);
        localEditor.commit();
    }

    public static void storeStateOfString(SharedPreferences paramSharedPreferences, String paramString1, String paramString2) {
        SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
        localEditor.putString(paramString1, paramString2);
        localEditor.commit();
    }

    public static String toSlug(String paramString) {
        return Pattern.compile("[^\\w-]").matcher(Normalizer.normalize(Pattern.compile("[\\s]").matcher(paramString).replaceAll("-"), Normalizer.Form.NFD)).replaceAll("").toLowerCase(Locale.ENGLISH);
    }

    public static boolean validMail(String paramString) {
        return Pattern.compile(".+@.+\\.[a-z]+").matcher(paramString).matches();
    }

    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 6 || phone.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
//                txtPhone.setError("Not Valid Number");
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }


    public static Drawable DrawableChange(Activity ctx, int d, int color) {
        Drawable drawable1 = ctx.getResources().getDrawable(d).mutate();
        drawable1.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        return drawable1;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches())
            isValid = true;
        return isValid;
    }

    public static boolean validateString(String str) {
        return stringNotNull(str) && stringNotEmpty(str);
    }

    private static boolean stringNotNull(String str) {
        return str != null;
    }

    private static boolean stringNotEmpty(String str) {
        return !str.isEmpty();
    }

    public static boolean isValidPassword(String password) {
        return !(TextUtils.isEmpty(password) || password.length() < 6);
    }


    //Utility
    public static final String TAG = Utils.class.getSimpleName();
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        && ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static String getStringBase64(String myUrl) {
        String base64 = "";
        Bitmap bitmap;
        StrictMode.ThreadPolicy policy = null;
        String newUrlString = "";
        try {
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL oldUrl = new URL(myUrl);
            if (myUrl.contains("http://")) {
                newUrlString = oldUrl.toString().replace("http", "https");
            } else {
                newUrlString = myUrl;
            }
            URL newUrl = new URL(newUrlString);
            bitmap = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
            System.out.println("bitmap>>>>>>>>> " + bitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }


    public static String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static int roundToHalf(double f) {
        int c = (int) ((f) + 0.5f);
        double n = f + 0.49f;
        return (n - c) % 2 == 0 ? (int) f : c;
    }

}
