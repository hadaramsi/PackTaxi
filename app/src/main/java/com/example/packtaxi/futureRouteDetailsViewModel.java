package com.example.packtaxi;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class futureRouteDetailsViewModel extends ViewModel {
    private String routeId;

    public String getRouteId(){
        return routeId;
    }
    public void setRouteId(String routeId){
        this.routeId = routeId;
    }

}
