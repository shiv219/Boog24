package com.boog24.fragment.salondetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.boog24.R;
import com.boog24.databinding.FragmentSalonInfoBinding;
import com.boog24.extra.BaseFragment;
import com.boog24.modals.getSaloonDetail.SaloonData;

import java.util.ArrayList;
import java.util.List;

public class SalonInfoFragment extends BaseFragment {

    FragmentSalonInfoBinding binding;
    public static int FRAGMENT = 0;
    String name, about;
    TimeSlotsAdapter adapter;
    private List timeSlots = new ArrayList<SaloonData.SalonTimeSlot>();

    public SalonInfoFragment(String name, String about, List timeSlots) {
        super();
        this.name = name;
        this.about = about;
        this.timeSlots = timeSlots;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_salon_info, container, false);
        View v = binding.getRoot();

        binding.tvname.setText(name);
        binding.tvdetail.setText(about);
        setTimeSlots();
        return v;
    }

    private void setTimeSlots() {
        adapter = new TimeSlotsAdapter(timeSlots);
        binding.rvTimeSlot.setAdapter(adapter);
    }

}
