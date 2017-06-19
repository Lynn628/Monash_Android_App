package com.example.s17.moniteryourlife.FragementUtil;

/**
 * Created by s17 on 4/20/2016.
 */
public class Location {
    double latitude;
    double longtitude;

    public Location(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLongtitude() {
        return longtitude;
    }
}


