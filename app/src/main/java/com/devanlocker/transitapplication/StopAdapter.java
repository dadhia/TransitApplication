package com.devanlocker.transitapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        public ViewHolder(View itemView) {
            super(itemView);
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
        number.setText(new Integer(mDataSet.get(position).getId()).toString());
        name.setText(mDataSet.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }




}
