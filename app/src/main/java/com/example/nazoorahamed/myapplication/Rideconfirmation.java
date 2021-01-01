package com.example.nazoorahamed.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import static com.example.nazoorahamed.myapplication.BarcodeScaner.*;


public class Rideconfirmation extends AppCompatActivity {
    Firebase nfc;
    static NumberPicker hrs =null ;
    TextView txt;
    Button bt_confirm;
    static Firebase credit,barcode;
     static Long int_Credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rideconfirmation);


        hrs = findViewById(R.id.np_Hrs);
        hrs.setMaxValue(12);
        hrs.setMinValue(1);
        hrs.setWrapSelectorWheel(false);


        credit = new Firebase("https://easy-bike-1f32d.firebaseio.com/credit_Balance");

        credit.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int_Credit = (Long)dataSnapshot.getValue();
                System.out.println(int_Credit);
                txt = findViewById(R.id.balance_credit);
                txt.setText(String.format(String.valueOf(int_Credit)));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        barcode = new Firebase("https://easy-bike-1f32d.firebaseio.com/Bike/"+scannerresult+"/barcode");





        //Here we are requesting from the user to select the hour package for checking whether he has enough money to ride the bike
        //Still some Security validations are need to be done.

        //Also here we are facing HCI Issues While letting the user to select the hour package and how we solve this,
        //Checking the Current credits of the user whether he can able to go for a ride
        bt_confirm = findViewById(R.id.bt_confirmride);

        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int current_am =hrs.getValue();
                int amount = current_am*80;
                System.out.println(int_Credit);
                System.out.println("Hrs  :"+amount);
               if( amount<int_Credit){
                   barcode.setValue(1);
                   System.out.println(int_Credit);
                   available.setValue(false);
                   Intent intent = new Intent(getApplicationContext(),RunningScreen.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                   finish();

               }else {
                   Toast.makeText(Rideconfirmation.this,"No Credit, add Credits to Continue",Toast.LENGTH_LONG).show();
               }

            }
        });


    }



}
