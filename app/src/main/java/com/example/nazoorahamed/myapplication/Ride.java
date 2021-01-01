package com.example.nazoorahamed.myapplication;

/**
 * Created by nazoorahamed on 4/3/18.
 */

public class Ride {

    private String Ride_Amount;
    private String Ride_Bike;
    private String Ride_ID;
    private String ridetime;


    public Ride() {
    }

    public Ride(String ride_Amount, String ride_Bike, String ride_ID, String ridetime) {
        Ride_Amount = ride_Amount;
        Ride_Bike = ride_Bike;
        Ride_ID = ride_ID;
        this.ridetime = ridetime;
    }

    public String getRide_Amount() {
        return Ride_Amount;
    }

    public void setRide_Amount(String ride_Amount) {
        Ride_Amount = ride_Amount;
    }

    public String getRide_Bike() {
        return Ride_Bike;
    }

    public void setRide_Bike(String ride_Bike) {
        Ride_Bike = ride_Bike;
    }

    public String getRide_ID() {
        return Ride_ID;
    }

    public void setRide_ID(String ride_ID) {
        Ride_ID = ride_ID;
    }

    public String getRidetime() {
        return ridetime;
    }

    public void setRidetime(String ridetime) {
        this.ridetime = ridetime;
    }
}
