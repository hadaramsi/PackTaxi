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

import com.example.packtaxi.model.DeliveryPoint;
import com.example.packtaxi.model.Model;
import com.example.packtaxi.model.Package;

public class packageDetailsFragment extends Fragment {
    private packageDetailsViewModel viewModel;
    private View view;
    private TextView sourceTv;
    private TextView destinationTv;
    private TextView dateTv;
    private TextView weightTv;
    private TextView volumeTv;
    private TextView noteTv;
    private TextView driverTv;
    private TextView rateDriverTv;
    private CheckBox match;
    private TextView costTv;
    private Button deleteBtn;
    private Button editBtn;
    private Package pac;
    ProgressBar pb;

    public packageDetailsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(packageDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_package_details, container, false);
        sourceTv = view.findViewById(R.id.packageDetails_source_tv);
        destinationTv = view.findViewById(R.id.packageDetails_destination_tv);
        dateTv = view.findViewById(R.id.packageDetails_date_tv);
        costTv = view.findViewById(R.id.packageDetails_cost_tv);
        noteTv = view.findViewById(R.id.packageDetails_notes_tv);
        weightTv = view.findViewById(R.id.packageDetails_weight_tv);
        volumeTv = view.findViewById(R.id.packageDetails_volume_tv);
        rateDriverTv = view.findViewById(R.id.packageDetails_rateDriver_tv);
        driverTv = view.findViewById(R.id.packageDetails_driver_tv);
        match = view.findViewById(R.id.packageDetails_match_cb);
        deleteBtn=view.findViewById(R.id.rateBtn);
//        editBtn=view.findViewById(R.id.editBtn);


        viewModel.setPackageId(packageDetailsFragmentArgs.fromBundle(getArguments()).getPackID());

        String packageId = packageDetailsFragmentArgs.fromBundle(getArguments()).getPackID();
        Model.getInstance().getPackageByID(packageId, new Model.getPackageByIDListener() {
            @Override
            public void onComplete(Package p) {
                pac=p;
                if(p != null) {
                    updatePackageDetailsDisplay(p);
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                Model.getInstance().deletePackage(pac, new Model.deletePackageListener() {
                            @Override
                            public void onComplete(boolean ifSuccess) {
                                if(ifSuccess) {
                                    pb.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "delete package", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getActivity(), "fail delete package", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                Navigation.findNavController(v).navigate(packageDetailsFragmentDirections.actionPackageDetailsFragmentToMainScreenSenderFragment());
            }
        });
        return view;
    }

    public void updateDetailsDisplay(Package p) {
        sourceTv.setText(p.getSource());
        destinationTv.setText(p.getDestination());
        costTv.setText(p.getCost()+ " ₪");
        dateTv.setText(p.getDate());
        noteTv.setText(p.getNote());
        weightTv.setText(p.getWeight()+" kg");
        volumeTv.setText(p.getVolume()+ " cc");
        driverTv.setText(p.getDriver());
        if(p.getDriver()!= "") {
            match.setChecked(true);
            deleteBtn.setEnabled(false);
//            editBtn.setEnabled(false);
        }
        else
            match.setChecked(false);
        rateDriverTv.setText(p.getRate()+" stars");

    }

    public void updatePackageDetailsDisplay(Package p) {
        Model.getInstance().getPackageByID(p.getPackageID(), new Model.getPackageByIDListener() {
            @Override
            public void onComplete(Package p) {
                if (p != null) {
                    updateDetailsDisplay(p);
                }
            }
        });
        sourceTv.setText(p.getSource());
        destinationTv.setText(p.getDestination());
        costTv.setText(p.getCost()+ "₪");
        dateTv.setText(p.getDate());
        noteTv.setText(p.getNote());
        weightTv.setText(p.getWeight()+"kg");
        volumeTv.setText(p.getVolume()+ "cc");
        driverTv.setText(p.getDriver());
        if(p.getDriver()!= "")
            match.setChecked(true);
        else
            match.setChecked(false);
        rateDriverTv.setText(p.getRate()+" stars");
    }
}