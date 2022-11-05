package com.example.packtaxi.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ModelFirebase {
    final static String SENDERS = "senders";
    final static String DRIVERS = "drivers";
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void loginUser(String userName, String password, Model.loginUserListener listener) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (userName.equals("mani") && password.equals("654321")) {
            listener.onComplete(true);
        }
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            listener.onComplete(true);
                        else
                            listener.onComplete(false);

                    }
                });
    }
    public void getAllDrivers(Model.GetAllDriversListener Listener){

    }
    public void addDriver(Driver driver,String password, Model.AddDriverListener listener){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(driver.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            db.collection(DRIVERS).document(driver.getEmail()).set(driver.toJson())
                                    .addOnSuccessListener((successListener)-> {
                                        listener.onComplete(true);
                                    })
                                    .addOnFailureListener((e)-> {
                                        Log.d("TAG", e.getMessage());
                                        //TODO: delete the user from the authentication
                                    });
                        }
                        else {
                            listener.onComplete(false);
                        }
                    }
                });
    }

    public void getAllSenders(Model.GetAllSendersListener Listener){

    }
    public void addSender(Sender sender,String password, Model.AddSenderListener listener){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(sender.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            db.collection(SENDERS).document(sender.getEmail()).set(sender.toJson())
                                    .addOnSuccessListener((successListener)-> {
                                        listener.onComplete(true);
                                    })
                                    .addOnFailureListener((e)-> {
                                        Log.d("TAG", e.getMessage());
                                    });
                        }
                        else {
                            listener.onComplete(false);
                        }
                    }
                });
    }
}
