package com.example.packtaxi;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.packtaxi.model.DeliveryPoint;
import com.example.packtaxi.model.Model;
import com.example.packtaxi.model.payment;


public class paymentFragment extends Fragment {
    private paymentViewModel viewModel;
    private Button payBtn;
    private EditText cardNumberEt;
    private EditText cardValidityEt;
    private EditText threeDigitsEt;
    private ProgressBar pb;
    public paymentFragment() {
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(paymentViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        cardNumberEt = view.findViewById(R.id.payment_cardnumber);
        cardValidityEt = view.findViewById(R.id.payment_valicity);
        threeDigitsEt = view.findViewById(R.id.payment_3digits);
        payBtn = view.findViewById(R.id.payment_pay_btn);
        pb = view.findViewById(R.id.payment_pb);
        pb.setVisibility(View.GONE);
        viewModel.setPackageId(paymentFragmentArgs.fromBundle(getArguments()).getPackageID());

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                payBtn.setEnabled(false);
                String cardNum= cardNumberEt.getText().toString();
                String cardVal=cardValidityEt.getText().toString();
                String digits = threeDigitsEt.getText().toString();
                if(cardNum.isEmpty()) {
                    cardNumberEt.setError("card number is required");
                    cardNumberEt.requestFocus();
                    payBtn.setEnabled(true);
                }else if(cardNum.length() < 10) {
                    cardNumberEt.setError("card number should be at least 10 digits");
                    cardNumberEt.requestFocus();
                    payBtn.setEnabled(true);
                } else if (cardVal.isEmpty()) {
                    pb.setVisibility(View.GONE);
                    cardValidityEt.setError("card validity is required");
                    cardValidityEt.requestFocus();
                    payBtn.setEnabled(true);
                }else if(cardVal.length() != 4) {
                    cardValidityEt.setError("card validity should be 4 digits");
                    cardValidityEt.requestFocus();
                    payBtn.setEnabled(true);
                }
                else if (digits.isEmpty()) {
                    pb.setVisibility(View.GONE);
                    threeDigitsEt.setError("3 digits is required");
                    threeDigitsEt.requestFocus();
                    payBtn.setEnabled(true);
                }else if(digits.length() != 3) {
                    threeDigitsEt.setError("card confirm digits should be 3 digits");
                    threeDigitsEt.requestFocus();
                    payBtn.setEnabled(true);
                }
                else{
                    save(view,null, cardNumberEt,cardValidityEt, threeDigitsEt);
                }
            }
        });
        return view;
    }
    public void save(View v, payment p, EditText cardNumberEt, EditText cardValidityEt, EditText threeDigitsEt){
        payment pay = new payment();
        String packageNumber=  viewModel.getPackageId();
        pay.setNumPackage(packageNumber);
        pay.setCardNumber(cardNumberEt.getText().toString());
        pay.setCardValidity(cardValidityEt.getText().toString());
        pay.setThreeDigits(threeDigitsEt.getText().toString());
        Model.getInstance().addNewPayment(pay, (ifSuccess) -> {
            if(ifSuccess) {
                @NonNull NavDirections action = paymentFragmentDirections.actionPaymentFragmentToMainScreenSenderFragment();
                Navigation.findNavController(v).navigate(action);
            }
            else{
                Toast.makeText(getActivity(), "failed to save payment in database", Toast.LENGTH_LONG).show();
                pb.setVisibility(View.GONE);
                payBtn.setEnabled(true);
            }
        });
    }

}