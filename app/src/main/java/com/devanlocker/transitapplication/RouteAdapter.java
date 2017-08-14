package com.devanlocker.transitapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by devan on 7/5/2017.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder>{
    private ArrayList<Route> mDataSet;
    private RoutesActivity mRoutesActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewRouteNumber;
        public TextView textViewRouteDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewRouteNumber =
                    (TextView) itemView.findViewById(R.id.textViewRouteNumber);
            this.textViewRouteDescription =
                    (TextView) itemView.findViewById(R.id.textViewRouteDescription);
        }
    }

    //Suitable constructor depending on dataset
    public RouteAdapter(ArrayList<Route> myDataSet, RoutesActivity routesActivity) {
        mDataSet = myDataSet;
        mRoutesActivity = routesActivity;
    }

    //Create new views
    //invoked by the layout manager
    @Override
    public RouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create a new view
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.route_card_layout, parent, false);
        view.setOnClickListener(new RouteClickListener(mRoutesActivity));
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //replace the contents of a ViewHolder
    //invoked by layout manager
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //get element from your dataset at that position
        //replace the contents of view with that element
        TextView textViewRouteNumber = holder.textViewRouteNumber;
        TextView textViewRouteDescription = holder.textViewRouteDescription;
        textViewRouteNumber.setText(new Integer(mDataSet.get(position).getNumber()).toString());
        textViewRouteDescription.setText(mDataSet.get(position).getDecription());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
