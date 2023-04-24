package com.example.packtaxi;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.packtaxi.model.DeliveryPoint;
import com.example.packtaxi.model.Model;


public class viewDeliveryPointDetailsFragment extends Fragment {
    private viewDeliveryPointDetailsViewModel viewModel;
    private View view;
    private TextView name;
    private TextView longitude;
    private TextView latitude;
    private Button remove;
    private ProgressBar pb;
    private DeliveryPoint dpp;

    public viewDeliveryPointDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(viewDeliveryPointDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_viewdelivery_point_details, container, false);
        name = view.findViewById(R.id.deliveryPointDetails_name_et);
        latitude = view.findViewById(R.id.deliveryPointDetails_latitude_et);
        longitude = view.findViewById(R.id.deliveryPointDetails_longitude_et);
        remove = view.findViewById(R.id.deliveryPointDetails_remove_btn);
        pb= view.findViewById(R.id.progressBar);
        viewModel.setDeliveryPointID(viewDeliveryPointDetailsFragmentArgs.fromBundle(getArguments()).getDeliveryPointID());

        String dpName = viewDeliveryPointDetailsFragmentArgs.fromBundle(getArguments()).getDeliveryPointID();
        Model.getInstance().getDeliveryPointByName(dpName, new Model.getDeliveryPointByIDListener() {
            @Override
            public void onComplete(DeliveryPoint dp) {
                dpp=dp;
                if(dp != null) {
                    updateDpDetailsDisplay(dp);
                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                Model.getInstance().deleteDeliveryPoint(dpp, new Model.deleteDeliveryPointListener() {
                    @Override
                    public void onComplete() {
                        pb.setVisibility(View.GONE);
                    }
                });
                Navigation.findNavController(v).navigate(viewDeliveryPointDetailsFragmentDirections.actionViewdeliveryPointDetailsFragmentToMangerMainScreenFragment());
            }
        });
        return view;
    }

    public void updateDpDetailsDisplay(DeliveryPoint dp) {
        name.setText(dp.getDeliveryPointName());
        latitude.setText(Double.toString(dp.getLatitude()));
        longitude.setText(Double.toString(dp.getLongitude()));
    }
}