package com.devanlocker.transitapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by devan on 7/31/2017.
 */

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder> {
    private ArrayList<Stop> mDataSet;
    private StopsActivity mStopsActivity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stopNumberTextView;
        public TextView stopNameTextView;
        public View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            stopNumberTextView = (TextView) itemView.findViewById(R.id.textViewStopNumber);
            stopNameTextView = (TextView) itemView.findViewById(R.id.textViewStopName);
        }
    }

    public StopAdapter(ArrayList<Stop> stops, StopsActivity stopsActivity) {
        mDataSet = stops;
        mStopsActivity = stopsActivity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stop_card_layout,
                parent, false);
        v.setOnClickListener(new StopClickListener(mStopsActivity));
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView number = holder.stopNumberTextView;
        TextView name = holder.stopNameTextView;
        Stop stop = mDataSet.get(position);
        number.setText(new Integer(stop.getId()).toString());
        name.setText(mDataSet.get(position).getName());
        holder.view.setTag(new LatLng(stop.getLatitude(), stop.getLongitude()));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
