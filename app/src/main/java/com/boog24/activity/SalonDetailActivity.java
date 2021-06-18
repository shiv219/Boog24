package com.boog24.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.adapter.ImgePagerAdapter;
import com.boog24.adapter.SalonDetailHeaderAdapter;
import com.boog24.custom.Constants;
import com.boog24.databinding.ActivitySalonDetailBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.fragment.salondetail.RatingFragment;
import com.boog24.fragment.salondetail.SalonInfoFragment;
import com.boog24.modals.CommonOffset;
import com.boog24.modals.getSaloonDetail.Result;
import com.boog24.modals.getSaloonDetail.SalonService;
import com.boog24.modals.getSaloonDetail.SaloonData;
import com.boog24.presenter.AddWishlistPresenter;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.presenter.GetSaloonDetailPresenter;
import com.boog24.view.IAddWishlistView;
import com.boog24.view.ICommonView;
import com.boog24.view.IGetSaloonDetailView;
import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SalonDetailActivity extends BaseActivity implements IGetSaloonDetailView, IAddWishlistView, ICommonView {

    ActivitySalonDetailBinding binding;
    String salonId;
    GetSaloonDetailPresenter getSaloonDetailPresenter;
    String salonName, about;
    JSONArray jsonArray = new JSONArray();
    AddWishlistPresenter addWishlistPresenter;
    GetCommonDataPresenter getCommonDataPresenter;
    public ArrayList<SalonService> arrayList = new ArrayList<>();
    private List timeSlots = new ArrayList<SaloonData.SalonTimeSlot>();
    SalonService salonService = new SalonService();
    String latitude = "", longitude = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_salon_detail);
        binding.setActivity(this);


        MyApplication.getInstance().getSession().setData("");
        getSaloonDetailPresenter = new GetSaloonDetailPresenter();
        getSaloonDetailPresenter.setView(this);

        addWishlistPresenter = new AddWishlistPresenter();
        addWishlistPresenter.setView(this);


        getCommonDataPresenter = new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);

        salonId = getIntent().getStringExtra("salonId");
