package com.example.packtaxi;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
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
import com.example.packtaxi.model.Model;
import com.example.packtaxi.model.Package;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class addPackageFragment extends Fragment {
    private Button addBtn;
    private Spinner source;
    private Spinner destination;
    private EditText weight;
    private EditText volume;
    private CalendarView CalendarView;
    private String date;
    private String sender;

    private EditText cost;
    private EditText notes;
    private ProgressBar pb;

    public addPackageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_package, container, false);
        addBtn = view.findViewById(R.id.addPackage_add_btn);
        source = view.findViewById(R.id.sour_spinner);
        destination = view.findViewById(R.id.spinner2);
        weight = view.findViewById(R.id.addPackage_weight_et);
        volume = view.findViewById(R.id.addPackage_volume_et);
        CalendarView = view.findViewById(R.id.calendarView2);
        cost = view.findViewById(R.id.addPackage_cost_et);
        notes = view.findViewById(R.id.addPackage_notes_et);
        pb = view.findViewById(R.id.add_pac_progressBar);

        pb.setVisibility(View.GONE);
//        setState(true);
        Model.getInstance().getDPStringList( (list)->{
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MyApplication.getContext(), android.R.layout.simple_spinner_item, list);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            source.setAdapter(adapter1);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MyApplication.getContext(), android.R.layout.simple_spinner_item, list);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            destination.setAdapter(adapter2);
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
                if (source.toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "source is required", Toast.LENGTH_LONG).show();
                    source.requestFocus();
                }if (destination.toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "destination is required", Toast.LENGTH_LONG).show();
                    destination.requestFocus();
                }else if((date).equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "Date is required", Toast.LENGTH_LONG).show();
                    CalendarView.requestFocus();
                }else if(weight.getText().toString().equals("")) {
                    pb.setVisibility(View.GONE);
                    addBtn.setEnabled(true);
                    weight.setError("weight is required");
                    weight.requestFocus();
            }else if(volume.getText().toString().equals("")) {
                pb.setVisibility(View.GONE);
                addBtn.setEnabled(true);
                volume.setError("volume is required");
                volume.requestFocus();
        }else if(cost.getText().toString().equals("")) {
            pb.setVisibility(View.GONE);
            addBtn.setEnabled(true);
            cost.setError("cost is required");
            cost.requestFocus();
        }
                else{
                    save (view,null,source, destination, date,cost,volume,weight, notes);
                }
            }
        });
        return  view;
    }

    public void save(View v, Package p,Spinner source,Spinner destination, String date,EditText cost,EditText volume,EditText weight,EditText note){
        Package pack = new Package();
        Model.getInstance().getCurrentSender(new Model.getCurrentSenderListener() {
            @Override
            public void onComplete(String senderEmail) {
                pack.setSource(source.getSelectedItem().toString());
                pack.setDestination(destination.getSelectedItem().toString());
                pack.setWeight(Double.parseDouble(weight.getText().toString()));
                pack.setVolume(Double.parseDouble(volume.getText().toString()));
                pack.setDate(date);
                pack.setSender(senderEmail);
                pack.setCost(Double.parseDouble(cost.getText().toString()));
                pack.setNote(note.getText().toString());
                if(p!= null && p.getPackageID() != null)
                    pack.setPackageID(p.getPackageID());
                else {
                    String currentTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
                    pack.setPackageID(currentTime);
                }
                Model.getInstance().addNewPack(pack, (ifSuccess) -> {
                    if(ifSuccess) {
                        @NonNull NavDirections action = addPackageFragmentDirections.actionAddPackageFragmentToMainScreenSenderFragment();
                        Navigation.findNavController(v).navigate(action);
                    }
                    else{
                        Toast.makeText(getActivity(), "failed to adding package to database", Toast.LENGTH_LONG).show();
                        pb.setVisibility(View.GONE);
                        addBtn.setEnabled(true);
                    }
                });
            }
        });
    }
}