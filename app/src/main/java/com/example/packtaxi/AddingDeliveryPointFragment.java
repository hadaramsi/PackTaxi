package com.example.packtaxi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.packtaxi.model.DeliveryPoint;
import com.example.packtaxi.model.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddingDeliveryPointFragment extends Fragment {
    private Button addBtn;
    private EditText nameEt;
    private EditText longitudeEt;
    private EditText latitudeEt;
    private ProgressBar pb;
    private boolean state;
//    private FusedLocationProviderClient fusedLocationProviderClient;


    public AddingDeliveryPointFragment() {

    }
//    protected void setFusedLocationProviderClient(FusedLocationProviderClient f){
//        fusedLocationProviderClient = f;
//    }
    protected void setState(boolean b){
        state = b;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adding_delivery_point, container, false);
        nameEt = view.findViewById(R.id.addDeliveryPoint_name_et);
        longitudeEt = view.findViewById(R.id.addDeliveryPoint_longitude_et);
        latitudeEt = view.findViewById(R.id.addDeliveryPoint_latitude_et);
        addBtn = view.findViewById(R.id.addDeliveryPoint_add_btn);
        pb = view.findViewById(R.id.addDeliveryPoint_pb);
        pb.setVisibility(View.GONE);
        setState(true);
        // initialize fusedLocationProviderClient
//        setFusedLocationProviderClient(LocationServices.getFusedLocationProviderClient(getActivity()));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                addBtn.setEnabled(false);
                if (nameEt.getText().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    nameEt.setError("Name is required");
                    nameEt.requestFocus();
                }else if(longitudeEt.getText().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    longitudeEt.setError("Longitude is required");
                    longitudeEt.requestFocus();
                }else if(latitudeEt.getText().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    latitudeEt.setError("Latitude is required");
                    latitudeEt.requestFocus();
                }
                else{
                    save(view,null, nameEt,longitudeEt, latitudeEt);
                }
            }
        });
        return view;
    }
    public void save(View v, DeliveryPoint dp, EditText nameEt, EditText longitudeEt, EditText latitudeEt){
        DeliveryPoint deliveryP = new DeliveryPoint();
        deliveryP.setDeliveryPointName(nameEt.getText().toString());
        deliveryP.setLongitude(Double.parseDouble(longitudeEt.getText().toString()));
        deliveryP.setLatitude(Double.parseDouble(latitudeEt.getText().toString()));
        Model.getInstance().addNewDeliveryPoint(deliveryP, (ifSuccess) -> {
            if(ifSuccess) {
                @NonNull NavDirections action = AddingDeliveryPointFragmentDirections.actionAddingDeliveryPointFragmentToMangerMainScreenFragment();
                Navigation.findNavController(v).navigate(action);
            }
            else{
                Toast.makeText(getActivity(), "failed to adding delivery point to database", Toast.LENGTH_LONG).show();
                pb.setVisibility(View.GONE);
                addBtn.setEnabled(true);
            }
        });
    }

//    @Override
//    public void failAction() {
//        super.failAction();
//        pb.setVisibility(View.GONE);
//        reportBtn.setEnabled(true);
//        Toast.makeText(getActivity(), "failed to add the report, please try again", Toast.LENGTH_LONG).show();
//    }
}