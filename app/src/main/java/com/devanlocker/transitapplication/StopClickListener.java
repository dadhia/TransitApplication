package com.devanlocker.transitapplication;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by devan on 8/7/2017.
 */

public class StopClickListener implements View.OnClickListener {
    private StopsActivity mStopsActivity;

    public StopClickListener(StopsActivity stopsActivity) {
        mStopsActivity = stopsActivity;
    }

    @Override
    public void onClick(View v) {
        TextView stopNumber = (TextView)v.findViewById(R.id.textViewStopNumber);
        //retrieve additional data that was associated with this text view
        LatLng location = (LatLng) v.getTag();
        mStopsActivity.switchToStopArrivals(new Integer(stopNumber.getText().toString()),
                                            location);
    }
}
