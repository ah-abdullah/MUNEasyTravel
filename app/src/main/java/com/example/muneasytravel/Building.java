package com.example.muneasytravel;
import android.os.Parcel;
import java.io.Serializable;

public class Building implements Serializable {
    private double lat;
    private double lon;
    private String buildingName;

    public Building(String buildingName, double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.buildingName = buildingName;
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
