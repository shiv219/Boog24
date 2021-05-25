package com.boog24.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boog24.R;
import com.boog24.adapter.BookingPager;
import com.boog24.databinding.FragmentAppointmentBinding;
import com.boog24.extra.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.databinding.DataBindingUtil;

public class AppointmentFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    public static int FRAGMENT = 0;
    FragmentAppointmentBinding binding;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            if(getActivity() != null)
            {
                init();
            }else {

            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment, container, false);
        View v = binding.getRoot();





        return v;
    }


    private void init()
    {
        if(binding.tabLayout.getTabCount() == 0)
        {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.upcoming)));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.completed)));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.cancelled)));
            binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            //Creating our pager adapter
            BookingPager adapter = new BookingPager(getActivity().getSupportFragmentManager(), binding.tabLayout.getTabCount());

            //Adding adapter to pager
            binding.pager.setAdapter(adapter);
            binding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
            //Adding onTabSelectedListener to swipe views
            binding.tabLayout.addOnTabSelectedListener(this);
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        binding.pager.setCurrentItem(tab.getPosition());
        if (tab.getPosition() == 1) {
            FRAGMENT = 1;
        } else if (tab.getPosition() == 2) {
            FRAGMENT = 2;
        } else {
            FRAGMENT = 3;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public static AppointmentFragment newInstance() {
        AppointmentFragment f = new AppointmentFragment();
        return f;
    }
}
