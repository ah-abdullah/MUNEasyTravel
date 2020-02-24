package com.example.muneasytravel;
import android.os.Parcel;
import java.io.Serializable;

public class Building implements Serializable {
    private double lat;
    private double lon;
    private String buildingName;
    private int numOfFloors;

    public Building(String buildingName, double lat, double lon) {
        this(buildingName, lat, lon, 1);
    }

    public Building(String buildingName, double lat, double lon, int numOfFloors) {
        this.lat = lat;
        this.lon = lon;
        this.buildingName = buildingName;
        this.numOfFloors = numOfFloors;
    }

    public int getNumOfFloors() {
        return numOfFloors;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getBuildingName() {
        return buildingName;
    }
}
