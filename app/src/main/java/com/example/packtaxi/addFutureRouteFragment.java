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
import android.widget.TextView;
import android.widget.Toast;

import com.example.packtaxi.model.DeliveryPoint;
import com.example.packtaxi.model.FutureRoute;
import com.example.packtaxi.model.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class addFutureRouteFragment extends Fragment {
    private Button addBtn;
    private TextView selectPointsTV;
    private CalendarView dateEt;
    private EditText costEt;
    private ProgressBar pb;
    private boolean state;
    boolean[] selectedPoints;
    ArrayList<Integer> pointsList = new ArrayList<>();
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
        selectPointsTV = view.findViewById(R.id.selectPoints_tv);
        Model.getInstance().getDPs((ifSuccess) -> {
            if(ifSuccess.isEmpty()) {
                String[] list=new String[ifSuccess.size()];
                for(int i=0;i<ifSuccess.size();i++){
                    list[i]=ifSuccess.get(i);
                    Log.d("TAG",ifSuccess.get(i));
                }
        selectPointsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(null);
                 //set title
                builder.setTitle("Select Points");
                // set dialog non cancelable
                builder.setCancelable(false);
                builder.setMultiChoiceItems(list, selectedPoints, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            pointsList.add(i);
                            // Sort array list
                            Collections.sort(pointsList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            pointsList.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < pointsList.size(); j++) {
                            // concat array value
                            stringBuilder.append(list[pointsList.get(j)]);
                            // check condition
                            if (j != pointsList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
// set text on textView
                        selectPointsTV.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedPoints.length; j++) {
                            // remove all selection
                            selectedPoints[j] = false;
                            // clear language list
                            pointsList.clear();
                            // clear text view value
                            selectPointsTV.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

//        Model.getInstance().getDPs((ifSuccess) -> {
//            if(ifSuccess.isEmpty()) {
//                String[] list=new String[ifSuccess.size()];
//                for(int i=0;i<ifSuccess.size();i++){
//                    list[i]=ifSuccess.get(i);
//                    Log.d("TAG",ifSuccess.get(i));
//                }
//                ArrayAdapter<String> adapterS = new ArrayAdapter<String>(list, android.R.layout.simple_spinner_item);
//                adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                sourceSpin.setAdapter(adapterS);
            }
        });
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
                if (selectPointsTV.toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "Points is required", Toast.LENGTH_LONG).show();
                    selectPointsTV.requestFocus();
//                }else if((dateEt.getDate()).equals("")) {
//                    pb.setVisibility(View.GONE);
//                    addBtn.setEnabled(true);
//                    Toast.makeText(getActivity(), "Date is required", Toast.LENGTH_LONG).show();
//                    dateEt.requestFocus();
                }else if(costEt.getText().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    costEt.setError("Cost is required");
                    costEt.requestFocus();
                }
                else{
                    save(view,null, selectPointsTV, dateEt,costEt);
                }
            }
        });
        return view;
    }
    public void save(View v, FutureRoute r, TextView selectPointsTV, CalendarView dateEt, EditText costEt){
        FutureRoute route = new FutureRoute();
        route.setSource(selectPointsTV.toString());
        route.setDate(dateEt.getDate());// not work
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