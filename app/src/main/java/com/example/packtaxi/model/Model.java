package com.example.packtaxi.model;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.packtaxi.MyApplication;

import java.util.List;

public class Model {
    static final private Model instance = new Model();
    private ModelFirebase modelFirebase = new ModelFirebase();
    private MutableLiveData<List<DeliveryPoint>> deliveryPointsList = new MutableLiveData<List<DeliveryPoint>>();
    private MutableLiveData<LoadingState> deliveryPointsListLoadingState = new MutableLiveData<LoadingState>();

    public enum LoadingState{
        loading,
        loaded
    }
    private Model(){

    }
    public interface logOutUserListener{
        void onComplete();
    }
    public interface addNewDeliveryPointListener{
        void onComplete(boolean ifSuccess);
    }

    public void addNewDeliveryPoint(DeliveryPoint dp,addNewDeliveryPointListener listener){
        modelFirebase.addNewDeliveryPoint(dp, (success)->{
            reloadDeliveryPointsList();
            listener.onComplete(success);
        });
    }
    public interface GetAllDeliveryPointsListener{
        void onComplete(List<DeliveryPoint> data);
    }
    public void reloadDeliveryPointsList(){
        deliveryPointsListLoadingState.setValue(LoadingState.loading); //התחלת הטעינה
        //get local last update
        Long localLastUpdate = DeliveryPoint.getLocalLastUpdated();
        //get all DeliveryPoints records since local last update from firebase
        modelFirebase.getDeliveryPointsList(localLastUpdate,(list)->{
            MyApplication.executorService.execute(()->{
                //update local last update date
                //add new record to the local db
                Long lLastUpdate = new Long(0);
                for(DeliveryPoint dp : list) {
                    AppLocalDB.db.deliveryPointDao().insertAll(dp);
                    if(dp.getIsDeleted())// if the delivery point is deleted in the firebase, delete him from the cache
                        AppLocalDB.db.deliveryPointDao().delete(dp);
                    if(dp.getLastUpdated() > lLastUpdate){
                        lLastUpdate = dp.getLastUpdated();
                    }
                }
                DeliveryPoint.setLocalLastUpdated(lLastUpdate);
                //return all records to the caller
                List<DeliveryPoint> dPsList = AppLocalDB.db.deliveryPointDao().getAll();
                deliveryPointsList.postValue(dPsList);
                deliveryPointsListLoadingState.postValue(LoadingState.loaded);// סיום הטעינה
            });

        });
    }
    public void logOutUser(logOutUserListener listener)
    {
        modelFirebase.logOutUser(listener);
    }

    public interface loginUserListener{
        void onComplete(boolean success);
    }
    public void loginUser(String email, String password, View v, loginUserListener listener) {
        modelFirebase.loginUser(email, password,v, listener);
    }
    public static Model getInstance(){
        return instance;
    }
    public interface getSenderByEmailListener{
        void onComplete(Sender sender);
    }

    public void getSenderByEmail(String email, getSenderByEmailListener listener)
    {
        modelFirebase.getSenderByEmail(email, listener);
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
            DeliveryPoint dp = AppLocalDB.db.deliveryPointDao().getDeliveryPointByID(deliveryPointID);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(dp);
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
        void onComplete(boolean ifSuccess);
    }
    public void addDriver(Driver driver,String password, AddDriverListener listener){
        modelFirebase.addDriver(driver, password, listener);
    }
    public interface AddSenderListener{
        void onComplete(boolean ifSuccess);
    }
    public void addSender(Sender sender,String password, AddSenderListener listener){
        modelFirebase.addSender(sender, password, listener);
    }
}
