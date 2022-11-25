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
import com.example.packtaxi.model.Sender;

public class signUpAsSenderFragment extends Fragment {

    private Button signUpBtn;
    private ProgressBar pb;
    private EditText emailEt;
    private EditText passwordEt;
    private EditText fullNameEt;
    final static int PASSWORDMINDIGIT = 6;

    public signUpAsSenderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_as_sender, container, false);
        signUpBtn = view.findViewById(R.id.signUpSender_btn);
        emailEt = view.findViewById(R.id.signUpSender_email_et);
        passwordEt = view.findViewById(R.id.signUpSender_password_et);
        fullNameEt = view.findViewById(R.id.signUpSender_fullName_et);
        pb = view.findViewById(R.id.signUpSender_pb);
        pb.setVisibility(View.GONE);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(false);
                if (checkDetails()) {
                    registerSender(v);
                }
                else {
                    pb.setVisibility(View.GONE);
                    signUpBtn.setEnabled(true);
                }
            }
        });
        return view;
    }
    private void registerSender(View v)
    {
        String password = passwordEt.getText().toString().trim();
        Sender sender = new Sender();
        sender.setFullName(fullNameEt.getText().toString().trim());
        sender.setEmail(emailEt.getText().toString().trim());
        sender.setPassword(passwordEt.getText().toString().trim());
        Model.getInstance().addSender(sender, password, (ifSuccess) -> {
            if(ifSuccess) {
                @NonNull NavDirections action = signUpAsSenderFragmentDirections.actionFragmentSignUpAsSenderToMainScreenSenderFragment();
                Navigation.findNavController(v).navigate(action);
            }
            else{
                Toast.makeText(getActivity(), "failed to register, please change the email", Toast.LENGTH_LONG).show();
                pb.setVisibility(View.GONE);
                signUpBtn.setEnabled(true);
            }
        });
    }
    private boolean checkDetails()
    {
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String fullName = fullNameEt.getText().toString().trim();
        if(fullName.isEmpty()) {
            fullNameEt.setError("full name is required");
            fullNameEt.requestFocus();
            return false;
        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
//            email.setError("Please provide valid email");
//            email.requestFocus();
//            return false;
//        }
        if(email.isEmpty()) {
            emailEt.setError("email is required");
            emailEt.requestFocus();
            return false;
        }
        if(password.isEmpty()) {
            passwordEt.setError("password is required");
            passwordEt.requestFocus();
            return false;
        }
        if(password.length() < PASSWORDMINDIGIT) {
            passwordEt.setError("password should be at least 6 characters");
            passwordEt.requestFocus();
            return false;
        }
        return true;
    }
}