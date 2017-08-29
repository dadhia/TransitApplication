package com.devanlocker.transitapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

public class ChooseAgency extends AppCompatActivity {
    public static final String AGENCY_MESSAGE = "com.devanlocker.transitapplication.agency";
    private Button mLAMetroBusButton;
    private Button mLAMetroRailButton;
    private Button mUSCShuttleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_agency);

        mLAMetroBusButton = (Button) findViewById(R.id.la_metro_bus_button);
        mLAMetroRailButton = (Button) findViewById(R.id.la_metro_rail_button);
        mUSCShuttleButton = (Button) findViewById(R.id.usc_shuttle_button);
        mLAMetroBusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToRoutesActivity("lametro");
            }
        });
        mLAMetroRailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToRoutesActivity("lametro-rail");
            }
        });
        mUSCShuttleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToRoutesActivity("USC");
            }
        });
    }

    private void switchToRoutesActivity(String agency) {
        Intent intent = new Intent(this, RoutesActivity.class);
        intent.putExtra(AGENCY_MESSAGE, agency);
        startActivity(intent);
    }
}
