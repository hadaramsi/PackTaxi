package com.example.packtaxi;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.packtaxi.model.DeliveryPoint;
import com.example.packtaxi.model.Model;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class managerMainScreenFragment extends Fragment {
    static final double ISRAELLATITUDE = 31.765;
    static final double ISRAELLONGITUDE = 34.787;
    static final float ISRAELZOOMLEVEL = 8.0f;
    static final float DELIVERYPOINTZOOMLEVEL = 11.0f;

    private GoogleMap gMap;
    private MapViewModel viewModel;
    private View view;
    private Menu mMenu;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manager_main_screen, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                if(Model.getInstance().getAllDeliveryPoints().getValue()!= null) {
                    for (DeliveryPoint dp : Model.getInstance().getAllDeliveryPoints().getValue()){
                        if(dp.getIsDeleted()== false)
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(dp.getLatitude(), dp.getLongitude())).title("Marker in " + dp.getLocation())).setTag(dp.getDeliveryPointName());
                    }
                    LatLng Israel = new LatLng(ISRAELLATITUDE, ISRAELLONGITUDE);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Israel, ISRAELZOOMLEVEL));
                }
                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Navigation.findNavController(view).navigate(managerMainScreenFragmentDirections.actionMangerMainScreenFragmentToViewdeliveryPointDetailsFragment(marker.getTag().toString()));
                        return false;
                    }
                });
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.manager_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_delivery_point:
                @NonNull NavDirections action = managerMainScreenFragmentDirections.actionMangerMainScreenFragmentToAddingDeliveryPointFragment();
                Navigation.findNavController(view).navigate(action);
                return true;
//            case R.id.search_delivery_point:
//                MenuItem searchItem = mMenu.findItem(R.id.search_delivery_point);
//                String dpName=searchItem.getIntent().getAction();
//                Model.getInstance().getDeliveryPointByName(dpName, (dp)->{
//                    if(dp!= null && dp.getIsDeleted()==false) {
//                        LatLng location = new LatLng(dp.getLatitude(), dp.getLongitude());
//                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, DELIVERYPOINTZOOMLEVEL));
//                    }
//                else{
//                Toast.makeText(getActivity(), "adding route to database", Toast.LENGTH_LONG).show();
//            }
//                });
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}