package com.example.packtaxi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public signUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_test.
     */
    // TODO: Rename and change types and number of parameters
    public static signUpFragment newInstance(String param1, String param2) {
        signUpFragment fragment = new signUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
//                @NonNull NavDirections action = mainScreenFragmentDirections.actionMainScreenFragmentToSignUpFragment();
                Navigation.findNavController(v).navigate(R.id.action_fragmentSignUp_to_fragmentSignUpAsSender);
            }
        });

        signUpAsDriverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                @NonNull NavDirections action = MainScreenFragmentDirections.action_fragmentMainScreen_to_fragmentLogin();
                Navigation.findNavController(v).navigate(R.id.action_fragmentSignUp_to_fragmentSignUpAsDriver);
            }
        });
        return view;
    }
}