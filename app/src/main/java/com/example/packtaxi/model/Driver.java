package com.example.packtaxi.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Driver {
    final static String EMAIL = "email";
    final static String FULLNAME = "fullName";
    final static String PASSWORD = "password";
    final static String CAR_NUMBER = "carNumber";
    final static String LICENSE_NUMBER = "licenseNumber";
    final static String RATE = "rate";
    final static String MAX_VOLUME = "maxVolume";
    final static String MAX_WEIGHT = "maxWeight";

    @PrimaryKey
    @NonNull
    private String email;
    private String fullName;
    private String licenseNumber;
    private String carNumber;
    private double rate;
    private long maxVolume;
    private long maxWeight;

    public Driver(){}

    public Driver(String email, String fullName, String licenseNumber,String carNumber, long maxVolume, long maxWeight, double rate){
        this.email= email;
//        this.password= password;
        this.fullName= fullName;
        this.licenseNumber= licenseNumber;
        this.carNumber=carNumber;
        this.maxVolume=maxVolume;
        this.maxWeight=maxWeight;
        this.rate=rate;
    }
    public double getRate(){return rate;}
    public String getEmail(){
        return email;
    }
//    public String getPassword(){
//        return password;
//    }
    public String getFullName(){
        return fullName;
    }
    public String getLicenseNumber(){
        return licenseNumber;
    }
    public String getCarNumber(){
        return carNumber;
    }
    public long getMaxVolume(){
        return maxVolume;
    }
    public long getMaxWeight(){
        return maxWeight;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setRate(double rate){this.rate=rate;}
//    public void setPassword(String password){
//        this.password = password;
//    }
    public void setLicenseNumber(String licenseNumber){
        this.licenseNumber = licenseNumber;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public void setCarNumber(String carNumber){
        this.carNumber = carNumber;
    }
    public void setMaxVolume(long maxVolume){
        this.maxVolume = maxVolume;
    }
    public void setMaxWeight(long maxWeight){
        this.maxWeight = maxWeight;
    }
    static public Driver fromJson(Map<String, Object> json){
        String email = (String)json.get(EMAIL);
        Log.d("TAG","---"+ email);
        if(email == null)
            return null;
        String fullName = (String)json.get(FULLNAME);
        Log.d("TAG","---"+ fullName);

        if(fullName == null)
            return null;
//        String password = (String)json.get(PASSWORD);
//        if(password == null)
//            return null;
        String licenseNumber = (String)json.get(LICENSE_NUMBER);
        Log.d("TAG","---"+ licenseNumber);

        if(licenseNumber == null)
            return null;
        String carNumber = (String)json.get(CAR_NUMBER);
        Log.d("TAG","---"+ carNumber);

        if(carNumber == null)
            return null;
        long maxVolume = (long) json.get(MAX_VOLUME);
        Log.d("TAG","---"+ maxVolume);

        if(maxVolume == 0)
            return null;
        long maxWeight = (long)json.get(MAX_WEIGHT);
        Log.d("TAG","---"+ maxWeight);

        if(maxWeight == 0)
            return null;
        double rate=Double.parseDouble(json.get(RATE).toString());
        Log.d("TAG","---"+ rate);
//        if(rate<0 || rate >5)
//            return null;
        Driver driver = new Driver(email, fullName, licenseNumber, carNumber, maxVolume, maxWeight, rate);
//        driver.setRate(rate);
        return driver;
    }
    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(EMAIL, email);
        json.put(FULLNAME, fullName);
//        json.put(PASSWORD, password);
        json.put(LICENSE_NUMBER, licenseNumber);
        json.put(CAR_NUMBER, carNumber);
        json.put(MAX_WEIGHT, maxWeight);
        json.put(MAX_VOLUME, maxVolume);
        json.put(RATE, rate);
        return json;
    }
}
