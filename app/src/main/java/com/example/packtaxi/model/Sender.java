package com.example.packtaxi.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Sender {
    final static String USERNAME = "userName";
    final static String FULLNAME = "fullName";
    final static String IDNUMBER = "IDNumber";
    final static String PASSWORD = "password";
    @PrimaryKey
    @NonNull
    private String userName;
    private String password;
    private String fullName;
    private String IDNumber;

    public Sender(){}

    public Sender(String userName, String password, String fullName, String IDNumber){
        this.userName= userName;
        this.password= password;
        this.fullName= fullName;
        this.IDNumber= IDNumber;
    }
    public String getUserName(){
        return userName;
    }
    public String getFullName(){
        return fullName;
    }
    public String getIDNumber(){
        return IDNumber;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }

}
