package com.boog24.activity;

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
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boog24.R;
import com.boog24.adapter.PlacesAutoCompleteAdapter;
import com.boog24.adapter.SalonListingAdapter;
import com.boog24.custom.Constants;
import com.boog24.custom.GPSTracker;
import com.boog24.databinding.ActivitySalonListingBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.getSaloons.Result;
import com.boog24.presenter.AddWishlistPresenter;
import com.boog24.presenter.GetSaloonsPresenter;
import com.boog24.view.IAddWishlistView;
import com.boog24.view.IGetSaloonsView;
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

public class SalonListingActivity extends BaseActivity implements IGetSaloonsView, IAddWishlistView, PlacesAutoCompleteAdapter.ClickListener {
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    ActivitySalonListingBinding binding;
    GetSaloonsPresenter getSaloonsPresenter;
    private LatLng pickupLocation;
    String category_id, subcategoryId = "";
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    AddWishlistPresenter addWishlistPresenter;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 2323;
    private static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    String lat;
    String lng;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_salon_listing);
        binding.setActivity(this);

        getSaloonsPresenter = new GetSaloonsPresenter();
        getSaloonsPresenter.setView(this);

        addWishlistPresenter = new AddWishlistPresenter();
        addWishlistPresenter.setView(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        category_id = getIntent().getStringExtra("id");
//        Prefs.putString(Constants.SharedPreferences_latitude, "23.54634");
//        Prefs.putString(Constants.SharedPreferences_longitude, "75.45345");
        if (getIntent().hasExtra("sub_category_id")) {

            subcategoryId = getIntent().getStringExtra("sub_category_id");
            if (NetworkAlertUtility.isConnectingToInternet(SalonListingActivity.this)) {
                getSaloonsPresenter.getSallons(SalonListingActivity.this, getIntent().getStringExtra("id"), getIntent().getStringExtra("sub_category_id"), "", "");
            } else {
                NetworkAlertUtility.showNetworkFailureAlert(SalonListingActivity.this);
            }
        } else {

            subcategoryId = "";
            if (NetworkAlertUtility.isConnectingToInternet(SalonListingActivity.this)) {
                getSaloonsPresenter.getSallons(SalonListingActivity.this, getIntent().getStringExtra("id"), "", "", "");
            } else {
                NetworkAlertUtility.showNetworkFailureAlert(SalonListingActivity.this);
            }
        }


        Places.initialize(this, getResources().getString(R.string.google_maps_key));

//        binding.cetPickup.addTextChangedListener(filterTextWatcher);
        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(this);
        binding.recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
//        registerForContextMenu(binding.tvSort);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, v.getId(), 0, "Yellow");
        menu.add(0, v.getId(), 0, "Gray");
        menu.add(0, v.getId(), 0, "Cyan");
    }

    @Override
    public void onGetDetail(Result response) {

        if (response.getStatus() == 200) {
            binding.recyclerview.setVisibility(View.VISIBLE);
            binding.tvTotalSaloons.setText(getResources().getString(R.string.salons) + " (" + response.getTotalSaloons() + ")");

            SalonListingAdapter salonListingAdapter = new SalonListingAdapter(this, SalonListingActivity.this, response.getSaloonData());
            binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerview.setAdapter(salonListingAdapter);

        } else if (response.getStatus() == 400) {

            binding.tvTotalSaloons.setText(getResources().getString(R.string.salons) + " (0)");
            binding.recyclerview.setVisibility(View.GONE);
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
     /*fun showPopupMenu(
            mBinding: ListItemCompetitionVideosBinding,
            view:ImageView,
            layoutId: Int,
            data: AppliedCompetitionData
        ) {
            //creating a popup menu
            val popup = PopupMenu(mBinding.root.context, view)
            //inflating menu from xml resource
            popup.inflate(layoutId)
            //adding click listener
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.addFeedback -> {
                    }
                    R.id.viewFeedback -> {

                    }
                }
                false
            })
            //displaying the popup
            popup.show()
        }*/

    public void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, binding.tvSort);

        popupMenu.inflate(R.menu.menu_sort_by);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.mostRated:
                        getSortedList("mostrated");
                        break;
                    case R.id.mostViewed:
                        getSortedList("mostviewed");

                        break;

                    case R.id.newListings:
                        getSortedList("newlistings");

                        break;
                    case R.id.highRated:
                        getSortedList("highrated");

                        break;

                }
                return false;
            }
        });
        popupMenu.show();

    }

    private void getSortedList(String sortBy) {
        if (NetworkAlertUtility.isConnectingToInternet(SalonListingActivity.this)) {
            getSaloonsPresenter.getSallons(SalonListingActivity.this, getIntent().getStringExtra("id"), getIntent().getStringExtra("sub_category_id"), "", sortBy);
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(SalonListingActivity.this);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvSort:

                showPopupMenu();
                break;
            case R.id.back:
//                InputMethodManager inputMethodManager =
//                        (InputMethodManager) getSystemService(
//                                Activity.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(
//                        getCurrentFocus().getWindowToken(), 0);
                finish();
                break;
            case R.id.tvSearch:
                if (!binding.cetPickup.getText().toString().equalsIgnoreCase("")) {
                    binding.recyclerView.setVisibility(View.GONE);
//                    Prefs.putString(Constants.SharedPreferences_latitude, String.valueOf(pickupLocation.latitude));
//                    Prefs.putString(Constants.SharedPreferences_longitude, String.valueOf(pickupLocation.longitude));

                    if (NetworkAlertUtility.isConnectingToInternet(SalonListingActivity.this)) {
                        getSaloonsPresenter.getSallons(SalonListingActivity.this, category_id, subcategoryId, binding.cetPickup.getText().toString(), "");
                    } else {
                        NetworkAlertUtility.showNetworkFailureAlert(SalonListingActivity.this);
                    }
                } else {
                    Toast.makeText(this, getResources().getString(R.string.pls_enter_something), Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.imgLocation:
                if (!checkPermissions()) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_ID
                    );
                }
                break;

            case R.id.tvMap:
                if (ContextCompat.checkSelfPermission(SalonListingActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    checkFinePermission();
                } else {
                    try {
                        gps = new GPSTracker(SalonListingActivity.this);
                        // Check if GPS enabled
                        if (gps.canGetLocation()) {
                            Intent intent = new Intent(SalonListingActivity.this, MapViewActivity.class);
                            intent.putExtra("id", getIntent().getStringExtra("id"));
                            startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                        } else {
                            // Can't get location.
                            // GPS or network is not enabled.
                            // Ask user to enable GPS/network in settings.
                            gps.showSettingsAlert();
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
                break;


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFinePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_MULTIPLE_REQUEST);

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_MULTIPLE_REQUEST);
                }
            }
        } else {

        }
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void click(Place place) {
        if (!binding.cetPickup.getText().toString().trim().equals("")) {
            binding.cetPickup.setText(place.getAddress());
            pickupLocation = place.getLatLng();
        } else {
            // set address

        }
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            binding.recyclerView.setVisibility(View.VISIBLE);
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
                                    geocoder = new Geocoder(SalonListingActivity.this, Locale.getDefault());

                                    try {
                                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                        String city = addresses.get(0).getLocality();
                                        String state = addresses.get(0).getAdminArea();
                                        String country = addresses.get(0).getCountryName();
                                        String postalCode = addresses.get(0).getPostalCode();
                                        String knownName = addresses.get(0).getFeatureName();
                                        Prefs.putString(Constants.SharedPreferences_Address, address);
                                        Prefs.putString(Constants.SharedPreferences_latitude, String.valueOf(location.getLatitude()));
                                        Prefs.putString(Constants.SharedPreferences_longitude, String.valueOf(location.getLongitude()));
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
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_ID
            );
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

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
            Log.e("TAG", "onLocationResult: " + mLastLocation.getLatitude());
//            latTextView.setText(mLastLocation.getLatitude()+"");
//            lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };


    public void addToWishlist(String id) {

        if (NetworkAlertUtility.isConnectingToInternet(SalonListingActivity.this)) {
            addWishlistPresenter.addWishlist(SalonListingActivity.this, id);
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(SalonListingActivity.this);
        }

    }

    @Override
    public void onAddedWishlist(com.boog24.modals.addWishlist.Result response) {

        if (response.getStatus() == 200) {
            windowPopUp(this, response.getMessage());
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
