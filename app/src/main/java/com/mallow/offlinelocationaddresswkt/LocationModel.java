package com.mallow.offlinelocationaddresswkt;

/**
 * Created by manikandan on 06/04/18.
 */

public class LocationModel {
    double latitude = 0l;
    double longitude = 0l;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocationModel(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
