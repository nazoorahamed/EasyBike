package com.example.nazoorahamed.myapplication;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by nazoorahamed on 3/21/18.
 */

public class MyAppliccation extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
