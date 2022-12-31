package com.example.packtaxi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.packtaxi.model.Driver;
import com.example.packtaxi.model.Model;
import com.example.packtaxi.model.Sender;


public class driverProfileFragment extends Fragment {
    TextView fullNameTv;
    TextView emailTv ;
    View view;
    ProgressBar pb;
    TextView carNumber;
    TextView licenseNumber;
    TextView maximumWeight;
    TextView maximumVolume;
    TextView rating;

    public driverProfileFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_driver_profile, container, false);
        fullNameTv = view.findViewById(R.id.driverProfile_fullName_info_tv);
        emailTv = view.findViewById(R.id.driverProfile_email_info_tv);
        carNumber = view.findViewById(R.id.driverProfile_carNumber_info_tv);
        licenseNumber = view.findViewById(R.id.driverProfile_licenseNumber_info_tv);
        maximumWeight = view.findViewById(R.id.driverProfile_maxWeight_info_tv);
        maximumVolume = view.findViewById(R.id.driverProfile_mavVolume_info_tv);
        rating = view.findViewById(R.id.driverProfile_rating_info_tv);
        pb = view.findViewById(R.id.driverProfile_pb);
        pb.setVisibility(View.VISIBLE);
        Model.getInstance().getCurrentDriver(new Model.getCurrentDriverListener() {
            @Override
            public void onComplete(String driverEmail) {
                Model.getInstance().getDriverByEmail(driverEmail, new Model.getDriverByEmailListener() {
                    @Override
                    public void onComplete(Driver driver) {
                        Log.d("TAG","on complete");
                        setDetails(driver);
                    }
                });
            }
        });
        setHasOptionsMenu(true);
        return view;
    }
    public void setDetails(Driver d){
        Log.d("TAG","on set");
        fullNameTv.setText(d.getFullName());
        emailTv.setText(d.getEmail());
        carNumber.setText(d.getCarNumber());
        licenseNumber.setText(d.getLicenseNumber());
        maximumWeight.setText(d.getMaxWeight());
        maximumVolume.setText(d.getMaxVolume());
        rating.setText(String.valueOf(d.getRate()));
        pb.setVisibility(View.GONE);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.driver_menu, menu);
        inflater.inflate(R.menu.base_menu, menu);
    }
}