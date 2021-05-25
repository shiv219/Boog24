package com.boog24.fragment.salondetail;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.adapter.RatingAdapter;
import com.boog24.databinding.FragmentRatingBinding;
import com.boog24.extra.BaseFragment;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.CommonOffset;
import com.boog24.modals.getSaloonDetail.Result;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.presenter.GetSaloonDetailPresenter;
import com.boog24.view.ICommonView;
import com.boog24.view.IGetSaloonDetailView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class RatingFragment  extends BaseFragment implements IGetSaloonDetailView , ICommonView {

    FragmentRatingBinding binding;
    GetSaloonDetailPresenter getSaloonDetailPresenter;
    GetCommonDataPresenter getCommonDataPresenter;
    String salonId;
    public RatingFragment(String salonId) {
        super();
        this.salonId=salonId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rating, container, false);
        View v = binding.getRoot();

        getSaloonDetailPresenter=new GetSaloonDetailPresenter();
        getSaloonDetailPresenter.setView(this);

        getCommonDataPresenter=new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);
        if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
            getSaloonDetailPresenter.userSignin(getActivity(),salonId);
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(getActivity());
        }


        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog myDialog = new Dialog(getActivity());
                myDialog.setContentView(R.layout.rating_dialog);
                //  myDialog.setCanceledOnTouchOutside(false);
                RatingBar ratingBar = myDialog.findViewById(R.id.ratingBar);
                EditText edtFeedback = myDialog.findViewById(R.id.edtFeedback);
                Button btnAdd = myDialog.findViewById(R.id.btnAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                        if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
                            getCommonDataPresenter.addFeedBack(getActivity(),"",salonId, String.valueOf(ratingBar.getRating()),edtFeedback.getText().toString());
                        } else {
                            NetworkAlertUtility.showNetworkFailureAlert(getActivity());
                        }
                    }
                });

                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });

        return v;
    }

    @Override
    public void onGetDetail(Result response) {

        if (response.getStatus()==200){

            RatingAdapter ratingAdapter = new RatingAdapter(getActivity(),response.getSaloonData().getReviews());
            binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recyclerview.setAdapter(ratingAdapter);
        }
    }

    @Override
    public void onGetDetail(CommonOffset response) {
        final Dialog myDialog = new Dialog(getActivity());
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

