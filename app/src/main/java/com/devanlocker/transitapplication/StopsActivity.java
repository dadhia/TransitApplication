package com.devanlocker.transitapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StopsActivity extends AppCompatActivity implements OnMapReadyCallback{
    public static final String STOP_NUMBER_MESSAGE = "com.devanlocker.transitapplication.stopNumber";
    public static final String LATITUDE_MESSAGE = "com.devanlocker.transitapplication.latitude";
    public static final String LONGITUDE_MESSAGE = "com.devanlocker.transitapplication.longitude";

    private RecyclerView mRecyclerView;
    private StopAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Stop> mStops;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_route);

        Intent intent = getIntent();
        int routeNumber = new Integer(intent.getStringExtra(RoutesActivity.ROUTE_NUMBER_MESSAGE));

        try {
            mStops = LAMetroParser.getStops(routeNumber);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.full_route_map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Starts the next activity which will display the arrivals for a specific stop.
     * @param stopNumber String
     * @param location LatLng
     */
    public void switchToStopArrivals(String stopNumber, LatLng location) {
        Intent intent = new Intent(this, ArrivalsActivity.class);
        intent.putExtra(STOP_NUMBER_MESSAGE, stopNumber);
        intent.putExtra(LATITUDE_MESSAGE, location.latitude);
        intent.putExtra(LONGITUDE_MESSAGE, location.longitude);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mStops != null) {
            int halfway = (int) Math.floor((double)mStops.size() * 0.5);
            for (int i = 0; i < mStops.size(); i++) {
                Stop stop = mStops.get(i);
                MarkerOptions marker = new MarkerOptions();
                marker.position(new LatLng(stop.getLatitude(), stop.getLongitude()));
                marker.title(stop.getName());
                googleMap.addMarker(marker);

                if (i == 0.5*mStops.size()) {
                    halfway = i;
                }
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(
                                                new LatLng(mStops.get(halfway).getLatitude(),
                                                mStops.get(halfway).getLongitude())));
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(12));

    }
}
