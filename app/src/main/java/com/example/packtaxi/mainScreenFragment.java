package com.example.packtaxi;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
public class mainScreenFragment extends Fragment {
    private View view;
    public mainScreenFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        Button signUpBtn = view.findViewById(R.id.mainScreen_btn_signUp);
        Button loginBtn = view.findViewById(R.id.mainScreen_btn_logIn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragmentMainScreen_to_signUpFragment);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragmentMainScreen_to_fragmentLogin);
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
                Navigation.findNavController(view).navigate(mainScreenFragmentDirections.actionFragmentMainScreenToAboutUsFragment2());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}