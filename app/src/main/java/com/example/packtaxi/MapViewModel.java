package com.example.packtaxi;

import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
    private String DeliveryPointID;

    public void setDeliveryPointID(String dp){ DeliveryPointID = dp;}
    public String getDeliveryPointID(){return DeliveryPointID;}
}
