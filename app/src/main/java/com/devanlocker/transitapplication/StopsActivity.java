package com.devanlocker.transitapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StopsActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    public static final String STOP_NUMBER_MESSAGE = "com.devanlocker.transitapplication.stopNumber";
    public static final String LATITUDE_MESSAGE = "com.devanlocker.transitapplication.latitude";
    public static final String LONGITUDE_MESSAGE = "com.devanlocker.transitapplication.longitude";
    public static final String AGENCY_MESSAGE = "com.devanlocker.transitapplication.agnecy";
    public static final int ACCESS_FINE_LOCATION_REQUEST_CODE = 1;
    private ArrayList<Stop> mStops;
    private int mRouteNumber;
    private GoogleMap mGoogleMap;
    private ArrayList<Marker> mMarkers;
    private String mAgencyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_route);

        Intent intent = getIntent();
        mRouteNumber = intent.getIntExtra(RoutesActivity.ROUTE_NUMBER_MESSAGE, 0);
        mAgencyName = intent.getStringExtra(RoutesActivity.AGENCY_MESSAGE);
        setUpToggleButtons();

        try {
            mStops = LAMetroParser.getStops(mRouteNumber, mAgencyName);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.full_route_map);
        mapFragment.getMapAsync(this);
    }

    private void setUpToggleButtons() {
        ToggleButton showStopsButton = (ToggleButton) findViewById(R.id.showStopsButton);
        showStopsButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setStopsVisible(isChecked);
            }
        });
        showStopsButton.setChecked(true);

        ToggleButton showTrafficButton = (ToggleButton) findViewById(R.id.showTrafficButton);
        showTrafficButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setTrafficVisible(isChecked);
            }
        });
        showTrafficButton.setChecked(false);
    }

    /**
     * Starts the next activity which will display the arrivals for a specific stop.
     * @param stopNumber String
     * @param location LatLng
     */
    public void switchToStopArrivals(int stopNumber, LatLng location) {
        Intent intent = new Intent(this, ArrivalsActivity.class);
        intent.putExtra(STOP_NUMBER_MESSAGE, stopNumber);
        intent.putExtra(LATITUDE_MESSAGE, location.latitude);
        intent.putExtra(LONGITUDE_MESSAGE, location.longitude);
        intent.putExtra(AGENCY_MESSAGE, mAgencyName);
        startActivity(intent);
    }

    /**
     * Prepares our map which is the main functionality of our activity.
     * @param googleMap GoogleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        UiSettings ui = googleMap.getUiSettings();
        ui.setZoomControlsEnabled(true);
        ui.setZoomGesturesEnabled(true);
        googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.map_style)));
        //Add every stop to the map and associate the marker listener with it so we have
        //behavior on click
        if (mStops != null) {
            mMarkers = new ArrayList<Marker>(mStops.size());
            for (int i = 0; i < mStops.size(); i++) {
                Stop stop = mStops.get(i);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(stop.getLatitude(), stop.getLongitude()));
                markerOptions.title(stop.getName());
                Marker marker = googleMap.addMarker(markerOptions);
                marker.setTag(stop.getId());    //associate the stop id with this marker
                mMarkers.add(marker);
            }
            googleMap.setOnMarkerClickListener(this);
        }

        //use mStops to draw a line that shows the route more clearly on the map
        //use a sequence request this time and replace the data in mStops which is no longer needed
        try {
            mStops = LAMetroParser.getStopsSequence(mRouteNumber, mAgencyName);
            PolylineOptions polylineOptions = new PolylineOptions();
            for (int i = 0; i < mStops.size(); i++) {
                polylineOptions.add(new LatLng(mStops.get(i).getLatitude(),
                        mStops.get(i).getLongitude()));
            }
            googleMap.addPolyline(polylineOptions);
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        //Runtime check of ACCESS_FINE_LOCATION
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            String[] requests = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, requests, ACCESS_FINE_LOCATION_REQUEST_CODE);
        } else {
            addUserLocation();
        }
    }

    /**
     * Called whenever a user decides whether or not to grant a permission.
     * @param requestCode int
     * @param permissions String []
     * @param grantResults int []
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == ACCESS_FINE_LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addUserLocation();
            } else {    //permission denied
                if (mStops != null) {
                    int halfway = (int) Math.floor(mStops.size() * 0.5);
                    //move the camera to a somewhat center position on our route
                    //since the user did not want to provide their location
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(
                            new LatLng(mStops.get(halfway).getLatitude(),
                                    mStops.get(halfway).getLongitude())));
                }
            }
        }
    }

    /**
     * Adds the users location to the map with a blue dot (like other Google Maps applications).
     * Focuses camera on the user's location to provide a better experience and show nearby stops.
     * Only call this method after verifying that the user has provided the necessary permissions.
     * Even if user has removed those permissions (ACCESS_FINE_LOCATION), we will still catch
     * SecurityException.
     */
    private void addUserLocation() {
        if (mGoogleMap == null)
            return;
        try {
            FusedLocationProviderClient flp = LocationServices.getFusedLocationProviderClient(this);
            flp.getLastLocation().addOnSuccessListener(this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(
                                    new LatLng(location.getLatitude(), location.getLongitude())));
                        }
                    });
            mGoogleMap.setMyLocationEnabled(true);
        } catch (SecurityException e){}
    }

    /**
     * Sets the visibility of markers that are placed on the map.
     * @param visible boolean
     */
    private void setStopsVisible(boolean visible) {
        if (mMarkers == null)
            return;
        for (int i = 0; i < mMarkers.size(); i++) {
            mMarkers.get(i).setVisible(visible);
        }
    }

    /**
     * Sets the visibility of traffic on the google map.
     * @param visible boolean
     */
    private void setTrafficVisible(boolean visible) {
        if (mGoogleMap == null)
            return;
        mGoogleMap.setTrafficEnabled(visible);
    }

    /**
     * Called whenever a user clicks on a marker on the map.
     * We should launch a new activity.
     * @param marker
     * @return boolean true always
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        switchToStopArrivals((int)marker.getTag(), marker.getPosition());
        return true;
    }

}
