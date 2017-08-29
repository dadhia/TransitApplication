package com.devanlocker.transitapplication;

public class Stop {
    private double longitude;
    private double latitude;
    private int id;
    private String name;

    public Stop (double latitude, double longitude, int id, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.name = name;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
