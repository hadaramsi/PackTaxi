package com.example.packtaxi;

import androidx.lifecycle.ViewModel;

public class packageDetailsViewModel extends ViewModel {
    private String packageId;

    public String getPackageId(){
        return packageId;
    }
    public void setPackageId(String packageId){
        this.packageId = packageId;
    }

}
