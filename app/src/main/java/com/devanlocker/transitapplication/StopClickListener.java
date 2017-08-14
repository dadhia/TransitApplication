package com.devanlocker.transitapplication;

import android.view.View;
import android.widget.TextView;

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
        mStopsActivity.switchToStopArrivals(stopNumber.getText().toString());
    }
}
