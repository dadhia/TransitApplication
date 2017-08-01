package com.devanlocker.transitapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String ROUTE_NUMBER_MESSAGE = "com.devanlocker.transitapplication.routeNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        mRecyclerView = (RecyclerView) findViewById(R.id.routes_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            ArrayList<Route> routes = LAMetroParser.getRoutes();
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

    public void switchToIndividualRoute(String number) {
        Intent intent = new Intent(this, IndividualRouteActivity.class);
        intent.putExtra(ROUTE_NUMBER_MESSAGE, number);
        startActivity(intent);
    }
}
