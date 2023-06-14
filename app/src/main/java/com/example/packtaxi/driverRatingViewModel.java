package com.example.packtaxi;

import androidx.lifecycle.ViewModel;

public class driverRatingViewModel extends ViewModel {
    private String driverId;

    public String getDriverId(){
        return driverId;
    }
    public void setDriverId(String driverId){
        this.driverId = driverId;
    }

}
