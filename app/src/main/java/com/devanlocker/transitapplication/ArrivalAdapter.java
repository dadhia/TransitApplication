package com.devanlocker.transitapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by devan on 8/6/2017.
 */

public class ArrivalAdapter extends RecyclerView.Adapter<ArrivalAdapter.ViewHolder> {
    private ArrayList<Arrival> mDataSet;

    public ArrivalAdapter(ArrayList<Arrival> dataSet) {
        this.mDataSet = dataSet;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.arrival_card_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.routeTextView.setText(new Integer(mDataSet.get(position).getRouteId()).toString());
        holder.minutesTextView.setText(new Integer(mDataSet.get(position).getMinutes()).toString()
                                        + " min");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView routeTextView;
        private TextView minutesTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.routeTextView = (TextView) itemView.findViewById(R.id.arrival_route_textView);
            this.minutesTextView = (TextView) itemView.findViewById(R.id.arrival_minutes_textView);
        }
    }
}
