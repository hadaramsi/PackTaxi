package com.example.packtaxi.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class FutureRoute {
    final static String SOURCE = "source";
    final static String FUTUREROUTEID = "futureRouteID";
    final static String DESTINATION = "destination";
    final static String DATE = "date";
    final static String COST = "cost";
    final static String WEIGHT = "weight";
    final static String VOLUME = "volume";
    final static String DRIVER = "driver";
    final static String MATCH = "match";
    final static String PACKAGESLIST="packagesList";

    @PrimaryKey
    @NonNull
    private String futureRouteID;
    private String source;
    private String destination;
    private String date;
    private double cost;
    private String driver;
    private String packagesNumbers;
    private long volume;
    private long weight;

    public FutureRoute(){
        this.packagesNumbers=",";
        this.volume=0;
        this.weight=0;
    }

    public FutureRoute(String futureRouteID, String source, String destination,
                       String date,double cost, String driver, String packagesNumbers,
                       long volume, long weight){
        this.futureRouteID= futureRouteID;
        this.source= source;
        this.destination= destination;
        this.cost= cost;
        this.driver=driver;
        this.date=date;
        this.volume=volume;
        this.weight=weight;
        this.packagesNumbers=packagesNumbers;
    }
    public long getVolume(){
        return volume;
    }
    public long getWeight(){
        return weight;
    }
    public String getFutureRouteID(){
        return futureRouteID;
    }
    public String getSource(){
        return source;
    }
    public String getDestination(){
        return destination;
    }
    public String getDriver(){
        return driver;
    }
    public String getDate(){
        return date;
    }
    public double getCost(){
        return cost;
    }
    public String getPackagesNumbers(){return packagesNumbers;}
    public void setVolume(long volume){
        this.volume = volume;
    }
    public void setWeight(long weight){
        this.weight = weight;
    }
    public void setFutureRouteID(String futureRouteID){
        this.futureRouteID = futureRouteID;
    }
    public void setSource(String source){
        this.source = source;
    }
    public void setDestination(String destination){
        this.destination = destination;
    }
    public void setDriver(String driver){
        this.driver = driver;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setCost(double cost){
        this.cost = cost;
    }
    public void setPackagesNumbers(String pn){
        if(this.packagesNumbers.equals(",")){
            this.packagesNumbers=pn;
        }
        else{
            this.packagesNumbers= packagesNumbers+","+ pn;
        }
    }

    static public FutureRoute fromJson(String futureRouteId,Map<String, Object> json){
        String futureRouteID = (String)json.get(FUTUREROUTEID);
        if( futureRouteID== null)
            return null;
        String source = (String)json.get(SOURCE);
        if(source == null)
            return null;
        String destination = (String)json.get(DESTINATION);
        if(destination == null)
            return null;
        double cost = Double.parseDouble(json.get(COST).toString());
        if(cost == 0.0)
            return null;
        String date= (String) json.get(DATE);
        String driver = (String) json.get(DRIVER);
        if(driver == null)
            return null;
        String pacNums = (String)json.get(PACKAGESLIST);
        if(pacNums == null)
            return null;
        long volume = Long.parseLong(json.get(VOLUME).toString());
        if(volume == 0)
            return null;
        long weight = Long.parseLong(json.get(WEIGHT).toString());
        if(weight == 0)
            return null;
        FutureRoute futureRoute = new FutureRoute(futureRouteID, source, destination, date,
                cost, driver, pacNums, volume, weight);
        return futureRoute;
    }
    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(FUTUREROUTEID, futureRouteID);
        json.put(SOURCE, source);
        json.put(DESTINATION, destination);
        json.put(DATE, date);
        json.put(COST, cost);
        json.put(DRIVER, driver);
        json.put(WEIGHT, weight);
        json.put(VOLUME, volume);
        json.put(PACKAGESLIST,packagesNumbers);
        return json;
    }
}
