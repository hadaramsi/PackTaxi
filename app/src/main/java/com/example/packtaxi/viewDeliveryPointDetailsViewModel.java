package com.example.packtaxi;

import androidx.lifecycle.ViewModel;

public class viewDeliveryPointDetailsViewModel  extends ViewModel {
    private String deliveryPointID;

    public String getDeliveryPointID(){
        return deliveryPointID;
    }
    public void setDeliveryPointID(String dpID){
        this.deliveryPointID = dpID;
    }
}
