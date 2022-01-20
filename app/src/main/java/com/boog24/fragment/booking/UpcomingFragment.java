package com.boog24.fragment.booking;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boog24.R;
import com.boog24.adapter.booking.UpcomingAdapter;
import com.boog24.custom.Constants;
import com.boog24.databinding.FragmentUpcomingBinding;
import com.boog24.extra.BaseFragment;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.myBookings.Result;
import com.boog24.presenter.GetMyBookingsPresenter;
import com.boog24.view.IMyBookingsView;
import com.pixplicity.easyprefs.library.Prefs;

public class UpcomingFragment extends BaseFragment implements IMyBookingsView {

    FragmentUpcomingBinding binding;
    GetMyBookingsPresenter getMyBookingsPresenter;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            if(getActivity() != null)
            {

                if (Prefs.getString(Constants.SharedPreferences_loginKey,"").equalsIgnoreCase("")){
                            windowPopUpForLogin(getActivity(),getResources().getString(R.string.pls_login_first));
                }else {
                    if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
                        getMyBookingsPresenter.userSignin(getActivity(), "upcoming");
                    } else {
                        NetworkAlertUtility.showNetworkFailureAlert(getActivity());
                    }
                }

            }else {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (Prefs.getString(Constants.SharedPreferences_loginKey,"").equalsIgnoreCase("")){
                            windowPopUpForLogin(getActivity(),getResources().getString(R.string.pls_login_first));
                        }else {
                            if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
                                getMyBookingsPresenter.userSignin(getActivity(), "upcoming");
                            } else {
                                NetworkAlertUtility.showNetworkFailureAlert(getActivity());
                            }
                        }

                    }
                }, 100);
            }






        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Prefs.getString(Constants.SharedPreferences_loginKey, "").equalsIgnoreCase("")) {
            windowPopUpForLogin(getActivity(), getResources().getString(R.string.pls_login_first));
        } else {
            if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
                getMyBookingsPresenter.userSignin(getActivity(), "upcoming");
            } else {
                NetworkAlertUtility.showNetworkFailureAlert(getActivity());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming, container, false);
        View v = binding.getRoot();

        getMyBookingsPresenter=new GetMyBookingsPresenter();
        getMyBookingsPresenter.setView(this);

//    if (Prefs.getString(Constants.SharedPreferences_loginKey, "").equalsIgnoreCase("")) {
////        windowPopUpForLogin(getActivity(), getActivity().getResources().getString(R.string.pls_login_first));
//
//    } else {
//        if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
//            getMyBookingsPresenter.userSignin(getActivity(), "upcoming");
//        } else {
//            NetworkAlertUtility.showNetworkFailureAlert(getActivity());
//        }
//    }
        return v;
    }

    @Override
    public void onGetDetail(Result response) {

        if (response.getStatus()==200){

            UpcomingAdapter salonListingAdapter = new UpcomingAdapter(getActivity(),response.getDataList(),"upcoming");
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

