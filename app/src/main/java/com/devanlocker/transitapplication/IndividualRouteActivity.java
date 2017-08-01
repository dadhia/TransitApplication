package com.devanlocker.transitapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IndividualRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_route);

        Intent intent = getIntent();
        String routeNumber = intent.getStringExtra(MainActivity.ROUTE_NUMBER_MESSAGE);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(routeNumber);

    }
}
