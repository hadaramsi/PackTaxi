package com.example.packtaxi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.packtaxi.model.Model;


public class fragmentLogin extends Fragment {
    EditText username;
    EditText password;
    final static int PASSWORDMINDIGIT = 6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = view.findViewById(R.id.userName_login);
        password = view.findViewById(R.id.password_login);
        Button logInBtn = view.findViewById(R.id.logIn_btn_login);
        ProgressBar pb = view.findViewById(R.id.logIn_progressBar);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                logInBtn.setEnabled(false);
                if (checkDetails()) {
                    Model.getInstance().loginUser(username.getText().toString().trim(),
                            password.getText().toString().trim(), new Model.loginUserListener() {
                                @Override
                                public void onComplete(boolean success) {

                                    if(success) {

                                        //@NonNull NavDirections action = fragmentLoginDirections.action;
                                        Navigation.findNavController(v).navigate(R.id.action_fragmentLogin_to_managerMainScreenFragment);
                                    }
                                    else {
                                        Toast.makeText(getActivity(), "failed to login, please check your credentials", Toast.LENGTH_LONG).show();
                                        pb.setVisibility(View.GONE);
                                        logInBtn.setEnabled(true);
                                    }
                                }
                            });
                }
                else
                { pb.setVisibility(View.GONE);
                    logInBtn.setEnabled(true);
                }
            }
        });
        pb.setVisibility(View.GONE);
        return view;
    }


    private boolean checkDetails() {
        String userName = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if (userName.isEmpty()) {
            username.setError("userName is required");
            username.requestFocus();
            return false;
        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
//            username.setError("Please provide valid userName");
//            username.requestFocus();
//            return false;
//        }

        if (pass.isEmpty()) {
            password.setError("password is required");
            password.requestFocus();
            return false;
        }
        if (password.length() < PASSWORDMINDIGIT) {
            password.setError("password should be at least 6 characters");
            password.requestFocus();
            return false;
        }
        return true;
    }
}