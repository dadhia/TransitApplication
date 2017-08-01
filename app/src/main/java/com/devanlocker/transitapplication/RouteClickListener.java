package com.devanlocker.transitapplication;

import android.view.View;
import android.widget.TextView;

/**
 * Created by devan on 7/23/2017.
 */

public class RouteClickListener implements View.OnClickListener {
    private TextView mRouteNumberTextView;
    private MainActivity mMainActivity;

    public RouteClickListener(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        TextView routeNumber = (TextView)v.findViewById(R.id.textViewRouteNumber);
        String number = routeNumber.getText().toString();
        mMainActivity.switchToIndividualRoute(number);
    }
}
