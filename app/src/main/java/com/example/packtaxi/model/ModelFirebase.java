package com.example.packtaxi.model;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.packtaxi.loginFragmentDirections;
import com.example.packtaxi.signUpAsSenderFragmentDirections;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ModelFirebase {
    final static String SENDERS = "senders";
    final static String PAYMENTS = "payments";
    final static String DRIVERS = "drivers";
    final static String MANAGER = "manager";
    final static String DELIVERYPOINTS = "deliveryPoints";
    final static String ROUTES = "routes";
    final static String PACKAGES = "packages";
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
    public void editDriver(Driver user, Model.editDriverListener listener) {
        db.collection(DRIVERS).document(user.getEmail()).set(user.toJson())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onComplete(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.getMessage());
                        listener.onComplete(false);
                    }
                });
    }

    public void getRoutesList(String email,Model.GetAllRoutesListener listener) {
        db.collection(ROUTES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<FutureRoute> routesList = new LinkedList<FutureRoute>();
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc:task.getResult()) {
                        FutureRoute r = FutureRoute.fromJson(doc.getId(), doc.getData());
                        if(r != null) {
                            if (r.getDriver().equals(email)) {
                                routesList.add(r);
                            }
                        }
                    }
                }
                listener.onComplete(routesList);
            }
        });
    }
    public void getPackagesList(String email, Model.GetAllPackagesListener listener) {
        db.collection(PACKAGES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<Package> packagesList = new LinkedList<Package>();
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc:task.getResult()) {
                        Package p = Package.fromJson(doc.getId(), doc.getData());
                        if(p != null) {
                            if (p.getSender().equals(email)) {
                                packagesList.add(p);
                            }
                        }
                    }
                }
                listener.onComplete(packagesList);
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
    public void getCurrentDriver(Model.getCurrentDriverListener listener) {
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

    public void editDeliveryPoint(DeliveryPoint dp, Model.editDpListener listener) {
        db.collection(DELIVERYPOINTS).document(dp.getDeliveryPointName()).set(dp.toJson())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onComplete(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.getMessage());
                        listener.onComplete(false);
                    }
                });
    }
    public void deletePackage(Package p, Model.deletePackageListener listener) {
        db.collection(PACKAGES).document(p.getPackageID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                listener.onComplete(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onComplete(false);
            }
        });
    }
    public void deleteFutureRoute(FutureRoute f, Model.deleteFutureRouteListener listener) {
        db.collection(ROUTES).document(f.getFutureRouteID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                listener.onComplete(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onComplete(false);
            }
        });
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
//    public void getAllDrivers(Model.GetAllDriversListener Listener){
//
//    }
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
        Log.d("TAG", "in model firebase");
        DocumentReference docRef = db.collection(DRIVERS).document(email);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "in model firebase driver data"+ Driver.fromJson(document.getData()));
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
    public void addNewRoute(FutureRoute route, Model.addNewRouteListener listener) {
        db.collection(ROUTES).document(route.getFutureRouteID()).set(route.toJson()).addOnSuccessListener((successListener)-> {
            listener.onComplete(true);
        })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());
                    listener.onComplete(false);
                });
        startMatch();

    }
    public void addNewPack(Package p, Model.addNewPackListener listener) {
        db.collection(PACKAGES).document(p.getPackageID()).set(p.toJson()).addOnSuccessListener((successListener)-> {
            listener.onComplete(true);
        })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());
                    listener.onComplete(false);
                });
        startMatch();
    }
    public void startMatch(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.1.156:5000/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("TAG","onFailure");
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.d("TAG","onResponse");
                final String responseData = response.body().string();
//                MainActivity.this.runOnUiThread(() -> textView_response.setText(responseData));
                Log.d("TAG", responseData);
            }
        });
    }
    public void addNewDeliveryPoint(DeliveryPoint dp, Model.addNewDeliveryPointListener listener) {
        db.collection(DELIVERYPOINTS).document(dp.getDeliveryPointName()).set(dp.toJson()).addOnSuccessListener((successListener)-> {
            listener.onComplete(true);
        })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());
                    listener.onComplete(false);
                });
    }
    public void addNewPayment(payment p, Model.addNewPaymentListener listener) {
        db.collection(PAYMENTS).document(p.getNumPackage()).set(p.toJson()).addOnSuccessListener((successListener)-> {
            listener.onComplete(true);
        })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());
                    listener.onComplete(false);
                });
        Model.getInstance().getPackageByID(p.getNumPackage(), new Model.getPackageByIDListener() {
            @Override
            public void onComplete(Package pac) {
                if(pac != null) {
                    pac.setPay(true);
                    db.collection(PACKAGES).document(p.getNumPackage()).set(pac);
                }
            }
        });
    }
    public void updateRateDriver(Driver d, Model.updateRateDriverListener listener) {
        db.collection(PACKAGES).document(d.getEmail()).set(d);
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
                        if(dp != null&& dp.getIsDeleted()==false)
                            dPsList.add(dp.getDeliveryPointName());
                    }
                }
                listener.onComplete(dPsList);
            }
        });
    }
    public void getDeliveryPointsList(Model.GetAllDeliveryPointsListener listener) {
        db.collection(DELIVERYPOINTS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<DeliveryPoint> deliveryPointsList = new LinkedList<DeliveryPoint>();
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc:task.getResult()) {
                        DeliveryPoint dp = DeliveryPoint.fromJson(doc.getId(), doc.getData());
                        if(dp != null&& dp.getIsDeleted()==false)
                            deliveryPointsList.add(dp);
                    }
                }
                listener.onComplete(deliveryPointsList);
            }
        });
    }
    public void getDPStringList(Model.GetAllDPListener listener) {
        db.collection(DELIVERYPOINTS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> dPList = new ArrayList<String>(); //= new List<String>();
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc:task.getResult()) {
                        DeliveryPoint dp = DeliveryPoint.fromJson(doc.getId(), doc.getData());
                        if(dp != null&& dp.getIsDeleted()==false)
                            dPList.add(dp.getDeliveryPointName());
                    }
                }
                listener.onComplete(dPList);
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
