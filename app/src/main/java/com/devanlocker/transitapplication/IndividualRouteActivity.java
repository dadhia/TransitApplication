package com.devanlocker.transitapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class IndividualRouteActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private StopAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_route);

        Intent intent = getIntent();
        int routeNumber = new Integer(intent.getStringExtra(MainActivity.ROUTE_NUMBER_MESSAGE));

        mRecyclerView = (RecyclerView) findViewById(R.id.stops_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        try {
            ArrayList<Stop> stops = LAMetroParser.getStops(routeNumber);
            StopAdapter mAdapter = new StopAdapter(stops, this);
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
}
