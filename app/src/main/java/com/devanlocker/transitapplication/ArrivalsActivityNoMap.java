package com.devanlocker.transitapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

public class ArrivalsActivityNoMap extends AppCompatActivity {
    private String mAgencyName;
    private int mStopNumber;
    private int mRouteNumber;
    private EditText mMultiLineText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrivals_no_map);

        Intent intent = getIntent();
        mAgencyName = intent.getStringExtra(Constants.AGENCY_MESSAGE);
        mStopNumber = intent.getIntExtra(Constants.STOP_NUMBER_MESSAGE, 0);
        mRouteNumber = intent.getIntExtra(Constants.ROUTE_NUMBER_MESSAGE, 0);

        mMultiLineText = (EditText) findViewById(R.id.arrivals_edit_text);

        try {
            mMultiLineText.setText(USCParser.getArrivals(mRouteNumber, mStopNumber));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
