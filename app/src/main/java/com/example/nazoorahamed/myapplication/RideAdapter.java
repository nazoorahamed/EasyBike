package com.example.nazoorahamed.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RideAdapter extends ArrayAdapter<Ride> {

    List<Ride> list;
    Context context;
    int resource;

    public RideAdapter(@NonNull Context context, int resource, @NonNull List<Ride> list) {
        super(context, resource, list);

        this.context  = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);

        TextView txt_bike =  view.findViewById(R.id.txtRidebike);
        TextView txt_rideamount =   view.findViewById(R.id.txtAmount);
        TextView txt_ridetime =   view.findViewById(R.id.txtRideTime);
        TextView txt_ridedate =   view.findViewById(R.id.txtRideDate);

        Ride ride = list.get(position);
        txt_bike.setText(ride.getRide_Bike());
        txt_rideamount.setText(ride.getRide_Amount());
        txt_ridetime.setText(ride.getRidetime());


        return view;
    }
}
