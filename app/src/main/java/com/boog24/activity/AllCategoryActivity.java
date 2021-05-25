package com.boog24.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.boog24.R;
import com.boog24.adapter.AllCategoriesHeaderAdapter;
import com.boog24.databinding.ActivityAllCategoriesBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.CommonOffset;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.view.ICommonView;
import com.pixplicity.easyprefs.library.Prefs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AllCategoryActivity extends BaseActivity implements ICommonView {

    ActivityAllCategoriesBinding binding;
    GetCommonDataPresenter getCommonDataPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_categories);
        binding.setActivity(this);


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getCommonDataPresenter = new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);

        if (NetworkAlertUtility.isConnectingToInternet(this)) {
            getCommonDataPresenter.getCategories(this);
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(this);
        }


    }

    @Override
    public void onGetDetail(CommonOffset response) {
        if (response.getStatus()==200) {
            AllCategoriesHeaderAdapter homeCategoriesAdapter = new AllCategoriesHeaderAdapter(this,response.getCategoryData());
            binding.rcvCategories.setLayoutManager(new LinearLayoutManager(this));
            binding.rcvCategories.setAdapter(homeCategoriesAdapter);
        }else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        } else {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Failure")
                    .setMessage(response.getMessage())
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show().setCanceledOnTouchOutside(false);
        }

    }

    @Override
    public Context getContext() {
        return null;
    }
}
