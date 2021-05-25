package com.boog24.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.adapter.WishlistAdapter;
import com.boog24.databinding.ActivityWishlistBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.getWishlist.Result;
import com.boog24.presenter.AddWishlistPresenter;
import com.boog24.presenter.GetWishlistPresenter;
import com.boog24.view.IAddWishlistView;
import com.boog24.view.IGetWishlistView;
import com.pixplicity.easyprefs.library.Prefs;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class WishlistActivity extends BaseActivity implements IGetWishlistView , IAddWishlistView {

    ActivityWishlistBinding binding;
    GetWishlistPresenter getWishlistPresenter;
     AddWishlistPresenter addWishlistPresenter;
    String apiType="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wishlist);
        binding.setActivity(this);

        getWishlistPresenter=new GetWishlistPresenter();
        getWishlistPresenter.setView(this);

        addWishlistPresenter=new AddWishlistPresenter();
        addWishlistPresenter.setView(this);

        if (NetworkAlertUtility.isConnectingToInternet(this)) {
            getWishlistPresenter.getWishlist(this);
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(this);
        }

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @Override
    public void onGetDetail(Result response) {
        if (response.getStatus()==200) {
            if (response.getData().size() > 0) {
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.tvNodata.setVisibility(View.GONE);
                WishlistAdapter headerAdapter = new WishlistAdapter(this, WishlistActivity.this, response.getData());
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                binding.recyclerView.setAdapter(headerAdapter);
                binding.recyclerView.setNestedScrollingEnabled(true);
            } else {
                binding.tvNodata.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            }
        }else if (response.getStatus()==400){
            binding.tvNodata.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);

        }else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }else{
            final Dialog myDialog = new Dialog(this);
            myDialog.setContentView(R.layout.alert_label_editor);
            //  myDialog.setCanceledOnTouchOutside(false);
            TextView popText = myDialog.findViewById(R.id.popText);
            popText.setText(response.getMessage());
            Button btnCancel = myDialog.findViewById(R.id.btnCancel);
            Button btnLogin = myDialog.findViewById(R.id.btnLogin);
            btnLogin.setVisibility(View.GONE);
            btnCancel.setText(getResources().getString(R.string.ok));
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDialog.dismiss();
                }
            });

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }

    }

    public void remove(String id){
         apiType="remove";
        if (NetworkAlertUtility.isConnectingToInternet(WishlistActivity.this)) {
            addWishlistPresenter.addWishlist(WishlistActivity.this, id);
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(WishlistActivity.this);
        }

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onAddedWishlist(com.boog24.modals.addWishlist.Result response) {

        if (response.getStatus()==200){
            if (NetworkAlertUtility.isConnectingToInternet(this)) {
                getWishlistPresenter.getWishlist(this);
            } else {
                NetworkAlertUtility.showNetworkFailureAlert(this);
            }
        }else if (response.getStatus()==406){
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
    }
}
