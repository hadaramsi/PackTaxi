package com.example.packtaxi.model;

import java.util.List;

public class Model {
    static final private Model instance = new Model();
    private ModelFirebase modelFirebase = new ModelFirebase();

    private Model(){

    }
    public static Model getInstance(){
        return instance;
    }
    public interface GetAllDriversListener{
        void onComplete(List<Driver> driversData);
    }
    public void getAllDrivers( GetAllDriversListener listener){
        modelFirebase.getAllDrivers(listener);
    }
    public interface GetAllSendersListener{
        void onComplete(List<Sender> sendersData);
    }
    public void getAllSenders( GetAllSendersListener listener){
        modelFirebase.getAllSenders(listener);
    }
    public interface AddDriverListener{
        void onComplete();
    }
    public void addDriver(Driver driver, AddDriverListener listener){
        modelFirebase.addDriver(driver, listener);
    }
    public interface AddSenderListener{
        void onComplete();
    }
    public void addSender(Sender sender, AddSenderListener listener){
        modelFirebase.addSender(sender, listener);
    }
}
