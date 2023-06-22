package com.example.packtaxi;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.packtaxi.model.FutureRoute;
import com.example.packtaxi.model.Model;

public class futureRouteDetailsFragment extends Fragment {
    private futureRouteDetailsViewModel viewModel;
    private View view;
    private TextView sourceTv;
    private TextView destinationTv;
    private TextView dateTv;
    private TextView costTv;
    private TextView packagesList;
    private Button deleteBtn;
    private FutureRoute fRoute;
    ProgressBar pb;

    public futureRouteDetailsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(futureRouteDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_future_route_details, container, false);
        sourceTv = view.findViewById(R.id.driveDetails_source_tv);
        destinationTv = view.findViewById(R.id.driveDetails_destination_tv);
        dateTv = view.findViewById(R.id.driveDetails_date_tv);
        costTv = view.findViewById(R.id.driveDetails_cost_tv);
        packagesList = view.findViewById(R.id.driveDetails_packages_numbers);
        pb = view.findViewById(R.id.progressBar2);
        deleteBtn=view.findViewById(R.id.deleteBtn_drive);
//        editBtn=view.findViewById(R.id.editDriveBtn);

        viewModel.setRouteId(futureRouteDetailsFragmentArgs.fromBundle(getArguments()).getRouteID());

        String routeId = futureRouteDetailsFragmentArgs.fromBundle(getArguments()).getRouteID();
        Model.getInstance().getRouteByID(routeId, new Model.getRouteByIDListener() {
            @Override
            public void onComplete(FutureRoute f) {
                fRoute=f;
                if(f != null) {
                    updateReportDetailsDisplay(f);
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                Model.getInstance().deleteFutureRoute(fRoute, new Model.deleteFutureRouteListener() {
                    @Override
                    public void onComplete(boolean ifSuccess) {
                        if(ifSuccess) {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "delete route", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(), "fail delete route", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                Navigation.findNavController(v).navigate(futureRouteDetailsFragmentDirections.actionFutuerRoudDetailsFragmentToMainScreenDriverFragment());
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    public void updateUserDetailsDisplay(FutureRoute f)
    {
        pb.setVisibility(View.GONE);
        sourceTv.setText(f.getSource());
        destinationTv.setText(f.getDestination());
        costTv.setText(f.getCost()+ "₪ per delivery");
        dateTv.setText(f.getDate());
        if(!f.getPackagesNumbers().equals(",")) {
            deleteBtn.setEnabled(false);
        }

        packagesList.setText(f.getPackagesNumbers().toString());
    }

    public void updateReportDetailsDisplay(FutureRoute f) {
        Model.getInstance().getRouteByID(f.getFutureRouteID(), new Model.getRouteByIDListener() {
            @Override
            public void onComplete(FutureRoute f) {
                if (f != null) {
                    updateUserDetailsDisplay(f);
                }
            }
        });
        sourceTv.setText(f.getSource());
        destinationTv.setText(f.getDestination());
        costTv.setText(f.getCost()+ "₪ per km");
        dateTv.setText(f.getDate());
        pb.setVisibility(View.GONE);
    }


}