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
    final static String SENDER = "sender";
    final static String DRIVER = "driver";
    final static String RATE = "rate";
    final static String PAY = "pay";
    final static String IFRATE = "ifRate";




    @PrimaryKey
    @NonNull
    private String packageID;
    private String source;
    private String destination;
//    private long date;
    private String date;
    private String sender;
    private double cost;
    private double volume;
    private double weight;
    private String note;
    private String driver;
    private double rate;
    private boolean pay;
    private boolean ifRate;

    public Package(){}

    public Package(String packageID, String source, String destination, String date,double cost,double volume,double weight, String note, String sender){
        this.packageID= packageID;
        this.source= source;
        this.destination= destination;
        this.cost= cost;
        this.note=note;
        this.date=date;
        this.weight=weight;
        this.volume=volume;
        this.sender=sender;
        this.driver="-";
        this.rate=0;
        this.pay=false;
        this.ifRate=false;

    }

    public String getPackageID(){
        return packageID;
    }
    public boolean getPay(){ return pay; }
    public boolean getIfRate(){ return ifRate; }
    public String getSender(){
        return sender;
    }
    public String getDriver(){
        return driver;
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
    public String getDate(){
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
    public double getRate(){
        return rate;
    }
    public void setSender(String s){
        this.sender = s;
    }
    public void setDriver(String d){
        this.driver = d;
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
    public void setDate(String date){
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
    public void setRate(double rate){
        this.rate = rate;
    }
    public void setPay(boolean p){
        this.pay = p;
    }
    public void setIfRate(boolean r){
        this.ifRate = r;
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
        String date= (String) json.get(DATE);
        String sender= (String) json.get(SENDER);

        String note = (String) json.get(NOTE);
        if(note == null)
            return null;
        Package p = new Package(packageID, source, destination, date,cost,volume,weight, note, sender);
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
        json.put(SENDER, sender);
        json.put(DRIVER, driver);
        json.put(RATE, rate);
        json.put(PAY,pay);
        json.put(IFRATE,ifRate);

        return json;
    }


}
