package com.example.nazoorahamed.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RetriveRideHistory extends AppCompatActivity {

    public static ListView retrivelist;

    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<String> list;
    ArrayAdapter <String> adapter;

    Ride ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_ride_history);


        ride = new Ride();
        retrivelist = findViewById(R.id.ridehistory);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Rides");

        list = new ArrayList<>();


        adapter = new ArrayAdapter<String>(this,R.layout.ride_info,R.id.ride_info,list);



        //Update Ride History from the Firebase
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){

                    ride = ds.getValue(Ride.class);
                    list.add("Ride ID :"+ride.getRide_ID().toString());
                    list.add("Ride Bike :"+ride.getRide_Bike());
                    list.add("Ride Duration :"+ride.getRidetime());
                    list.add("Ride Amount :"+ride.getRide_Amount());
                    list.add("*********************************************************");

                }

                retrivelist.setAdapter(adapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
