package com.boog24.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.boog24.R;
import com.boog24.adapter.MainViewPagerAdapter;
import com.boog24.databinding.ActivityMainBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.fragment.AppointmentFragment;
import com.boog24.fragment.HomeFragment;
import com.boog24.fragment.MoreFragment;
import com.boog24.fragment.ProfileFragment;
import com.boog24.presenter.GetCommonDataPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    MainViewPagerAdapter adapter;
    HomeFragment homeFragment;
    AppointmentFragment appointmentFragment;
    ProfileFragment profileFragment;
    MoreFragment moreFragment;
    public ViewPager viewPager;
    GetCommonDataPresenter getCommonDataPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        changeStatusBarColor(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setupViewPager(binding.viewpager);
        OnPageChangeListener(binding.viewpager);

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) binding.navigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(3);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        binding.navigation.setSelectedItemId(R.id.navigation_home);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    binding.viewpager.setCurrentItem(0);
                    return true;
                case R.id.navigation_appointment:
                    binding.viewpager.setCurrentItem(1);
                    return true;
                case R.id.navigation_user:
                    binding.viewpager.setCurrentItem(2);
                    return true;
                case R.id.navigation_more:
                    binding.viewpager.setCurrentItem(3);
                    return true;

            }
            return false;
        }
    };

    private void OnPageChangeListener(ViewPager viewpager) {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        binding.navigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        binding.navigation.setSelectedItemId(R.id.navigation_appointment);
                        break;
                    case 2:
                        binding.navigation.setSelectedItemId(R.id.navigation_user);
                        break;
                    case 3:
                        binding.navigation.setSelectedItemId(R.id.navigation_more);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //ViewPager
    private void setupViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(4);
        homeFragment = new HomeFragment();
        appointmentFragment = new AppointmentFragment();
        profileFragment = new ProfileFragment();
        moreFragment = new MoreFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(appointmentFragment);
        adapter.addFragment(profileFragment);
        adapter.addFragment(moreFragment);

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivityResult: MAIN " );
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if (binding.navigation.getSelectedItemId()==R.id.navigation_home){
            finish();
        }else if (binding.navigation.getSelectedItemId()==R.id.navigation_appointment){
            viewPager.setCurrentItem(0);
        }else if (binding.navigation.getSelectedItemId()==R.id.navigation_user){
            viewPager.setCurrentItem(0);
        }else if (binding.navigation.getSelectedItemId()==R.id.navigation_more){
            viewPager.setCurrentItem(0);
        }
    }
}
