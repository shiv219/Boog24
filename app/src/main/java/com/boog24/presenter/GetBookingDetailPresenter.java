package com.boog24.presenter;

import android.app.Activity;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.modals.bookingAppointment.Result;
import com.boog24.view.IBookingDetailView;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBookingDetailPresenter extends BasePresenter<IBookingDetailView> {

    public void getBooking(final Activity activity,String fName,String lName,String email, String phone,String serviceId,String empId,String date,String salonId,String amount ,String fromTime,String toTime, String moreId) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));

        if(empId.equals("")){
            empId = moreId;
        }else{
            empId = "0";
        }
        MyApplication.getInstance()
                .getApiService()
                .bookingAppointment(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""),fName,lName,email,phone,serviceId,empId,date,salonId,amount,fromTime,toTime)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
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

    public void getUpdateBooking(final Activity activity,String fName,String lName,String email, String phone,String serviceId,String empId,String date,String salonId,String amount ,String fromTime,String toTime, String orderId, String notificationId, String moreId) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));

        if(empId.equals("")){
            empId = moreId;
        }else{
            empId = "0";
        }
        MyApplication.getInstance()
                .getApiService()
                .updateBookingAppointment(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""),fName,lName,email,phone,serviceId,empId,date,salonId,amount,fromTime,toTime, orderId, notificationId)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetUpdateDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
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
