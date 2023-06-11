package com.example.packtaxi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.packtaxi.model.Driver;
import com.example.packtaxi.model.Model;
import com.example.packtaxi.model.Sender;

public class signUpAsDriverFragment extends Fragment {
    private Button signUpBtn;
    private ProgressBar pb;
    private EditText emailEt;
    private EditText passwordEt;
    private EditText fullNameEt;
    private EditText carNumberEt;
    private EditText licenseEt;
    private EditText maxVolumeEt;
    private EditText maxWeightEt;

    public signUpAsDriverFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_as_driver, container, false);

        signUpBtn = view.findViewById(R.id.signUpDriver_btn);
        emailEt = view.findViewById(R.id.signUpDriver_email_et);
        passwordEt = view.findViewById(R.id.signUpDriver_password_et);
        fullNameEt = view.findViewById(R.id.signUpDriver_fullName_et);
        carNumberEt = view.findViewById(R.id.signUpDriver_carNumber_et);
        licenseEt = view.findViewById(R.id.signUpDriver_licenseNumber_et);
        maxVolumeEt = view.findViewById(R.id.signUpDriver_maxVolume_ev);
        maxWeightEt = view.findViewById(R.id.signUpDriver_maxWeight_et);
        pb = view.findViewById(R.id.signUpDriver_pb);
        pb.setVisibility(View.GONE);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(false);
                if (checkDetails()) {
                    registerSender(v);
                }
                else {
                    pb.setVisibility(View.GONE);
                    signUpBtn.setEnabled(true);
                }
            }
        });
        return view;
    }

    private void registerSender(View v)
    {
        String password = passwordEt.getText().toString().trim();
        Driver driver = new Driver();
        driver.setFullName(fullNameEt.getText().toString().trim());
        driver.setEmail(emailEt.getText().toString().trim());
//        driver.setPassword(passwordEt.getText().toString().trim());
        driver.setLicenseNumber(licenseEt.getText().toString().trim());
        driver.setCarNumber(carNumberEt.getText().toString().trim());
        driver.setMaxVolume(Integer.parseInt(maxVolumeEt.getText().toString().trim()));
        driver.setMaxWeight(Integer.parseInt(maxWeightEt.getText().toString().trim()));
        Model.getInstance().addDriver(driver, password, (ifSuccess) -> {
            if(ifSuccess) {
                @NonNull NavDirections action = signUpAsDriverFragmentDirections.actionFragmentSignUpAsDriverToMainScreenDriverFragment();
                Navigation.findNavController(v).navigate(action);
            }
            else{
                Toast.makeText(getActivity(), "failed to register, please change the email", Toast.LENGTH_LONG).show();
                pb.setVisibility(View.GONE);
                signUpBtn.setEnabled(true);
            }
        });
    }
    private boolean checkDetails()
    {
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String fullName = fullNameEt.getText().toString().trim();
        String license= licenseEt.getText().toString().trim();
        String carNumber = carNumberEt.getText().toString().trim();
        String maxVolume = maxVolumeEt.getText().toString().trim();
        String maxWeight = maxWeightEt.getText().toString().trim();
        if(fullName.isEmpty()) {
            fullNameEt.setError("Full name is required");
            fullNameEt.requestFocus();
            return false;
        }
        if(email.isEmpty()) {
            emailEt.setError("Email is required");
            emailEt.requestFocus();
            return false;
        }
        if(password.isEmpty()) {
            passwordEt.setError("Password is required");
            passwordEt.requestFocus();
            return false;
        }
        if(password.length() < 6) {
            passwordEt.setError("Password should be at least 6 characters");
            passwordEt.requestFocus();
            return false;
        }
        if(license.isEmpty()) {
            licenseEt.setError("License number is required");
            licenseEt.requestFocus();
            return false;
        }
        if(carNumber.isEmpty()) {
            carNumberEt.setError("Car number is required");
            carNumberEt.requestFocus();
        return false;
    }
        if(maxVolume.isEmpty()) {
            maxVolumeEt.setError("Max volume is required");
            maxVolumeEt.requestFocus();
        return false;
    }
        if(maxWeight.isEmpty()) {
            maxWeightEt.setError("Max weight is required");
            maxWeightEt.requestFocus();
        return false;
    }
        return true;
    }
}