package com.example.packtaxi;

import androidx.lifecycle.ViewModel;

public class driverRatingViewModel extends ViewModel {
    private String driverId;
    private String packageId;

    public String getDriverId(){
        return driverId;
    }
    public void setDriverId(String driverId){
        this.driverId = driverId;
    }
    public String getPackageId(){
        return packageId;
    }
    public void setPackageId(String packageId){
        this.packageId = packageId;
    }

}
