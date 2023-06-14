package com.example.packtaxi.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class payment {
    final static String NUMPACKAGE = "package";
    final static String CARDNUMBER = "cardNumber";
    final static String CARDVALIDITY = "cardValidity";
    final static String THREEDIGITS = "threeDigits";
    @PrimaryKey
    @NonNull
    private String numPackage;
    private String cardNumber;
    private String cardValidity;
    private String threeDigits;

    public payment(){}

    public payment(String numPackage, String cardNumber, String cardValidity,String threeDigits ){
        this.numPackage= numPackage;
        this.cardNumber= cardNumber;
        this.cardValidity= cardValidity;
        this.threeDigits=threeDigits;
    }

    public String getNumPackage(){ return numPackage; }
    public String getCardNumber(){
        return cardNumber;
    }
    public String getCardValidity(){ return cardValidity; }
    public String getThreeDigits(){
        return threeDigits;
    }
    public void setNumPackage(String np){
        this.numPackage = np;
    }
    public void setCardNumber(String cn){
        this.cardNumber = cn;
    }
    public void setCardValidity(String cv){ this.cardValidity = cv; }
    public void setThreeDigits(String td){ this.threeDigits = td; }

    static public payment fromJson(Map<String, Object> json){
        String np = (String)json.get(NUMPACKAGE);
        if(np == null)
            return null;
        String cn = (String)json.get(CARDNUMBER);
        if(cn == null)
            return null;
        String cv = (String)json.get(CARDVALIDITY);
        if(cv == null)
            return null;
        String td = (String)json.get(THREEDIGITS);
        if(td == null)
            return null;
        payment paym = new payment(np,cn, cv,td);
        return paym;
    }
    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(NUMPACKAGE, numPackage);
        json.put(CARDNUMBER, cardNumber);
        json.put(CARDVALIDITY, cardValidity);
        json.put(THREEDIGITS, threeDigits);
        return json;
    }

}
