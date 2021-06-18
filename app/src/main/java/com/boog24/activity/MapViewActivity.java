package com.boog24.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boog24.R;
import com.boog24.adapter.PlacesAutoCompleteAdapter;
import com.boog24.custom.GPSTracker;
import com.boog24.databinding.ActivityMapviewBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.getSaloons.Result;
import com.boog24.modals.getSaloons.SaloonDatum;
import com.boog24.presenter.GetSaloonsPresenter;
import com.boog24.view.IGetSaloonsView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;
import java.util.Locale;

public class MapViewActivity extends BaseActivity implements OnMapReadyCallback , IGetSaloonsView,PlacesAutoCompleteAdapter.ClickListener {
    LocationManager locationManager;
    private GoogleMap mMap;
    double lat;
    double lng;
    GPSTracker gpsTracker;
    View mapView;
    ActivityMapviewBinding binding;
    GetSaloonsPresenter getSaloonsPresenter;
    String salonName;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_mapview);
        binding.setActivity(this);

        getSaloonsPresenter =new GetSaloonsPresenter();
        getSaloonsPresenter.setView(this);


//        if (NetworkAlertUtility.isConnectingToInternet(MapViewActivity.this)) {
////            getSaloonsPresenter.getSallons(MapViewActivity.this, getIntent().getStringExtra("id"),"");
//            getSaloonsPresenter.getSallons(MapViewActivity.this, "","");
//        } else {
//            NetworkAlertUtility.showNetworkFailureAlert(MapViewActivity.this);
//        }


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
//                LOCATION_REFRESH_DISTANCE, mLocationListener);

        binding.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (NetworkAlertUtility.isConnectingToInternet(MapViewActivity.this)) {
////            getSaloonsPresenter.getSallons(MapViewActivity.this, getIntent().getStringExtra("id"),"");
//                    binding.recyclerView.setVisibility(View.GONE);
//                    getSaloonsPresenter.getSallonsByLat(MapViewActivity.this, String.valueOf(lat),String.valueOf(lng));
//                } else {
//                    NetworkAlertUtility.showNetworkFailureAlert(MapViewActivity.this);
//                }
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);



        Places.initialize(this, getResources().getString(R.string.google_maps_key));

        binding.cetPickup.addTextChangedListener(filterTextWatcher);
        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(this);
        binding.recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();

        binding.recyclerView.setVisibility(View.GONE);
    }

    @SuppressLint("NewApi")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null)
            mMap.setMyLocationEnabled(true);

        gpsTracker = new GPSTracker(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (gpsTracker.canGetLocation()){

                    lat = gpsTracker.getLatitude();
                    lng = gpsTracker.getLongitude();



                    Log.e("TAG", "run: "+lat +" "+ lng );

                    LatLng india = new LatLng(lat, lng);
//                    mMap.addMarker(new MarkerOptions().position(india).title(gpsTracker.get));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india, 15));

//                    binding.cetPickup.setText(getCompleteAddressString(lat,lng));
                    if (NetworkAlertUtility.isConnectingToInternet(MapViewActivity.this)) {
//            getSaloonsPresenter.getSallons(MapViewActivity.this, getIntent().getStringExtra("id"),"");
                        getSaloonsPresenter.getSallonsByLat(MapViewActivity.this, String.valueOf(lat), String.valueOf(lng), getIntent().getStringExtra("id"), "");
                    } else {
                        NetworkAlertUtility.showNetworkFailureAlert(MapViewActivity.this);
                    }

                }else{

                    gpsTracker.showSettingsAlert();
                }



            }
        }, 1000);


        if (mapView != null &&  mapView.findViewById(Integer.parseInt("1")) != null ) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the view
            View locationCompass = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("5"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationCompass.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            layoutParams.setMargins(30, 160,0, 0); // 160 la truc y , 30 la  truc x
        }

        if (mMap != null)
            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
