package com.boog24.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;

import com.boog24.R;
import com.boog24.activity.AllCategoryActivity;
import com.boog24.activity.LoginActivity;
import com.boog24.activity.SalonListingActivity;
import com.boog24.activity.SearchLocationActivity;
import com.boog24.adapter.HomeCategoriesAdapter;
import com.boog24.adapter.HomeTopRatedAdapter;
import com.boog24.adapter.ImgePagerAdapter;
import com.boog24.custom.Constants;
import com.boog24.databinding.FragmentHomeBinding;
import com.boog24.extra.BaseFragment;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.homescreen.Result;
import com.boog24.presenter.GetHomeDataPresenter;
import com.boog24.view.IGetHomeDataView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends BaseFragment implements IGetHomeDataView, View.OnClickListener {
    LocationManager locationManager;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;

    FragmentHomeBinding binding;

    GetHomeDataPresenter getHomeDataPresenter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
                getHomeDataPresenter.userSignin(getActivity());
            } else {
                NetworkAlertUtility.showNetworkFailureAlert(getActivity());
            }
        }
    }

    public static HomeFragment newInstance() {
        HomeFragment f = new HomeFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View v = binding.getRoot();

        binding.imgSearch.setOnClickListener(this);
        binding.arrowdown.setOnClickListener(this);
        binding.tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllCategoryActivity.class);
                startActivity(intent);
            }
        });

        getHomeDataPresenter = new GetHomeDataPresenter();
        getHomeDataPresenter.setView(this);

        if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
            getHomeDataPresenter.userSignin(getActivity());
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(getActivity());
        }

        if (Prefs.getString(Constants.SharedPreferences_FirstName, "").equals(""))
            binding.tvName.setText(getString(R.string.guest));
        else
            binding.tvName.setText(Prefs.getString(Constants.SharedPreferences_FirstName, ""));

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getLastLocation();

//        if (ActivityCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // Check Permissions Now
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
//                    0);
//        }
//        statusCheck();
//
//        locationManager = (LocationManager) getActivity().getSystemService(
//                Context.LOCATION_SERVICE);
//
//        locationManager.requestLocationUpdates(
//                LocationManager.NETWORK_PROVIDER, 500, 0, this);
        return v;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_search:
                Intent intent = new Intent(getActivity(), SalonListingActivity.class);
                intent.putExtra("id", "");
                startActivity(intent);
                break;

            case R.id.arrowdown:
                Intent intent1 = new Intent(getActivity(), SearchLocationActivity.class);
                startActivity(intent1);
                break;
        }
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    Prefs.putString(Constants.SharedPreferences_latitude, String.valueOf(location.getLatitude()));
                                    Prefs.putString(Constants.SharedPreferences_longitude, String.valueOf(location.getLongitude()));

                                    Geocoder geocoder;
                                    List<Address> addresses;
                                    geocoder = new Geocoder(getActivity(), Locale.getDefault());

                                    try {
                                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                        String city = addresses.get(0).getLocality();
                                        String state = addresses.get(0).getAdminArea();
                                        String country = addresses.get(0).getCountryName();
                                        String postalCode = addresses.get(0).getPostalCode();
                                        String knownName = addresses.get(0).getFeatureName();
                                        Prefs.putString(Constants.SharedPreferences_Address, address);
                                        binding.tvAddress.setText(Prefs.getString(Constants.SharedPreferences_Address, ""));
                                        Log.e("TAG", "onComplete: CURRENT ADDRESS" + address);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    Log.e("TAG", "onComplete: " + location.getLatitude());
//                                    latTextView.setText(location.getLatitude()+"");
//                                    lonTextView.setText(location.getLongitude()+"");
                                }
                            }
                        }
                );
            } else {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!Prefs.getString(Constants.SharedPreferences_Address, "").equalsIgnoreCase(""))
            binding.tvAddress.setText(Prefs.getString(Constants.SharedPreferences_Address, ""));
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }


    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Log.e("TAG", "onLocationResult: " + mLastLocation.getLatitude());
//            latTextView.setText(mLastLocation.getLatitude()+"");
//            lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
                getLastLocation();
            }
        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(
                getResources().getString(R.string.your_gps))
                .setCancelable(false).setPositiveButton(getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int id) {
                        startActivity(new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onGetDetail(Result response) {
        if (response.getStatus() == 200) {

            PagerAdapter pagerAdapter = new ImgePagerAdapter(getActivity(), response.getBannerData());
            binding.viewPager.setAdapter(pagerAdapter);
            binding.viewPager.setClipToPadding(false);
            // set padding manually, the more you set the padding the more you see of prev & next page
            binding.viewPager.setPadding(30, 0, 30, 0);
            // sets a margin b/w individual pages to ensure that there is a gap b/w them
            binding.viewPager.setPageMargin(20);
            binding.indicr.setupWithViewPager(binding.viewPager, true);

            HomeCategoriesAdapter homeCategoriesAdapter = new HomeCategoriesAdapter(getActivity(), response.getCategoryData());
            binding.rcvCategories.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            binding.rcvCategories.setAdapter(homeCategoriesAdapter);


            HomeTopRatedAdapter homeTopRatedAdapter = new HomeTopRatedAdapter(getActivity(), response.getSaloonData());
            binding.rcvTopRated.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            binding.rcvTopRated.setAdapter(homeTopRatedAdapter);


        } else if (response.getStatus() == 406) {

            Prefs.clear();
            startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            getActivity().finish();
        }
    }

//
}
