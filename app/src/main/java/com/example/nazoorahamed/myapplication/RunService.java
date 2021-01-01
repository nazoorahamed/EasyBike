package com.example.nazoorahamed.myapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.SettingInjectorService;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import static com.example.nazoorahamed.myapplication.BarcodeScaner.scannerresult;
import static com.example.nazoorahamed.myapplication.RunningScreen.time;


/**
 * Created by nazoorahamed on 3/30/18.
 */

public class RunService extends Service {
    private boolean isPaused,isCanceled;
   // private TextView time;
    public static int sec,min,millisec,hrs,totalmin;
    private static Firebase Rides,noofrides,timesec,timemin,timehrs,nfc;
    private int intrides;

    public static double Running_amount;

    public static String settextString="";

    static Handler handler = new Handler();

    MediaPlayer player;

     public static long starttime=0L,timeinsec=0L,timesapbuffer=0L,updatetimer=0L;
     public static boolean runningback=false;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    //This Run Service class is mainly created for HCI Issues where the user will face while in the Running Screen
    //THis Run Service will run the foreground and after closing the app run in the Background Services
    //SO Even the user close the app the running data will still continue to  run ! So there will be no interruption in Ride while Riding

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent intent1 = new Intent(this, RunningScreen.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification noti = new Notification.Builder(getApplicationContext())
                .setContentTitle("EasyBike")
                .setContentText("Your Ride is Running Now !")
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent)
                .build();




        startForeground(1234, noti);

        runmenu();
        runningback =true;
        starttime =SystemClock.uptimeMillis();
        handler.postDelayed(updatetime,0);

        player =  MediaPlayer.create(this,Settings.System.DEFAULT_ALARM_ALERT_URI);
        player.start();
        return  START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        runningback = false;
        player.stop();

    }

    public void runmenu(){
        Rides = new Firebase("https://easy-bike-1f32d.firebaseio.com/Rides");
        noofrides = new Firebase("https://easy-bike-1f32d.firebaseio.com/Noofrides");



    }
    //In running screen the time and price will get update in run time
    static Runnable updatetime = new Runnable() {
        @Override
        public void run() {
            timeinsec = SystemClock.uptimeMillis()-starttime;
            updatetimer = timesapbuffer+timeinsec;
            sec = (int)(updatetimer/1000);
            min =sec/60;
            totalmin =sec/60;
            sec%=60;
            hrs = min/60;
            min%=60;
            millisec = (int)(updatetimer%1000);

            Running_amount = totalmin*1.50;

//            nfc = new Firebase("https://easy-bike-1f32d.firebaseio.com/Bike/"+scannerresult+"/NFC");
//            nfc.setValue(0);



            settextString=""+String.format("%02d",hrs)+":"+String.format("%02d",min)+":"+String.format("%02d",sec);

            handler.postDelayed(this,0);

        }

    };
}
