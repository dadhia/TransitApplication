package com.devanlocker.transitapplication;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ArrivalsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private double mLatitude, mLongitude;
    private int mRouteNumber;
    private String mAgencyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //basic initialization
        super.onCreate(savedInstanceState);
        //updateValuesFromBundle(savedInstanceState);
        setContentView(R.layout.activity_arrivals);

        //Get the stop number, latitude, and longitude from intent
        Intent intent = getIntent();
        mRouteNumber = intent.getIntExtra(Constants.STOP_NUMBER_MESSAGE, 0);
        mLatitude = intent.getDoubleExtra(Constants.LATITUDE_MESSAGE, 0.0);
        mLongitude = intent.getDoubleExtra(Constants.LONGITUDE_MESSAGE, 0.0);
        mAgencyName = intent.getStringExtra(Constants.AGENCY_MESSAGE);



        //create the scrolling list view to show all arrivals at this stop
        mRecyclerView = (RecyclerView) findViewById(R.id.arrivals_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            ArrayList<Arrival> arrivals = LAMetroParser.getArrivals(mRouteNumber, mAgencyName);
            mAdapter = new ArrivalAdapter(arrivals);
            mRecyclerView.setAdapter(mAdapter);
        } catch (InterruptedException e) {
            //TODO
            //Some sort of error message here
            e.printStackTrace();
        } catch (ExecutionException e) {
            //TODO
            //Some sort of error message here
            e.printStackTrace();
        }
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.single_stop_map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(mLatitude, mLongitude));
        markerOptions.title(new Integer(mRouteNumber).toString());
        googleMap.addMarker(markerOptions);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLatitude, mLongitude)));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.map_style)));
    }
}
