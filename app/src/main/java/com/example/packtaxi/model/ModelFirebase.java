package com.example.packtaxi.model;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.packtaxi.loginFragmentDirections;
import com.example.packtaxi.signUpAsSenderFragmentDirections;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

public class ModelFirebase {
    final static String SENDERS = "senders";
    final static String DRIVERS = "drivers";
    final static String MANAGER = "manager";
    final static String DELIVERYPOINTS = "deliveryPoints";
    final static String ROUTES = "routes";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void loginUser(String email, String password,View v, Model.loginUserListener listener) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG","login");
                            checkUserConnected(email,v);
                            listener.onComplete(true);
                        }
                        else{
                            Log.d("TAG","fail to login");
                            listener.onComplete(false);
                        }
                    }
                });
    }

    public void getRoutesList(Model.GetAllRoutesListener listener) {
        db.collection(ROUTES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<FutureRoute> routesList = new LinkedList<FutureRoute>();
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc:task.getResult()) {
                        FutureRoute r = FutureRoute.fromJson(doc.getId(), doc.getData());
                        if(r != null)
                            routesList.add(r);
                    }
                }
                listener.onComplete(routesList);
            }
        });
    }

    public void getCurrentSender(Model.getCurrentSenderListener listener)
    {
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currUser == null)
            listener.onComplete(null);
        else {
            listener.onComplete(currUser.getEmail());
        }
    }
    public void getCurrentDriver(Model.getCurrentDriverListener listener)
    {
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currUser == null)
            listener.onComplete(null);
        else {
            listener.onComplete(currUser.getEmail());
        }
    }

    public void logOutUser(Model.logOutUserListener listener) {
        FirebaseAuth.getInstance().signOut();
        listener.onComplete();
    }
    public void checkUserConnected(String email, View v){
        DocumentReference docRef1 = db.collection(MANAGER).document(email);
        DocumentReference docRef2 = db.collection(SENDERS).document(email);
        DocumentReference docRef3 = db.collection(DRIVERS).document(email);
        docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        @NonNull NavDirections action = loginFragmentDirections.actionFragmentLoginToMangerMainScreenFragment();
                        Navigation.findNavController(v).navigate(action);
                    }
                }
            }
        });
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        @NonNull NavDirections action = loginFragmentDirections.actionFragmentLoginToMainScreenSenderFragment();
                        Navigation.findNavController(v).navigate(action);
                    }
                }
            }
        });
        docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        @NonNull NavDirections action = loginFragmentDirections.actionFragmentLoginToMainScreenDriverFragment();
                        Navigation.findNavController(v).navigate(action);
                    }
                }
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
    public void getSenderByEmail(String email, Model.getSenderByEmailListener listener) {
        DocumentReference docRef = db.collection(SENDERS).document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Sender sender = Sender.fromJson(document.getData());
                        if(sender != null)
                            listener.onComplete(sender);
                    } else {
                        listener.onComplete(null);
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                    listener.onComplete(null);
                }
            }
        });
    }
    public void getDriverByEmail(String email, Model.getDriverByEmailListener listener) {
        DocumentReference docRef = db.collection(DRIVERS).document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Driver driver = Driver.fromJson(document.getData());
                        if(driver != null)
                            listener.onComplete(driver);
                    } else {
                        listener.onComplete(null);
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                    listener.onComplete(null);
                }
            }
        });
    }
    public void getAllSenders(Model.GetAllSendersListener Listener){

    }
    public void addNewRoute(FutureRoute route, Model.addNewRouteListener listener) {
        Task<DocumentReference> ref = db.collection(ROUTES).add(route.toJson());
        ref.addOnSuccessListener((successListener)-> {
            listener.onComplete(true);
        })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());
                    listener.onComplete(false);
                });
    }
    public void addNewDeliveryPoint(DeliveryPoint dp, Model.addNewDeliveryPointListener listener) {
        Task<DocumentReference> ref = db.collection(DELIVERYPOINTS).add(dp.toJson());
        ref.addOnSuccessListener((successListener)-> {
            listener.onComplete(true);
        })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());
                    listener.onComplete(false);
                });
    }
    public void getDPs(Model.GetDPsListener listener) {
        db.collection(DELIVERYPOINTS)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<String> dPsList = new LinkedList<String>();
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc:task.getResult()) {
                        DeliveryPoint dp = DeliveryPoint.fromJson(doc.getId(), doc.getData());
                        dPsList.add(dp.getDeliveryPointName());
                    }
                }
                listener.onComplete(dPsList);
            }
        });
    }
    public void getDeliveryPointsList(Long since, Model.GetAllDeliveryPointsListener listener) {
        db.collection(DELIVERYPOINTS).whereGreaterThanOrEqualTo(DeliveryPoint.LAST_UPDATED,new Timestamp(since, 0))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<DeliveryPoint> deliveryPointsList = new LinkedList<DeliveryPoint>();
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc:task.getResult()) {
                        DeliveryPoint dp = DeliveryPoint.fromJson(doc.getId(), doc.getData());
                        if(dp != null)
                            deliveryPointsList.add(dp);
                    }
                }
                listener.onComplete(deliveryPointsList);
            }
        });
    }
    public void addSender(Sender sender,String password, Model.AddSenderListener listener){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(sender.getEmail(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
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
