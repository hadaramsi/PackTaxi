package com.example.packtaxi.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;


@Entity
public class Sender {
    final static String EMAIL = "email";
    final static String FULLNAME = "fullName";
    final static String IDNUMBER = "IDNumber";
    final static String PASSWORD = "password";
    @PrimaryKey
    @NonNull
    private String email;
    private String password;
    private String fullName;

    public Sender(){}

    public Sender(String email, String password, String fullName){
        this.email= email;
        this.password= password;
        this.fullName= fullName;
    }
    public String getEmail(){
        return email;
    }
    public String getFullName(){
        return fullName;
    }
    public String getPassword(){
        return password;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    static public Sender fromJson(Map<String, Object> json){
        String email = (String)json.get(EMAIL);
        if(email == null)
            return null;
        String fullName = (String)json.get(FULLNAME);
        if(fullName == null)
            return null;
        String password = (String)json.get(PASSWORD);
        if(password == null)
            return null;
        Sender sender = new Sender(email, password, fullName);
        return sender;
    }
    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(EMAIL, email);
        json.put(FULLNAME, fullName);
        json.put(PASSWORD, password);
        return json;
    }
}
