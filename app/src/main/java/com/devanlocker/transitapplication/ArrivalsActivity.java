package com.devanlocker.transitapplication;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ArrivalsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private static final int REQUEST_CHECK_SETTINGS = 1;
    private Location mLocation;
    private static final String REQUESTING_LOCATION_UPDATES_KEY =
            "com.devanlocker.TransitApplication.REQUESTING_LOCATION_UPDATES";
    private boolean mRequestingLocationUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //basic initialization
        super.onCreate(savedInstanceState);
        //updateValuesFromBundle(savedInstanceState);
        setContentView(R.layout.activity_arrivals);


        //Get the stop number from StopsActivity which should start this activity
        Intent intent = getIntent();
        int routeNumber =
                new Integer(intent.getStringExtra(StopsActivity.STOP_NUMBER_MESSAGE));

        /*
        //initialize mLocationRequest with the necessary parameters/values
        setupLocationRequest();

        //Create a LocationSettingsRequest and verify that we have access to these locations
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                //get location
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                                                                            ArrivalsActivity.this);
                startLocationUpdates();
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch(statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        //Location settings are not satisfactory, requires user intervention
                        //use a dialog
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(ArrivalsActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                            //TODO do we need to do something here?

                        } catch (IntentSender.SendIntentException sendEx){
                            //Ignore error.
                            //TODO maybe add something here
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //no way to fix location settings, don't show dialog
                        break;
                }
            }
        });
        try {
            if(mFusedLocationClient !=null) {
                mFusedLocationClient.getLastLocation().
                        addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                //get last known location.  this may be null in rare cases
                                if (location == null) {
                                    //TODO
                                    //Do something here
                                }
                            }
                        });
            }
        } catch (SecurityException e) {
            //TODO do something
        }
        */

        //create the scrolling list view to show all arrivals at this stop
        mRecyclerView = (RecyclerView) findViewById(R.id.arrivals_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            ArrayList<Arrival> arrivals = LAMetroParser.getArrivals(routeNumber);
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

    }
    /*
    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }
    */

    /*
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
    */

    /*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        super.onSaveInstanceState(outState);
    }
    */
    /*
    private void setupLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }
    */

    /*
    private void startLocationUpdates() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location: locationResult.getLocations()) {
                    //TODO update UI with locations
                }
            }
        };
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        } catch (SecurityException e) {
            //TODO do something here to handle the security exception
        }
    }
    */

    /*
    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
    */

    /*
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        //update value of mRequestingLocationUpdates
        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            mRequestingLocationUpdates =
                    savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY);
        }

        //TODO update UI
        //updateUI();
    }
    */
}
