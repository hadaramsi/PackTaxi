package com.example.packtaxi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    Button save;

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
        maximumVolume = view.findViewById(R.id.driverProfile_maxVolume_info_tv);
        rating = view.findViewById(R.id.driverProfile_rating_info_tv);
        pb = view.findViewById(R.id.driverProfile_pb);
        pb.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        save=view.findViewById(R.id.driverProfile_save_btn);
        Model.getInstance().getCurrentDriver(new Model.getCurrentDriverListener() {
            @Override
            public void onComplete(String driverEmail) {
                Model.getInstance().getDriverByEmail(driverEmail, new Model.getDriverByEmailListener() {
                    @Override
                    public void onComplete(Driver driver) {
                        Log.d("TAG","on complete"+ driver.getFullName());
                        setDetails(driver);
                    }
                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                save.setEnabled(false);
                if(!checkConditions()) {
                    setDetails();
                }
                else {
                    pb.setVisibility(View.GONE);
                    save.setEnabled(true);
                }
            }
        });
        return view;
    }
    private void setDetails() {
        Driver user = new Driver();
        Model.getInstance().getCurrentDriver(new Model.getCurrentDriverListener() {
            @Override
            public void onComplete(String driverEmail) {
                user.setFullName(fullNameTv.getText().toString());
                user.setLicenseNumber(licenseNumber.getText().toString());
                user.setEmail(emailTv.getText().toString());
                user.setCarNumber(carNumber.getText().toString());
                user.setMaxVolume(Long.parseLong(maximumVolume.getText().toString()));
                user.setMaxWeight(Long.parseLong(maximumWeight.getText().toString()));
                user.setRate(Double.parseDouble(rating.getText().toString()));
                Model.getInstance().editDriver(user, (success)->{
                    if(success)
                        Navigation.findNavController(view).navigateUp();
                    else{
                        pb.setVisibility(View.GONE);
                        save.setEnabled(true);
                    }
                });
            }
        });
    }
    private boolean checkConditions(){
        if(carNumber.getText().toString().equals("")){
            carNumber.setError("car number is required");
            carNumber.requestFocus();
            return true;
        }
        if(maximumVolume.getText().toString().equals("")){
            maximumVolume.setError("maximum Volume is required");
            maximumVolume.requestFocus();
            return true;
        }
        if(maximumWeight.getText().toString().equals("")){
            maximumWeight.setError("maximum Weight is required");
            maximumWeight.requestFocus();
            return true;
        }
        return false;
    }
    public void setDetails(Driver d){
        Log.d("TAG","on set");
        fullNameTv.setText(d.getFullName());
        emailTv.setText(d.getEmail());
        carNumber.setText(d.getCarNumber());
        licenseNumber.setText(d.getLicenseNumber());
        maximumWeight.setText(String.valueOf(d.getMaxWeight()));
        maximumVolume.setText(String.valueOf(d.getMaxVolume()));
        rating.setText(String.valueOf(d.getRate()));
        pb.setVisibility(View.GONE);
    }
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.driver_menu, menu);
//        inflater.inflate(R.menu.base_menu, menu);
//    }
}