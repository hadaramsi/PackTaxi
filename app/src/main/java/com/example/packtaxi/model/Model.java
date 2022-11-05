package com.example.packtaxi.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.packtaxi.MyApplication;

import java.util.List;

public class Model {
    static final private Model instance = new Model();
    private ModelFirebase modelFirebase = new ModelFirebase();
    private MutableLiveData<List<DeliveryPoint>> deliveryPointsList = new MutableLiveData<List<DeliveryPoint>>();

    private Model(){

    }
    public interface loginUserListener{
        void onComplete(boolean success);
    }
    public void loginUser(String userName, String password, loginUserListener listener) {
        modelFirebase.loginUser(userName, password, listener);
    }
    public static Model getInstance(){
        return instance;
    }

    public LiveData<List<DeliveryPoint>> getAllDeliveryPoints(){
        return deliveryPointsList;
    }

    public interface getDeliveryPointByIDListener{
        void onComplete(DeliveryPoint dp);
    }

    public void getDeliveryPointByID(String deliveryPointID, getDeliveryPointByIDListener listener)
    {
        MyApplication.executorService.execute(()-> {
//            DeliveryPoint dp = AppLocalDB.db.DeliveryPointDao().getDeliveryPointByID(deliveryPointID);
            MyApplication.mainHandler.post(()->{
//                listener.onComplete(dp);
            });
        });

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
