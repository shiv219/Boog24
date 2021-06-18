package com.boog24.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.boog24.R;
import com.boog24.activity.AboutUsActivity;
import com.boog24.activity.ContactUsActivity;
import com.boog24.databinding.FragmentMoreBinding;
import com.boog24.extra.BaseFragment;

public class MoreFragment extends BaseFragment implements View.OnClickListener {

    FragmentMoreBinding binding;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        View v = binding.getRoot();

        binding.tvImpessum.setOnClickListener(this);
        binding.tvContactus.setOnClickListener(this);
        binding.tvAgb.setOnClickListener(this);
        binding.tvdate.setOnClickListener(this);
        binding.tvUser.setOnClickListener(this);
        binding.tvAbout.setOnClickListener(this);

        return v;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvImpessum:
                Intent intent3=new Intent(getActivity(), AboutUsActivity.class);
                intent3.putExtra("what","impessum");
                startActivity(intent3);
            break;

            case R.id.tvAgb:
                Intent intent4=new Intent(getActivity(), AboutUsActivity.class);
                intent4.putExtra("what","agb");
                startActivity(intent4);
                break;

            case R.id.tvdate:
                Intent intent6=new Intent(getActivity(), AboutUsActivity.class);
                intent6.putExtra("what","date");
                startActivity(intent6);
                break;

            case R.id.tvUser:
                Intent intent7 = new Intent(getActivity(), AboutUsActivity.class);
                intent7.putExtra("what", "user");
                startActivity(intent7);
                break;

            case R.id.tvContactus:
                Intent intent5 = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent5);
                break;

            case R.id.tvAbout:
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                intent.putExtra("what", "about");
                startActivity(intent);
                break;
        }
    }

}
