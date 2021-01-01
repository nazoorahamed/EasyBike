package com.example.nazoorahamed.myapplication;

import android.app.Notification;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.concurrent.TimeUnit;

import me.dm7.barcodescanner.core.ViewFinderView;


import static com.example.nazoorahamed.myapplication.RunService.Running_amount;
import static com.example.nazoorahamed.myapplication.RunService.handler;
import static com.example.nazoorahamed.myapplication.RunService.hrs;

import static com.example.nazoorahamed.myapplication.RunService.min;
import static com.example.nazoorahamed.myapplication.RunService.settextString;
import static com.example.nazoorahamed.myapplication.RunService.sec;
import static com.example.nazoorahamed.myapplication.RunService.totalmin;
import static com.example.nazoorahamed.myapplication.RunService.updatetime;
import static com.example.nazoorahamed.myapplication.RunService.runningback;
import static com.example.nazoorahamed.myapplication.BarcodeScaner.*;
import static com.example.nazoorahamed.myapplication.Rideconfirmation.*;


public class RunningScreen extends AppCompatActivity {

    private boolean isPaused,isCanceled;
    public static TextView time,amount;

    private int secint,minint,hrsint;

    private Firebase Rides,noofrides,endride,ridetitle,rideID,ridebike,ridetime,rideamount,yourbalance,nfc,unlockride;
    public static Long intrides;
    private Button stopRide,unlock;
    public static String st_ridetitle,st_id,st_ridebike,st_ridetime,st_urbalance,st_Camount;
    static Long bl_credit;
    static Long nfcint= 1L;

    private boolean stopride;
//    Handler handler = new Handler();

    long starttime=0L,timeinsec=0L,timesapbuffer=0L,updatetimer=0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_screen);
        time = findViewById(R.id.timecount);
        amount =findViewById(R.id.amount);
        stopRide = findViewById(R.id.stopride);


        if(!runningback){

            Rides = new Firebase("https://easy-bike-1f32d.firebaseio.com/Rides");
            noofrides = new Firebase("https://easy-bike-1f32d.firebaseio.com/Noofrides");


            noofrides.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Long Num =(Long) dataSnapshot.getValue();
                    Long NumAfter = Num+1;
                    dataSnapshot.getRef().setValue(NumAfter);
                    intrides =(Long) dataSnapshot.getValue();

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });



            startService(new Intent(this,RunService.class));

            starttime =SystemClock.uptimeMillis();
            handler.postDelayed(updatetime,0);

        }else {


            starttime =SystemClock.uptimeMillis();
            handler.postDelayed(updatetime,0);

        }

        unlock = findViewById(R.id.unlock);
        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                unlockride = new Firebase("https://easy-bike-1f32d.firebaseio.com/Bike/"+scannerresult+"/needunlock");

                unlockride.setValue(1);


            }
        });




        stopRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getApplicationContext(),RunService.class));
                handler.removeCallbacks(updatetime);
                handler.removeCallbacks(RunService.updatetime);

                //Updating the database to see the all rides he go through


                String st_rideid = String.valueOf(intrides);
                String ridetit = "https://easy-bike-1f32d.firebaseio.com/Rides/Ride"+st_rideid+"/";
                ridetitle = new Firebase(ridetit);
                rideID =new Firebase(ridetit+"Ride_ID");
                ridebike =new Firebase(ridetit+"Ride_Bike");
                ridetime =new Firebase(ridetit+"ridetime");
                rideamount = new Firebase(ridetit+"Ride_Amount");


                st_id = "Ride"+st_rideid;
                rideID.setValue(st_id);
                st_ridebike = scannerresult;
                ridebike.setValue(st_ridebike);
                ridetime.setValue(st_ridetime);
                rideamount.setValue(st_Camount);

                bl_credit = int_Credit-(int)Running_amount;
                credit.setValue(bl_credit);

                credit.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int_Credit = (Long)dataSnapshot.getValue();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                available.setValue(true);









                Intent intent = new Intent(getApplicationContext(),EndoftheRide.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();




            }
        });





    }

            //getting the runnable from the Service Class runnable to update the Time and the price in the Activity
    Runnable updatetime = new Runnable() {
        @Override
        public void run() {
            st_ridetime = ""+String.format("%02d",hrs)+":"+String.format("%02d",min)+":"+String.format("%02d",sec);
            time.setText(""+String.format("%02d",hrs)+":"+String.format("%02d",min)+":"+String.format("%02d",sec));
            st_Camount ="Rs. "+Running_amount;
            amount.setText("Rs. "+Running_amount);

            nfc = new Firebase("https://easy-bike-1f32d.firebaseio.com/Bike/"+scannerresult+"/NFC");

            nfc.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    nfcint = (Long) dataSnapshot.getValue();
                    System.out.println(nfcint);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });






            endride = new Firebase("https://easy-bike-1f32d.firebaseio.com/Endride");

            endride.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    stopride =(boolean) dataSnapshot.getValue();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });



            if(stopride){
                stopRide.setEnabled(true);
            }else {
                stopRide.setEnabled(false);
            }
            handler.postDelayed(this,0);

        }

    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
