package com.example.packtaxi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class mainScreenSenderFragment extends Fragment {
    private View view;
    public mainScreenSenderFragment() {
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sender_menu, menu);
        inflater.inflate(R.menu.base_menu, menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_pack_sender:
                @NonNull NavDirections action = mainScreenSenderFragmentDirections.actionMainScreenSenderFragmentToAddPackageFragment();
                Navigation.findNavController(view).navigate(action);
                return true;
            case R.id.my_profile_sender:
                Navigation.findNavController(view).navigate(mainScreenSenderFragmentDirections.actionMainScreenSenderFragmentToSenderProfileFragment());
                return true;
            case R.id.aboutAs_menu:
                Navigation.findNavController(view).navigate(mainScreenSenderFragmentDirections.actionMainScreenSenderFragmentToAboutUsFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view= inflater.inflate(R.layout.fragment_main_screen_sender, container, false);
        return view;
    }
}