package com.boog24.presenter;

import android.app.Activity;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.custom.Constants;
import com.boog24.view.IGetSaloonsView;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSaloonsPresenter extends BasePresenter<IGetSaloonsView> {

    public void getSallons(final Activity activity,String category_id,String subcategoryid,String searchString) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .getSaloons(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""),"other",category_id,subcategoryid,Prefs.getString(Constants.SharedPreferences_latitude,""),Prefs.getString(Constants.SharedPreferences_longitude,""),searchString)
                .enqueue(new Callback<com.boog24.modals.getSaloons.Result>() {
                    @Override
                    public void onResponse(Call<com.boog24.modals.getSaloons.Result> call, Response<com.boog24.modals.getSaloons.Result> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<com.boog24.modals.getSaloons.Result> call, Throwable t) {
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

    public void getSallonsByLat(final Activity activity,String lat,String longitude,String category_id) {
        getView().enableLoadingBar(activity, true, activity.getResources().getString(R.string.loading));
        MyApplication.getInstance()
                .getApiService()
                .getSaloons(Constants.ACCESS_TOKEN, Prefs.getString(Constants.SharedPreferences_loginKey,""),Prefs.getString(Constants.SharedPreferences_Langauge,""),"other",category_id,"",lat,longitude,"")
                .enqueue(new Callback<com.boog24.modals.getSaloons.Result>() {
                    @Override
                    public void onResponse(Call<com.boog24.modals.getSaloons.Result> call, Response<com.boog24.modals.getSaloons.Result> response) {
                        getView().enableLoadingBar(activity, false, null);
                        getView().onGetDetail(response.body());
                    }

                    @Override
                    public void onFailure(Call<com.boog24.modals.getSaloons.Result> call, Throwable t) {
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
