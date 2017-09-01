package com.devanlocker.transitapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RoutesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mAgencyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        Intent intent = getIntent();
        mAgencyName = intent.getStringExtra(ChooseAgency.AGENCY_MESSAGE);

        mRecyclerView = (RecyclerView) findViewById(R.id.routes_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            ArrayList<Route> routes = null;
            if (mAgencyName.equals("USC")) {
                routes = USCParser.getRoutes();
            } else {
                routes = LAMetroParser.getRoutes(mAgencyName);
            }

            mAdapter = new RouteAdapter(routes, this);
            mRecyclerView.setAdapter(mAdapter);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches to either a StopsActivity or a StopsActivityNoMap.
     * @param number int
     */
    public void switchToStopsActivity(int number) {
        //Determine which kind of stops activity to switch to based on agency
        Intent intent = null;
        if (mAgencyName.equals("USC"))
            intent = new Intent(this, StopsActivityNoMap.class);
        else
            intent = new Intent(this, StopsActivity.class);

        //put extras and start
        intent.putExtra(Constants.ROUTE_NUMBER_MESSAGE, number);
        intent.putExtra(Constants.AGENCY_MESSAGE, mAgencyName);
        startActivity(intent);
    }
}