//        if (NetworkAlertUtility.isConnectingToInternet(SalonDetailActivity.this)) {
//            getSaloonDetailPresenter.userSignin(SalonDetailActivity.this,salonId);
//        } else {
//            NetworkAlertUtility.showNetworkFailureAlert(SalonDetailActivity.this);
//        }


        binding.tvMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + salonName + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Prefs.getString(Constants.SharedPreferences_loginKey, "").equalsIgnoreCase("")) {
                    windowPopUpForLogin(SalonDetailActivity.this, getResources().getString(R.string.pls_login_first));

                } else {
                    if (binding.ivHeart.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.heart_active).getConstantState())
                        binding.ivHeart.setImageDrawable(getResources().getDrawable(R.drawable.heart));
                    else
                        binding.ivHeart.setImageDrawable(getResources().getDrawable(R.drawable.heart_active));
                    if (NetworkAlertUtility.isConnectingToInternet(SalonDetailActivity.this)) {
                        addWishlistPresenter.addWishlist(SalonDetailActivity.this, salonId);
                    } else {
                        NetworkAlertUtility.showNetworkFailureAlert(SalonDetailActivity.this);
                    }
                }

            }
        });

        binding.tvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Prefs.getString(Constants.SharedPreferences_loginKey, "").equalsIgnoreCase("")) {
                    windowPopUpForLogin(SalonDetailActivity.this, getResources().getString(R.string.pls_login_first));
                } else {
                    jsonArray = new JSONArray();
                    for (int i = 0; i < arrayList.size(); i++) {

                        for (int j = 0; j < arrayList.get(i).getServices().size(); j++) {

                            if (arrayList.get(i).getServices().get(j).getSelected()) {
                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("name", arrayList.get(i).getServices().get(j).getServiceName());
                                    jsonObject.put("price", arrayList.get(i).getServices().get(j).getServicePrice());
                                    jsonObject.put("hour", arrayList.get(i).getServices().get(j).getHour());
                                    jsonObject.put("minutes", arrayList.get(i).getServices().get(j).getMinutes());
                                    jsonObject.put("service_id", arrayList.get(i).getServices().get(j).getServiceId());
                                    jsonArray.put(jsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        MyApplication.getInstance().getSession().setData(jsonArray.toString());
                    }
                    if (jsonArray.length() > 0) {
                        Intent intent = new Intent(SalonDetailActivity.this, ChooseEmployeesActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST", (Serializable) arrayList);
                        intent.putExtra("BUNDLE", args);
                        intent.putExtra("salonId", salonId);
                        intent.putExtra("array", jsonArray.toString());
                        intent.putExtra("from", "");
                        startActivity(intent);
                    } else {
                        Toast.makeText(SalonDetailActivity.this, "Please Select at least one Service", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        setFragment(new SalonInfoFragment(salonName, about, timeSlots));
                        break;
                    case 1:
                        setFragment(new RatingFragment(salonId));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.cointener, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        //No call for super(). Bug on API Level > 11.
//    }

    @Override
    public void onGetDetail(Result response) {
        if (response.getStatus() == 200) {


            salonName = response.getSaloonData().getShopname();
            about = response.getSaloonData().getSaloonInfo();
            timeSlots = response.getSaloonData().getSalonTimeSlotList();

            if (response.getSaloonData().getWishlistStatus().equalsIgnoreCase("1")) {
                binding.ivHeart.setImageDrawable(getResources().getDrawable(R.drawable.heart_active));
            } else {
                binding.ivHeart.setImageDrawable(getResources().getDrawable(R.drawable.heart));
            }

            setFragment(new SalonInfoFragment(salonName, about, timeSlots));
            binding.tabs.getTabAt(0).select();

            binding.tvName.setText(response.getSaloonData().getShopname());
            binding.tvAddress.setText(response.getSaloonData().getShopAddress());
            binding.ratingBar.setRating((int) Float.parseFloat(response.getSaloonData().getSaloonRatings()));
            binding.tvRating.setText(response.getSaloonData().getSaloonRatings());
            binding.tvTotal.setText("(" + response.getSaloonData().getSaloonRatingsUsers() + ")");

            latitude = response.getSaloonData().getLatitude();
            longitude = response.getSaloonData().getLongitude();
            PagerAdapter pagerAdapter = new ImgePagerAdapter(this, response.getSaloonData().getSalonImages(), "detail");
            binding.viewPager.setAdapter(pagerAdapter);
            binding.indicr.setupWithViewPager(binding.viewPager, true);


            arrayList = new ArrayList<>();
            arrayList.addAll(response.getSaloonData().getSalonServices());


            if (arrayList.size() > 0) {

                if (!MyApplication.getInstance().getSession().getData().isEmpty()) {

                    try {
                        JSONArray jsonArray = new JSONArray(MyApplication.getInstance().getSession().getData());


                        for (int j = 0; j < arrayList.size(); j++) {
                            for (int i = 0; i < jsonArray.length(); i++) {

                                for (int k = 0; k < arrayList.get(j).getServices().size(); k++) {

                                    if (jsonArray.getJSONObject(i).getString("service_id").equalsIgnoreCase(arrayList.get(j).getServices().get(k).getServiceId())) {
                                        Log.e("TAG", "onResume: " + jsonArray.getJSONObject(i).getString("service_id"));
                                        arrayList.get(j).getServices().get(k).setSelected(true);


                                    } else {
                                        Log.e("TAG", "onResume:ELSE " + jsonArray.getJSONObject(i).getString("service_id"));

//                                        if ()
//                                        arrayList.get(j).getServices().get(k).setSelected(false);
                                    }
                                }
                            }
                        }


//                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {

                    for (int j = 0; j < arrayList.size(); j++) {

                        for (int k = 0; k < arrayList.get(j).getServices().size(); k++) {
                            arrayList.get(j).getServices().get(k).setSelected(false);
                        }
                    }

                }
            }


            Log.e("TAG", "onGetDetail: " + arrayList.toString());
            SalonDetailHeaderAdapter headerAdapter = new SalonDetailHeaderAdapter(this, arrayList);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            binding.recyclerView.setAdapter(headerAdapter);
            binding.recyclerView.setNestedScrollingEnabled(true);


        } else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
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

//    public void refresh(ArrayList<SalonService> arrayLists){
//
//        arrayList=new ArrayList<>();
//        Log.e("TAG", "refresh before: " + arrayLists.get(0).getServices().get(0).getSelected());
////        arrayList=arrayLists;
//        arrayList.addAll(arrayLists);
//        Log.e("TAG", "refresh after: " + arrayList.get(0).getServices().get(0).getSelected());
////        binding.recyclerView.getAdapter().notifyDataSetChanged();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkAlertUtility.isConnectingToInternet(SalonDetailActivity.this)) {
            getSaloonDetailPresenter.userSignin(SalonDetailActivity.this, salonId);
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(SalonDetailActivity.this);
        }

//            Log.e("TAG", "ON RESUME after: " + arrayListNew.get(1).getServices().get(0).getSelected());
//            SalonDetailHeaderAdapter headerAdapter = new SalonDetailHeaderAdapter(this, arrayListNew);
//            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//            binding.recyclerView.setAdapter(headerAdapter);
//            binding.recyclerView.setNestedScrollingEnabled(true);

    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
//            getSupportFragmentManager().popBackStack();
//        } else {
        finish();
//        }
    }

    @Override
    public Context getContext() {
        return null;
    }


    @Override
    public void onAddedWishlist(com.boog24.modals.addWishlist.Result response) {
        windowPopUp(this, response.getMessage());
    }

    @Override
    public void onGetDetail(CommonOffset response) {
        if (response.getStatus() == 200) {

        } else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
}
