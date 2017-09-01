package com.devanlocker.transitapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Stops activity with no google map.  Use this for APIs / Agencies that do
 * not readily provide location data for their stops.
 * As of now, we will not integrate with ArrivalsActivity and we will instead
 * direct users to an ArrivalsActivityNoMap when they select as stop.
 */
public class StopsActivityNoMap extends AppCompatActivity {
    private int mRouteNumber;
    private String mAgencyName;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Stop> stops;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops_no_map);

        Intent intent = getIntent();
        mRouteNumber = intent.getIntExtra(Constants.ROUTE_NUMBER_MESSAGE, 0);
        mAgencyName = intent.getStringExtra(Constants.AGENCY_MESSAGE);

        mRecyclerView = (RecyclerView) findViewById(R.id.stops_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            stops = USCParser.getStops(mRouteNumber);
            mAdapter = new StopAdapter(stops, this);
            mRecyclerView.setAdapter(mAdapter);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void switchToStopArrivals(int stopNumber, LatLng location) {
        Intent intent = new Intent(this, ArrivalsActivityNoMap.class);
        intent.putExtra(Constants.STOP_NUMBER_MESSAGE, stopNumber);
        intent.putExtra(Constants.AGENCY_MESSAGE, mAgencyName);
        intent.putExtra(Constants.ROUTE_NUMBER_MESSAGE, mRouteNumber);
        startActivity(intent);
    }
}
