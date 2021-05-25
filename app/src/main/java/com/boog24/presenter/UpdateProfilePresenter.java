package com.boog24.presenter;

import android.app.Activity;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.modals.updateProfile.Result;
import com.boog24.view.IUpdateProfileView;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfilePresenter extends BasePresenter<IUpdateProfileView> {

    public void userSignin(final Activity activity, String firstname,String lastname,String phone, String gender) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .updateProfile(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""),firstname,lastname,phone, gender)
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
}

