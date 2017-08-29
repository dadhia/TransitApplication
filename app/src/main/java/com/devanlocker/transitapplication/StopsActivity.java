package com.devanlocker.transitapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StopsActivity extends AppCompatActivity{
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

        mRecyclerView = (RecyclerView) findViewById(R.id.stops_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            mStops = LAMetroParser.getStops(routeNumber);
            mAdapter = new StopAdapter(mStops, this);
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
}
