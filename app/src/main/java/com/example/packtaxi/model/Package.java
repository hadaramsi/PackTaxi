package com.example.packtaxi.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Package {
    final static String SOURCE = "source";
    final static String PACKAGEID = "packageID";
    final static String DESTINATION = "destination";
    final static String DATE = "date";
    final static String COST = "cost";
    final static String VOLUME = "volume";
    final static String WEIGHT = "weight";
    final static String NOTE = "note";
    @PrimaryKey
    @NonNull
    private String packageID;
    private String source;
    private String destination;
    private long date;
    private double cost;
    private double volume;
    private double weight;
    private String note;

    public Package(){}

    public Package(String packageID, String source, String destination, long date,double cost,double volume,double weight, String note){
        this.packageID= packageID;
        this.source= source;
        this.destination= destination;
        this.cost= cost;
        this.note=note;
        this.date=date;
        this.weight=weight;
        this.volume=volume;
    }

    public String getPackageID(){
        return packageID;
    }
    public String getSource(){
        return source;
    }
    public String getDestination(){
        return destination;
    }
    public String getNote(){
        return note;
    }
    public long getDate(){
        return date;
    }
    public double getCost(){
        return cost;
    }
    public double getWeight(){
        return weight;
    }
    public double getVolume(){
        return volume;
    }

    public void setPackageID(String packageID){
        this.packageID = packageID;
    }
    public void setSource(String source){
        this.source = source;
    }
    public void setDestination(String destination){
        this.destination = destination;
    }
    public void setNote(String note){
        this.note = note;
    }
    public void setDate(long date){
        this.date = date;
    }
    public void setCost(double cost){
        this.cost = cost;
    }
    public void setVolume(double volume){
        this.volume = volume;
    }
    public void setWeight(double weight){
        this.weight = weight;
    }

    static public Package fromJson(String packageId, Map<String, Object> json){
        String packageID = (String)json.get(PACKAGEID);
        if( packageID== null)
            return null;
        String source = (String)json.get(SOURCE);
        if(source == null)
            return null;
        String destination = (String)json.get(DESTINATION);
        if(destination == null)
            return null;
        double cost = (double)json.get(COST);
        if(cost == 0.0)
            return null;
        double volume = (double)json.get(VOLUME);
        if(volume == 0.0)
            return null;
        double weight = (double)json.get(WEIGHT);
        if(weight == 0.0)
            return null;
        long date= (long) json.get(DATE);
        String note = (String) json.get(NOTE);
        if(note == null)
            return null;
        Package p = new Package(packageID, source, destination, date,cost,volume,weight, note);
        return p;
    }
    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(PACKAGEID, packageID);
        json.put(SOURCE, source);
        json.put(DESTINATION, destination);
        json.put(DATE, date);
        json.put(COST, cost);
        json.put(NOTE, note);
        json.put(WEIGHT, weight);
        json.put(VOLUME, volume);
        return json;
    }


}
