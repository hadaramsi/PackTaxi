package com.example.packtaxi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.packtaxi.model.DeliveryPoint;
import com.example.packtaxi.model.FutureRoute;
import com.example.packtaxi.model.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class addFutureRouteFragment extends Fragment {
    private Button addBtn;
    private Spinner sourceSpin;
    private Spinner destinationSpin;
    private CalendarView dateEt;
    private EditText costEt;
    private ProgressBar pb;
    private boolean state;
    public addFutureRouteFragment() {
    }
    protected void setState(boolean b){
        state = b;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_future_route, container, false);
        sourceSpin = (Spinner)view.findViewById(R.id.source_spinner);
        Model.getInstance().getDPs((ifSuccess) -> {
            if(ifSuccess.isEmpty()) {
                String[] list=new String[ifSuccess.size()];
                for(int i=0;i<ifSuccess.size();i++){
                    list[i]=ifSuccess.get(i);
                    Log.d("TAG",ifSuccess.get(i));
                }
//                ArrayAdapter<String> adapterS = new ArrayAdapter<String>(list, android.R.layout.simple_spinner_item);
//                adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                sourceSpin.setAdapter(adapterS);
            }
        });
        destinationSpin = (Spinner)view.findViewById(R.id.des_spinner);
        dateEt = view.findViewById(R.id.calendarView);
        costEt = view.findViewById(R.id.addDrive_cost_et);
        addBtn = view.findViewById(R.id.addDrive_add_btn);
        pb = view.findViewById(R.id.addDrive_pb);
        pb.setVisibility(View.GONE);
        setState(true);
        // initialize fusedLocationProviderClient
//        setFusedLocationProviderClient(LocationServices.getFusedLocationProviderClient(getActivity()));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                addBtn.setEnabled(false);
                if (sourceSpin.getSelectedItem().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "Source is required", Toast.LENGTH_LONG).show();
                    sourceSpin.requestFocus();
                }else if(destinationSpin.getSelectedItem().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "Destination is required", Toast.LENGTH_LONG).show();
                    destinationSpin.requestFocus();
                }else if(new SimpleDateFormat("yy-MM-dd").format(dateEt.getDate()).equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "Date is required", Toast.LENGTH_LONG).show();
                    dateEt.requestFocus();
                }else if(costEt.getText().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    costEt.setError("Cost is required");
                    costEt.requestFocus();
                }
                else{
                    save(view,null, sourceSpin,destinationSpin, dateEt,costEt);
                }
            }
        });
        return view;
    }
    public void save(View v, FutureRoute r, Spinner sourceSpin, Spinner destinationEt, CalendarView dateEt, EditText costEt){
        FutureRoute route = new FutureRoute();
        route.setSource(sourceSpin.getSelectedItem().toString());
        route.setDestination(destinationEt.getSelectedItem().toString());
        route.setDate(new SimpleDateFormat("yy-MM-dd").format(dateEt.getDate()));//new SimpleDateFormat("yyyy-MM-dd").format(dateEt.getText())
        route.setCost(Double.parseDouble(costEt.getText().toString()));
        Model.getInstance().addNewRoute(route, (ifSuccess) -> {
            if(ifSuccess) {
                @NonNull NavDirections action = addFutureRouteFragmentDirections.actionAddFutureRoudFragmentToMainScreenDriverFragment();
                Navigation.findNavController(v).navigate(action);
            }
            else{
                Toast.makeText(getActivity(), "failed to adding route to database", Toast.LENGTH_LONG).show();
                pb.setVisibility(View.GONE);
                addBtn.setEnabled(true);
            }
        });
    }

}