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

public class signUpFragment extends Fragment {
    public signUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        Button signUpAsSenderBtn = view.findViewById(R.id.signUpAs_Sender_btn);
        Button signUpAsDriverBtn = view.findViewById(R.id.SignUpAs_Drive_btn);

        signUpAsSenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @NonNull NavDirections action = signUpFragmentDirections.actionSignUpFragmentToFragmentSignUpAsSender();
                Navigation.findNavController(v).navigate(action);
            }
        });

        signUpAsDriverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @NonNull NavDirections action = signUpFragmentDirections.actionSignUpFragmentToFragmentSignUpAsDriver();
                Navigation.findNavController(v).navigate(action);
            }
        });
        return view;
    }

}