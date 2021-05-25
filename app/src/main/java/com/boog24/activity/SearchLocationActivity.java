package com.boog24.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.boog24.R;
import com.boog24.adapter.PlacesAutoCompleteAdapter;
import com.boog24.custom.Constants;
import com.boog24.databinding.ActivitySearchLocationBinding;
import com.boog24.extra.BaseActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SearchLocationActivity extends BaseActivity implements PlacesAutoCompleteAdapter.ClickListener{
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;

    ActivitySearchLocationBinding binding;
    private LatLng pickupLocation;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_location);
        binding.setActivity(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        binding.icClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cetPickup.setText("");

            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.lytCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();

            }
        });
        Places.initialize(this, getResources().getString(R.string.google_maps_key));

        binding.cetPickup.addTextChangedListener(filterTextWatcher);
        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(this);
        binding.recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();

    }

    public static void hideKeyboardwithoutPopulate(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void click(Place place) {
        if (!binding.cetPickup.getText().toString().trim().equals("")) {
            binding.cetPickup.setText(place.getAddress());
            pickupLocation = place.getLatLng();
            hideKeyboardwithoutPopulate(this);
            Prefs.putString(Constants.SharedPreferences_Address,place.getAddress());
            Prefs.putString(Constants.SharedPreferences_latitude, String.valueOf(place.getLatLng().latitude));
            Prefs.putString(Constants.SharedPreferences_longitude, String.valueOf(place.getLatLng().longitude));
            finish();
        } else {
            // set address

        }
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                mAutoCompleteAdapter.getFilter().filter(s.toString());
                    if (binding.recyclerView.getVisibility() == View.GONE) {
                        binding.recyclerView.setVisibility(View.VISIBLE);
//                        searchViewVisibility();
                    }
            } else {
                if (binding.recyclerView.getVisibility() == View.VISIBLE) {
                    binding.recyclerView.setVisibility(View.GONE);
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };



    @SuppressLint("MissingPermission")
    private void getLastLocation(){
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
                                    geocoder = new Geocoder(SearchLocationActivity.this, Locale.getDefault());

                                    try {
                                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                        String city = addresses.get(0).getLocality();
                                        String state = addresses.get(0).getAdminArea();
                                        String country = addresses.get(0).getCountryName();
                                        String postalCode = addresses.get(0).getPostalCode();
                                        String knownName = addresses.get(0).getFeatureName();
                                        Prefs.putString(Constants.SharedPreferences_Address,address);
                                        Prefs.putString(Constants.SharedPreferences_latitude, String.valueOf(location.getLatitude()));
                                        Prefs.putString(Constants.SharedPreferences_longitude, String.valueOf(location.getLongitude()));
                                        finish();
                                        Log.e("TAG", "onComplete: CURRENT ADDRESS"+address );
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    Log.e("TAG", "onComplete: "+location.getLatitude() );
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

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Log.e("TAG", "onLocationResult: "+mLastLocation.getLatitude() );
//            latTextView.setText(mLastLocation.getLatitude()+"");
//            lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions()  {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
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

}
