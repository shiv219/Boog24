package com.boog24.fragment.salondetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boog24.R;
import com.boog24.databinding.FragmentAppointmentBinding;
import com.boog24.databinding.FragmentSalonInfoBinding;
import com.boog24.extra.BaseFragment;

import androidx.databinding.DataBindingUtil;

public class SalonInfoFragment extends BaseFragment {

    FragmentSalonInfoBinding binding;
    public static int FRAGMENT = 0;
    String name,about;
    public SalonInfoFragment(String name,String about) {
        super();
        this.name=name;
        this.about=about;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_salon_info, container, false);
        View v = binding.getRoot();

        binding.tvname.setText(name);
        binding.tvdetail.setText(about);

        return v;
    }

}
