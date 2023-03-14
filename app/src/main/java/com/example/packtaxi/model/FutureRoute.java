package com.example.packtaxi.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Entity
public class FutureRoute {
    final static String SOURCE = "source";
    final static String FUTUREROUTEID = "futureRouteID";
    final static String DESTINATION = "destination";
    final static String DATE = "date";
    final static String COST = "cost";
    final static String DRIVER = "driver";

    @PrimaryKey
    @NonNull
    private String futureRouteID;
    private String source;
    private String destination;
    private String date;
    private double cost;
    private String driver;

    public FutureRoute(){}

    public FutureRoute(String futureRouteID, String source, String destination, String date,double cost, String driver){
        this.futureRouteID= futureRouteID;
        this.source= source;
        this.destination= destination;
        this.cost= cost;
        this.driver=driver;
        this.date=date;
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
        double cost = (double)json.get(COST);
        if(cost == 0.0)
            return null;
        String date= (String) json.get(DATE);
        String driver = (String) json.get(DRIVER);
        if(driver == null)
            return null;
        FutureRoute futureRoute = new FutureRoute(futureRouteID, source, destination, date, cost, driver);
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
        return json;
    }
}
