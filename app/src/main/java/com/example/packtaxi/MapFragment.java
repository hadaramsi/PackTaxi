package com.example.packtaxi;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
//import com.example.packtaxi.Model.Model;
//import com.example.packtaxi.Model.DeliveryPoints;
import com.example.packtaxi.model.DeliveryPoint;
import com.example.packtaxi.model.Model;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment{

    static final double ISRAELLATITUDE = 31.3555;
    static final double ISRAELLONGITUDE = 34.3565;
    static final float ISRAELZOOMLEVEL = 7.0f;
    static final float REPORTZOOMLEVEL = 11.0f;

    private GoogleMap gMap;
    private MapViewModel viewModel;
    private View view;

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
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        viewModel.setDeliveryPointID(MapFragmentArgs.fromBundle(getArguments()).getReportID());

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                if(viewModel.getDeliveryPointID() == null) {
                    for(DeliveryPoint dp : Model.getInstance().getAllDeliveryPoints().getValue())
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(dp.getLatitude(), dp.getLongitude())).title("Marker in " + dp.getLocation())).setTag(dp.getDeliveryPointID());
                    LatLng Israel = new LatLng(ISRAELLATITUDE, ISRAELLONGITUDE);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Israel, ISRAELZOOMLEVEL));
                }
                else{
                    Model.getInstance().getDeliveryPointByID(viewModel.getDeliveryPointID(), new Model.getDeliveryPointByIDListener() {
                        @Override
                        public void onComplete(DeliveryPoint dp) {
                            LatLng reportLocation = new LatLng(dp.getLatitude(), dp.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(reportLocation).title("Marker in " + dp.getLocation())).setTag(dp.getDeliveryPointID());
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(reportLocation, REPORTZOOMLEVEL));
                        }
                    });
                }

                gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
//                        Navigation.findNavController(view).navigate(MapFragmentDirections.actionMapFragmentToViewReportFragment(marker.getTag().toString()));
                    }
                });
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.add_menu, menu);
//        inflater.inflate(R.menu.my_profile_menu, menu);
//        inflater.inflate(R.menu.my_reports_menu, menu);
//        inflater.inflate(R.menu.log_out_menu, menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.addMenu_addDeliveryPoint:
//                @NonNull NavDirections action = MapFragmentDirections.actionMapFragmentToAddingReportFragment();
//                Navigation.findNavController(view).navigate(action);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
