package com.boog24.presenter;

import android.app.Activity;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.modals.addWishlist.Result;
import com.boog24.view.IAddWishlistView;
import com.boog24.view.IGetWishlistView;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWishlistPresenter extends BasePresenter<IAddWishlistView> {

    public void addWishlist(final Activity activity, String salonId) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .addToWishlist(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_loginKey, ""), Prefs.getString(Constants.SharedPreferences_Langauge, ""), salonId)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onAddedWishlist(response.body());
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
