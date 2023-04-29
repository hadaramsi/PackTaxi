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
import com.example.packtaxi.model.FutureRoute;
import com.example.packtaxi.model.Model;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;

public class addFutureRouteFragment extends Fragment {
    private Button addBtn;
    private Spinner source;
    private Spinner destination1;
    private Spinner destination2;
    private Spinner destination3;
    private Spinner destination4;
    private String date;
    private CalendarView CalendarView;
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
        CalendarView = view.findViewById(R.id.calendarView);
        costEt = view.findViewById(R.id.addDrive_cost_et);
        source = view.findViewById(R.id.source1);
        destination1 = view.findViewById(R.id.des1);
        destination2 = view.findViewById(R.id.des2);
        destination3 = view.findViewById(R.id.des3);
        destination4 = view.findViewById(R.id.des4);
        addBtn = view.findViewById(R.id.addDrive_add_btn);
        pb = view.findViewById(R.id.addDrive_pb);
        pb.setVisibility(View.GONE);
        setState(true);
        // initialize fusedLocationProviderClient
//        setFusedLocationProviderClient(LocationServices.getFusedLocationProviderClient(getActivity()));

        Model.getInstance().getDPStringList( (list)->{
            list.add(0,"");
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MyApplication.getContext(), android.R.layout.simple_spinner_item, list);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            source.setAdapter(adapter1);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MyApplication.getContext(), android.R.layout.simple_spinner_item, list);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            destination1.setAdapter(adapter2);
            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(MyApplication.getContext(), android.R.layout.simple_spinner_item, list);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            destination2.setAdapter(adapter3);
            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(MyApplication.getContext(), android.R.layout.simple_spinner_item, list);
            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            destination3.setAdapter(adapter4);
            ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(MyApplication.getContext(), android.R.layout.simple_spinner_item, list);
            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            destination4.setAdapter(adapter5);
        });
        CalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + month + "/" + year ;
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                addBtn.setEnabled(false);
                if (source.getSelectedItem().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "source is required", Toast.LENGTH_LONG).show();
                    source.requestFocus();
                }if (destination1.getSelectedItem().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "first destination is required", Toast.LENGTH_LONG).show();
                    destination1.requestFocus();
                }else if((date).equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "Date is required", Toast.LENGTH_LONG).show();
                    CalendarView.requestFocus();
                }else if(costEt.getText().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    costEt.setError("Cost is required");
                    costEt.requestFocus();
                }
                else{
                    save(view,null, source, destination1,date,costEt);
                    if(!(destination2.getSelectedItem().toString().equals(""))){
                        save(view,null, source, destination2,date,costEt);
                        save(view,null, destination1, destination2,date,costEt);
                    }
                    if(!(destination3.getSelectedItem().toString().equals("")) && !(destination2.getSelectedItem().toString().equals(""))){
                        save(view,null, source, destination3,date,costEt);
                        save(view,null, destination2, destination3,date,costEt);
                        save(view,null, destination1, destination3,date,costEt);
                    }
                    if(!(destination4.getSelectedItem().toString().equals("")) && !(destination2.getSelectedItem().toString().equals(""))&&!(destination4.getSelectedItem().toString().equals(""))){
                        save(view,null, source, destination4,date,costEt);
                        save(view,null, destination1, destination4,date,costEt);
                        save(view,null, destination3, destination4,date,costEt);
                        save(view,null, destination2, destination4,date,costEt);
                    }
                    @NonNull NavDirections action = addFutureRouteFragmentDirections.actionAddFutureRoudFragmentToMainScreenDriverFragment();
                    Navigation.findNavController(v).navigate(action);
                }
            }

        });
        return view;
    }
    public void save(View v, FutureRoute r, Spinner source,Spinner destination, String date, EditText costEt){
        FutureRoute route = new FutureRoute();
        Model.getInstance().getCurrentDriver(new Model.getCurrentDriverListener() {
            @Override
            public void onComplete(String driverEmail) {
                route.setCost(Double.parseDouble(costEt.getText().toString()));
                route.setSource(source.getSelectedItem().toString());
                route.setDestination(destination.getSelectedItem().toString());
                route.setDate(date);
                route.setDriver(driverEmail);
                if(r!= null && r.getFutureRouteID() != null)
                    route.setFutureRouteID(r.getFutureRouteID());
                else {
                    String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    route.setFutureRouteID(currentTime+route.getSource()+route.getDestination());
                }
                Model.getInstance().addNewRoute(route, (ifSuccess) -> {
                    if(ifSuccess) {
                        Toast.makeText(getActivity(), "Adding future route", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "failed to adding route to database", Toast.LENGTH_LONG).show();
                        pb.setVisibility(View.GONE);
                        addBtn.setEnabled(true);
                    }
                });
            }
        });
    }

}