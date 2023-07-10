package com.example.packtaxi;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.packtaxi.model.Driver;
import com.example.packtaxi.model.Model;
import com.example.packtaxi.model.Package;

public class driverRatingFragment extends Fragment {
    private ImageButton star1;
    private ImageButton star2;
    private ImageButton star3;
    private ImageButton star4;
    private ImageButton star5;
    private double rate=0;
    private Button sendBtn;
    private driverRatingViewModel viewModel;

    public driverRatingFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(driverRatingViewModel.class);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_driver_rating, container, false);
        star1 = view.findViewById(R.id.star1);
        star2 = view.findViewById(R.id.star2);
        star3 = view.findViewById(R.id.star3);
        star4 = view.findViewById(R.id.star4);
        star5 = view.findViewById(R.id.star5);
        sendBtn=view.findViewById(R.id.driverRating_btn);

        viewModel.setDriverId(driverRatingFragmentArgs.fromBundle(getArguments()).getDriver());
        viewModel.setPackageId(driverRatingFragmentArgs.fromBundle(getArguments()).getPackageName());


        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    star1.setImageResource(R.drawable.yellow_star);
                    star2.setImageResource(R.drawable.empty_star);
                    star3.setImageResource(R.drawable.empty_star);
                    star4.setImageResource(R.drawable.empty_star);
                    star5.setImageResource(R.drawable.empty_star);
                    rate = 1;

            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.yellow_star);
                star2.setImageResource(R.drawable.yellow_star);
                star3.setImageResource(R.drawable.empty_star);
                star4.setImageResource(R.drawable.empty_star);
                star5.setImageResource(R.drawable.empty_star);
                rate=2;
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.yellow_star);
                star2.setImageResource(R.drawable.yellow_star);
                star3.setImageResource(R.drawable.yellow_star);
                star4.setImageResource(R.drawable.empty_star);
                star5.setImageResource(R.drawable.empty_star);
                rate=3;
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.yellow_star);
                star2.setImageResource(R.drawable.yellow_star);
                star3.setImageResource(R.drawable.yellow_star);
                star4.setImageResource(R.drawable.yellow_star);
                star5.setImageResource(R.drawable.empty_star);
                rate=4;
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(R.drawable.yellow_star);
                star2.setImageResource(R.drawable.yellow_star);
                star3.setImageResource(R.drawable.yellow_star);
                star4.setImageResource(R.drawable.yellow_star);
                star5.setImageResource(R.drawable.yellow_star);
                rate=5;
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d = viewModel.getDriverId();
                Log.d("TAG", "driver email dddd"+ d);
                String pac = viewModel.getPackageId();
                Model.getInstance().getPackageByID(pac, new Model.getPackageByIDListener() {
                            @Override
                            public void onComplete(Package p) {
                                if (p != null) {
                                    p.setIfRate(true);
                                    Log.d("TAG", "ppppppp"+ p.getIfRate());
                                    Model.getInstance().getDriverByEmail(d, new Model.getDriverByEmailListener() {
                                        @Override
                                        public void onComplete(Driver driver) {
                                            if (driver != null) {
                                                Log.d("TAG", "ddriverrrrrr"+ driver.getEmail());
                                                if((driver.getRate() + rate) / 2 >= 4.5){
                                                    driver.setRate(5);
                                                }
                                                else {
                                                    driver.setRate((driver.getRate() + rate) / 2);
                                                }

                                                Log.d("TAG", "dddddd"+ driver.getRate());
                                                Model.getInstance().updateRateDriver(driver, p, new Model.updateRateDriverListener() {
                                                    @Override
                                                    public void onComplete(boolean ifSuccess) {
                                                        Log.d("TAG", "update rate driver");
                                                    }
                                                });
                                            }
                                            Navigation.findNavController(v).navigate(driverRatingFragmentDirections.actionDriverRatingFragmentToMainScreenSenderFragment());

                                    }
                                });
                            }
                            }
                        });

                        }
            });


        return  view;
    }
}