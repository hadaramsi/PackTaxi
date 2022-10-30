package com.example.packtaxi.model;

import android.location.Address;
import android.location.Geocoder;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.packtaxi.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Entity
public class DeliveryPoint {
    final static String ID = "id";
    final static String NAME = "name";
    final static String LATITUDE = "latitude";
    final static String LONGITUDE = "longitude";

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private double longitude;
    private double latitude;

    public DeliveryPoint() {
    }
    public DeliveryPoint(String id, String name, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public String getDeliveryPointID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public DeliveryPoint getDeliveryPointByID(String id) {
        return this;
    }
    public void setDeliveryPointName(String name) {
        this.name = name;
    }
    public void setDeliveryPointID(String id) {
        this.id = id;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public String getLocation() {
        Geocoder geocoder = new Geocoder(MyApplication.getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses.get(0).getAddressLine(0);
    }
    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID,ID);
        json.put(LONGITUDE,longitude);
        json.put(LATITUDE,latitude);
        return json;
    }
    static public DeliveryPoint fromJson(String reportId, Map<String, Object> json){
        String id = ID;
        String name = (String)json.get(NAME);
        if(name == null)
            return null;
        double latitude = (double)json.get(LATITUDE);
        double longitude = (double)json.get(LONGITUDE);
        DeliveryPoint dp = new DeliveryPoint(id, name, longitude, latitude);
        return dp;
    }


}
