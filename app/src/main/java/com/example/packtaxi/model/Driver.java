package com.example.packtaxi.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Driver {
    final static String FULL_NAME = "fullName";
    final static String ID = "id";
    final static String PASSWORD = "password";
    final static String CAR_NUMBER = "carNumber";
    final static String LICENSE_NUMBER = "licenseNumber";
    final static String MAX_VOLUME = "maxVolume";
    final static String MAX_WEIGHT = "maxWeight";// maybe int

    @PrimaryKey
    @NonNull
    private String id;
    private String password;
    private String fullName;
    private String licenseNumber;
    private String carNumber;
    private int maxVolume;
    private int maxWeight;

    public Driver(){}

    public Driver(String id, String password, String fullName, String licenseNumber,String carNumber, int maxVolume, int maxWeight){
        this.id= id;
        this.password= password;
        this.fullName= fullName;
        this.licenseNumber= licenseNumber;
        this.carNumber=carNumber;
        this.maxVolume=maxVolume;
        this.maxWeight=maxWeight;
    }
    public String getID(){
        return id;
    }
    public String getFullName(){
        return fullName;
    }
    public String getLicenseNumber(){
        return licenseNumber;
    }
    public String getCarNumber(){
        return carNumber;
    }
    public int getMaxVolume(){
        return maxVolume;
    }
    public int getMaxWeight(){
        return maxWeight;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public void setCarNumber(String carNumber){
        this.carNumber = carNumber;
    }
    public void setMaxVolume(int maxVolume){
        this.maxVolume = maxVolume;
    }
    public void setMaxWeight(int maxWeight){
        this.maxWeight = maxWeight;
    }
}
