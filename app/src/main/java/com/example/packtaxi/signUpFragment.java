package com.example.packtaxi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class signUpFragment extends Fragment {
    private View view;
    public signUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);

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
        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.base_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutAs_menu:
                Navigation.findNavController(view).navigate(signUpFragmentDirections.actionSignUpFragmentToAboutUsFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}