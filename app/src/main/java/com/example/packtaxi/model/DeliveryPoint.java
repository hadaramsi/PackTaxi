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
//    final static String DELIVERYPOINTID = "deliveryPointID";
    final static String NAME = "deliveryPointName";
    final static String LATITUDE = "latitude";
    final static String LONGITUDE = "longitude";
    public final static String LAST_UPDATED = "lastUpdated";
    final static String DELIVERYPOINTS_LAST_UPDATE = "DELIVERYPOINTS_LAST_UPDATE";
    final static String IS_DELETED = "isDeleted";

    @PrimaryKey
    @NonNull
    private String deliveryPointName;
    private double longitude;
    private double latitude;
    private boolean isDeleted;
    private Long lastUpdated = new Long(0);

    public DeliveryPoint() {
    }
    public DeliveryPoint(String name, double longitude, double latitude, Long lastUpdated) {
        this.deliveryPointName = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.lastUpdated = lastUpdated;
        this.isDeleted = false;
    }

//    public String getDeliveryPointID() {
//        return DELIVERYPOINTID;
//    }
    public String getDeliveryPointName() {
        return deliveryPointName;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public Long getLastUpdated(){
        return lastUpdated;
    }
    public boolean getIsDeleted(){return isDeleted;}

    public DeliveryPoint getDeliveryPointByName(String name) {
        return this;
    }

    public void setDeliveryPointName(String name) {
        this.deliveryPointName = name;
    }
//    public void setDeliveryPointID(String id) {
//        this.deliveryPointID = id;
//    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLastUpdated(Long lastUpdated){
        this.lastUpdated = lastUpdated;
    }
    public void setIsDeleted(boolean state){this.isDeleted = state;}

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
//        json.put(DELIVERYPOINTID,deliveryPointID);
        json.put(NAME,deliveryPointName);
        json.put(LONGITUDE,longitude);
        json.put(LATITUDE,latitude);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(IS_DELETED, isDeleted);
        return json;
    }

    static public DeliveryPoint fromJson(String deliveryPointID, Map<String, Object> json){
//        String id = DELIVERYPOINTID;
        String name = (String)json.get(NAME);
        if(name == null)
            return null;
        double latitude = (double)json.get(LATITUDE);
        double longitude = (double)json.get(LONGITUDE);
        Timestamp ts = (Timestamp) json.get(LAST_UPDATED);
        Long lastUpdated = new Long(ts.getSeconds());
        boolean state = (boolean) json.get(IS_DELETED);
        DeliveryPoint dp = new DeliveryPoint(name, longitude, latitude,lastUpdated);
        dp.setIsDeleted(state);
        return dp;
    }
    static Long getLocalLastUpdated(){
        Long localLastUpdate = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getLong(DELIVERYPOINTS_LAST_UPDATE, 0);
        return localLastUpdate;
    }

    static void setLocalLastUpdated(Long date){
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong(DELIVERYPOINTS_LAST_UPDATE, date);
        editor.commit();
    }

}
