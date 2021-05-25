package com.boog24.presenter;

import android.app.Activity;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.modals.getServiceTimeSlots.Result;
import com.boog24.view.IGetTimeSlotsView;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTimeSlotsPresenter extends BasePresenter<IGetTimeSlotsView> {

    public void getTimeSlots(final Activity activity, String employee_id, String date_time ,String salon_id ,String services_id) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .getServiceTimeSlots(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_loginKey, ""), Prefs.getString(Constants.SharedPreferences_Langauge, ""), employee_id,date_time,salon_id,services_id)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().ongetTimeSLots(response.body());
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