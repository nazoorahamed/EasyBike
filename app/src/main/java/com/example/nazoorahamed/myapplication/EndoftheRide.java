package com.example.nazoorahamed.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.nazoorahamed.myapplication.RunningScreen.*;

public class EndoftheRide extends AppCompatActivity {

    TextView finalamount,bikeid,bikename,timetaken,yourbalance;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endofthe_ride);


        finalamount = findViewById(R.id.finalamount);
        bikeid = findViewById(R.id.rideid);
        bikename = findViewById(R.id.ridebike);
        timetaken = findViewById(R.id.timetaken);
        yourbalance = findViewById(R.id.yourbalance);
        done = findViewById(R.id.done);


        finalamount.setText(st_Camount);
        bikeid.setText("Ride ID         :"+st_id);
        bikename.setText("Bike ID         :"+st_ridebike);
        timetaken.setText("Ride Duration   :"+st_ridetime);
        yourbalance.setText("Current Balance :"+String.valueOf(bl_credit));


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });




    }
}
