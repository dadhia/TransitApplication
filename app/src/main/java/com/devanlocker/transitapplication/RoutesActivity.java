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
    public static final String ROUTE_NUMBER_MESSAGE = "com.devanlocker.transitapplication.routeNumber";
    public static final String AGENCY_MESSAGE = "com.devanlocker.transitapplication.agency";
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

    public void switchToIndividualRoute(int number) {
        Intent intent = new Intent(this, StopsActivity.class);
        intent.putExtra(ROUTE_NUMBER_MESSAGE, number);
        intent.putExtra(AGENCY_MESSAGE, mAgencyName);
        startActivity(intent);
    }
}
