package com.example.packtaxi.model;

import com.google.firebase.firestore.FirebaseFirestore;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void getAllDrivers(Model.GetAllDriversListener Listener){

    }
    public void addDriver(Driver driver, Model.AddDriverListener Listener){

    }
    public void getAllSenders(Model.GetAllSendersListener Listener){

    }
    public void addSender(Sender sender, Model.AddSenderListener Listener){

    }
}
