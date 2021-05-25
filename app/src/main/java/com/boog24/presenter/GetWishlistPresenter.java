package com.boog24.presenter;

import android.app.Activity;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.modals.getWishlist.Result;
import com.boog24.view.IGetWishlistView;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetWishlistPresenter extends BasePresenter<IGetWishlistView> {


    public void getWishlist(final Activity activity) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .getWishlist(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""))
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

