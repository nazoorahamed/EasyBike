package com.example.nazoorahamed.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.zxing.Result;

import java.net.URL;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;

public class BarcodeScaner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int rqCamera=1 ;
    private ZXingScannerView scannerView;
    private String Bike1,Bike2,Bike3,availablestring;
    private boolean isavailable;
    public static String scannerresult;

    public static Firebase available;
    private Firebase Matchbarcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Matchbarcode = new Firebase("https://easy-bike-1f32d.firebaseio.com/AllBikes");








        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkPermission()){
                Toast.makeText(BarcodeScaner.this,"Scan the Barcode!",Toast.LENGTH_LONG).show();

            }else {
                requestPermission();
            }
        }





        Matchbarcode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String> map =dataSnapshot.getValue(Map.class);

                Bike1 = map.get("Bike1");
                Bike2 = map.get("Bike2");
                Bike3 = map.get("Bike3");



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,new String[]{CAMERA},rqCamera);
    }

    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(BarcodeScaner.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    public void onRequestPermissionsResult(int reqCode, String permissions[],int[] grantResult){

        switch (reqCode){
            case rqCamera :
                if (grantResult.length>0){
                    boolean CameraAccepted = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    if(CameraAccepted){
                        Toast.makeText(BarcodeScaner.this,"permission granted !",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(BarcodeScaner.this,"permission Denied!",Toast.LENGTH_LONG).show();
                        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(CAMERA)){
                                displayAlertMSG("You need to allow for both permission",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},rqCamera);
                                                }
                                            }
                                        });
                                return;

                            }
                        }
                    }
                }
                break;
        }

    }

    @Override
    public void onResume(){
        super.onResume();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkPermission()){
                if(scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMSG(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(BarcodeScaner.this)
                .setMessage(message)
                .setPositiveButton("OK",listener)
                .setNegativeButton("Cancel",null)
                .create().show();
    }

    @Override
    public void handleResult(final Result result) {


         scannerresult = result.getText();

        available = new Firebase("https://easy-bike-1f32d.firebaseio.com/Bike/"+scannerresult+"/Available");

        available.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                isavailable =  dataSnapshot.getValue(boolean.class);

                availablestring = dataSnapshot.getValue(String.class);



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        if((scannerresult.equals(Bike1)||scannerresult.equals(Bike2)||scannerresult.equals(Bike3))&& isavailable){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to ride this bike ?");
            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    scannerView.resumeCameraPreview(BarcodeScaner.this);
                }
            });
            builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {



                    Intent intent = new Intent(getApplicationContext(),Rideconfirmation.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                }
            });
            builder.setMessage(scannerresult);
            AlertDialog alert = builder.create();
            alert.show();


        }else {
            scannerView.resumeCameraPreview(BarcodeScaner.this);


        }

    }
}
