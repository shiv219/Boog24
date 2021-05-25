package com.boog24.fragment.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boog24.R;
import com.boog24.activity.SplaceActivity;
import com.boog24.adapter.booking.UpcomingAdapter;
import com.boog24.custom.Constants;
import com.boog24.databinding.FragmentCancelledBinding;
import com.boog24.databinding.FragmentCompletedBinding;
import com.boog24.extra.BaseFragment;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.myBookings.Result;
import com.boog24.presenter.GetMyBookingsPresenter;
import com.boog24.view.IMyBookingsView;
import com.pixplicity.easyprefs.library.Prefs;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CompletedFragment extends BaseFragment implements IMyBookingsView {

    FragmentCompletedBinding binding;
    GetMyBookingsPresenter getMyBookingsPresenter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (Prefs.getString(Constants.SharedPreferences_loginKey,"").equalsIgnoreCase("")){
                windowPopUpForLogin(getActivity(),getActivity().getResources().getString(R.string.pls_login_first));

            }else {
                if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
                    getMyBookingsPresenter.userSignin(getActivity(), "completed");
                } else {
                    NetworkAlertUtility.showNetworkFailureAlert(getActivity());
                }
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Prefs.getString(Constants.SharedPreferences_loginKey,"").equalsIgnoreCase("")){
//            windowPopUpForLogin(getActivity(),getActivity().getResources().getString(R.string.pls_login_first));
        }else {
            if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
                getMyBookingsPresenter.userSignin(getActivity(), "completed");
            } else {
                NetworkAlertUtility.showNetworkFailureAlert(getActivity());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_completed, container, false);
        View v = binding.getRoot();

        getMyBookingsPresenter=new GetMyBookingsPresenter();
        getMyBookingsPresenter.setView(this);


        if (Prefs.getString(Constants.SharedPreferences_loginKey,"").equalsIgnoreCase("")){
//            windowPopUpForLogin(getActivity(),getActivity().getResources().getString(R.string.pls_login_first));

        }else {
            if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
                getMyBookingsPresenter.userSignin(getActivity(), "completed");
            } else {
                NetworkAlertUtility.showNetworkFailureAlert(getActivity());
            }
        }

        return v;
    }

    @Override
    public void onGetDetail(Result response) {

        if (response.getStatus()==200){
            UpcomingAdapter salonListingAdapter = new UpcomingAdapter(getActivity(),response.getDataList(),"completed");
            binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recyclerview.setAdapter(salonListingAdapter);

        }
//        else if (response.getStatus()==406){
//
//            Prefs.clear();
//            startActivity(new Intent(getActivity(), SplaceActivity.class));
//            getActivity().finishAffinity();
//        }
    }
}
