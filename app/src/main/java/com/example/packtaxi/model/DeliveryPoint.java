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
import android.content.Context;
import android.content.SharedPreferences;

@Entity
public class DeliveryPoint {
    final static String DELIVERYPOINTID = "deliveryPointID";
    final static String NAME = "deliveryPointName";
    final static String LATITUDE = "latitude";
    final static String LONGITUDE = "longitude";

    @PrimaryKey
    @NonNull
    private String deliveryPointID;
    private String deliveryPointName;
    private double longitude;
    private double latitude;

    public DeliveryPoint() {
    }
    public DeliveryPoint(String id, String name, double longitude, double latitude) {
        this.deliveryPointID = id;
        this.deliveryPointName = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public String getDeliveryPointID() {
        return DELIVERYPOINTID;
    }
    public String getDeliveryPointName() {
        return deliveryPointName;
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
        this.deliveryPointName = name;
    }
    public void setDeliveryPointID(String id) {
        this.deliveryPointID = id;
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
        json.put(DELIVERYPOINTID,deliveryPointID);
        json.put(NAME,deliveryPointName);
        json.put(LONGITUDE,longitude);
        json.put(LATITUDE,latitude);
        return json;
    }

    static public DeliveryPoint fromJson(String deliveryPointID, Map<String, Object> json){
        String id = DELIVERYPOINTID;
        String name = (String)json.get(NAME);
        if(name == null)
            return null;
        double latitude = (double)json.get(LATITUDE);
        double longitude = (double)json.get(LONGITUDE);
        DeliveryPoint dp = new DeliveryPoint(id, name, longitude, latitude);
        return dp;
    }


}
