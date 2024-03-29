package com.example.packtaxi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
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

import com.example.packtaxi.model.Model;
import com.example.packtaxi.model.Sender;
public class senderProfileFragment extends Fragment {
    public senderProfileFragment() {
    }
    TextView fullNameTv;
    TextView emailTv ;
    View view;
    ProgressBar pb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sender_profile, container, false);
        fullNameTv = view.findViewById(R.id.senderProfile_fullName_info_tv);
        emailTv = view.findViewById(R.id.senderProfile_email_info_tv);
        pb = view.findViewById(R.id.sender_profile_progressBar);
        pb.setVisibility(View.VISIBLE);
        Model.getInstance().getCurrentSender(new Model.getCurrentSenderListener() {
            @Override
            public void onComplete(String senderEmail) {
                Log.d("TAG","senderEmail: "+senderEmail);
                Model.getInstance().getSenderByEmail(senderEmail, new Model.getSenderByEmailListener() {
                    @Override
                    public void onComplete(Sender sender) {
                        Log.d("TAG","on complete");
                        setDetails(sender);
                    }
                });
            }
        });
        return view;
    }
    public void setDetails(Sender s){
        Log.d("TAG","on set");
        fullNameTv.setText(s.getFullName());
        emailTv.setText(s.getEmail());
        pb.setVisibility(View.GONE);
    }
}