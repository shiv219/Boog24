package com.boog24.presenter;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.modals.CommonOffset;
import com.boog24.view.ICommonView;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCommonDataPresenter extends BasePresenter<ICommonView> {


    public void changeLanguageApi(final Activity activity) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .changeLanguage(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_loginKey, ""), Prefs.getString(Constants.SharedPreferences_Langauge, ""), Prefs.getString(Constants.SharedPreferences_Langauge, "").equals("en") ? "english" : "german")
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }

    public void userSignin(final Activity activity, String deviceid, String emailid, String password) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .userSignin(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_Langauge, ""), emailid, password, deviceid, Prefs.getString(Constants.SharedPreferences_FCMID, ""), Constants.DEVICE_TYPE)
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }

    public void userSignup(final Activity activity, String emailid, String fname, String lname, String phone, String password, String confirmpassword, String gender, String countryCode) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .userSignup(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_Langauge, ""), emailid, fname, lname, phone, password, confirmpassword, gender, countryCode)
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }


    public void forgotPassword(final Activity activity, String emailid) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .forgotPassword(Constants.ACCESS_TOKEN,Prefs.getString(Constants.SharedPreferences_Langauge,""),emailid)
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }
    public void getCategories(final Activity activity ) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .getCategories(Constants.ACCESS_TOKEN,Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""))
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }
    public void update_customer_image(final Activity activity, String profileimage, Uri uri) {


        String[] projection1 = { MediaStore.Images.Media.DATA };
        Cursor cursor1 = activity.getContentResolver().query(uri, projection1, null, null, null);
        int column_index1 = cursor1.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor1.moveToFirst();
        String s1=cursor1.getString(column_index1);
        cursor1.close();

        File file1 = new File(s1);
        String fileName1= String.valueOf(file1);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/*"), file1);
        MultipartBody.Part body1;
        body1 = MultipartBody.Part.createFormData("profileimage", file1.getName(), requestFile1);

        getView().enableLoadingBar(activity, true, null);
        MyApplication.getInstance()
                .getApiService()
                .updateProfileImage(Constants.ACCESS_TOKEN,Prefs.getString(Constants.SharedPreferences_loginKey, ""),Prefs.getString(Constants.SharedPreferences_Langauge,""),
                        body1)
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }
    public void logout(final Activity activity ) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .logout(Constants.ACCESS_TOKEN,Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""))
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }
    public void addFeedBack(final Activity activity,String orderId,String salonId,String rating,String feedback ) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .addFeedBack(Constants.ACCESS_TOKEN,Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""),orderId,salonId,rating,feedback)
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }

    public void cancelBooking(final Activity activity,String orderId ) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .cancelBooking(Constants.ACCESS_TOKEN,Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""),orderId)
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }


    public void getContactUsDetails(final Activity activity ) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .getContactUsDetails(Constants.ACCESS_TOKEN,Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""))
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }
    public void checkSalonAvailability(final Activity activity,String employid,String date,String salonid ) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .checkSalonAvailability(Constants.ACCESS_TOKEN,Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""),employid,date,salonid)
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }

    public void getRecommendSalon(final Activity activity, String user_email, String first_name, String last_name, String contact_number,
                                  String shopname, String street, String cityName, String speak, String category_id, String zipcode, String contactPerson) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .recommend_salon(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_Langauge, ""),
                        user_email, first_name, last_name, contact_number, shopname, street, cityName, speak, category_id,
                        Prefs.getString(Constants.SharedPreferences_userId, ""), zipcode, contactPerson)
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }

    public void updateNotificationStatus(final Activity activity,String status) {
        getView().enableLoadingBar(activity, true, null);
        MyApplication.getInstance()
                .getApiService()
                .updateNotificationStatus(Constants.ACCESS_TOKEN,Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge, ""),
                         status)
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });

    }


    public void deleteMyAccount(final Activity activity){
        getView().enableLoadingBar(activity, true, null);
        MyApplication.getInstance()
                .getApiService()
                .deleteAccount(Constants.ACCESS_TOKEN,Prefs.getString(Constants.SharedPreferences_loginKey,""),
                        Prefs.getString(Constants.SharedPreferences_Langauge, "") )
                .enqueue(new Callback<CommonOffset>() {
                    @Override
                    public void onResponse(Call<CommonOffset> call, Response<CommonOffset> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<CommonOffset> call, Throwable t) {
                        getView().enableLoadingBar(activity, false, null);
                        try {
                            t.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getView().onError(null);
                    }
                });
    }
}