//                    lat = mMap.getCameraPosition().target.latitude;
//                    lng = mMap.getCameraPosition().target.longitude;
//
//                    Log.e("TAG", "run: "+lat +" "+ lng );
//
//                    binding.textHeaderALA.setText(getCompleteAddressString(lat,lng));
                }
            });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {


            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Intent intent =new Intent(MapViewActivity.this,SalonDetailActivity.class);
                intent.putExtra("salonId",marker.getSnippet());
                startActivity(intent);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e("TAG", "onMarkerClick: "+marker.getTitle() );
                salonName=  marker.getTitle();
                return false;
            }
        });


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file infowindowlayout.xml
                View v = getLayoutInflater().inflate(R.layout.infowindowlayout, null);

                LatLng latLng = arg0.getPosition();

                ImageView im = (ImageView) v.findViewById(R.id.imageView1);
                TextView tv1 = (TextView) v.findViewById(R.id.textView1);
                TextView tv2 = (TextView) v.findViewById(R.id.textView2);
                TextView tvCount = (TextView) v.findViewById(R.id.tvCount);
                TextView tvAddress = (TextView) v.findViewById(R.id.tvAddress);
                String title=arg0.getTitle();
                String informations=arg0.getSnippet();

                SaloonDatum infoWindowData = (SaloonDatum) arg0.getTag();



                tv1.setText(infoWindowData.getSaloonName());
                tv2.setText(infoWindowData.getSaloonRatings());
                tvAddress.setText(infoWindowData.getSaloonAddress());
                tvCount.setText("("+salonName+")");

//                if(onMarkerClick(arg0)==true && markerclicked==1){
                    im.setImageResource(R.drawable.star);
//                }


                return v;

            }
        });
    }
    private Marker Somewhere;
    private int markerclicked;

    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(Somewhere))
        {
            markerclicked=1;
            return true;
        }
        return false;
    }



    @SuppressLint("LongLogTag")
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    public void onGetDetail(Result response) {

        if (response.getStatus()==200){

            binding.tvSalons.setText(getResources().getString(R.string.salons)+" ("+response.getSaloonData().size()+")");
            for(int i=0;i<response.getSaloonData().size();i++){

                // Getting the latitude of the i-th location
                lat = Double.parseDouble(response.getSaloonData().get(i).getLatitude());

                // Getting the longitude of the i-th location
                lng =Double.parseDouble(response.getSaloonData().get(i).getLongitude());

                // Drawing marker on the map
                drawMarker(new LatLng((lat), (lng)),response.getSaloonData(),response.getSaloonData().get(i).getSaloonAddress(),response.getSaloonData().get(i).getSaloonName(),response.getSaloonData().get(i).getSaloonRatings(), String.valueOf(response.getSaloonData().size()),response.getSaloonData().get(i).getUserId());
            }
        }else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }else if (response.getStatus()==400){
            binding.tvSalons.setText(getResources().getString(R.string.salons)+" (0)");
        }
            else {
            binding.tvSalons.setText(getResources().getString(R.string.salons)+" (0)");
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Failure")
                    .setMessage(response.getMessage())
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show().setCanceledOnTouchOutside(false);
        }

    }

    private void drawMarker(LatLng point, List<SaloonDatum> saloonData,String address,String name,String rating, String count, String id){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);


        SaloonDatum saloonDatum=new SaloonDatum();
        saloonDatum.setSaloonName(name);
        saloonDatum.setSaloonRatings(rating);
        saloonDatum.setSaloonAddress(address);
        // Adding marker on the Google Map
      Marker m=  mMap.addMarker(markerOptions.title(count).position(point).snippet(id));
      m.setTag(saloonDatum);
      m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
    }



    @Override
    public Context getContext() {
        return null;
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
    public void click(Place place) {
        if (!binding.cetPickup.getText().toString().trim().equals("")) {
            binding.cetPickup.setText(place.getAddress());
            binding.recyclerView.setVisibility(View.GONE);
            lat = place.getLatLng().latitude;
            lng = place.getLatLng().longitude;
            LatLng india = new LatLng(lat, lng);
//                    mMap.addMarker(new MarkerOptions().position(india).title(gpsTracker.get));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india, 15));
            if (NetworkAlertUtility.isConnectingToInternet(MapViewActivity.this)) {
//            getSaloonsPresenter.getSallons(MapViewActivity.this, getIntent().getStringExtra("id"),"");
                binding.recyclerView.setVisibility(View.GONE);
                getSaloonsPresenter.getSallonsByLat(MapViewActivity.this, String.valueOf(lat), String.valueOf(lng), "", "");
            } else {
                NetworkAlertUtility.showNetworkFailureAlert(MapViewActivity.this);
            }
        } else {
            // set address

        }
    }
}
