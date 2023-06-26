package com.example.packtaxi.model;

import android.util.Log;
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
    private MutableLiveData<List<FutureRoute>> routesList = new MutableLiveData<List<FutureRoute>>();
    private MutableLiveData<LoadingState> routesListLoadingState = new MutableLiveData<LoadingState>();
    private MutableLiveData<List<Package>> packagesList = new MutableLiveData<List<Package>>();
    private MutableLiveData<LoadingState> packagesListLoadingState = new MutableLiveData<LoadingState>();

    public enum LoadingState{ loading, loaded}

    private Model(){
        deliveryPointsListLoadingState.setValue(LoadingState.loaded);
        reloadDeliveryPointsList();
        routesListLoadingState.setValue(LoadingState.loaded);
        reloadRoutesList();
        packagesListLoadingState.setValue(LoadingState.loaded);
        reloadPackagesList();
    }
    public LiveData<LoadingState> getDPSListLoadingState(){ return deliveryPointsListLoadingState;}
    public LiveData<List<FutureRoute>> getAllFutureRoutes(){
        return routesList;
    }
    public LiveData<LoadingState> getRoutesListLoadingState(){ return routesListLoadingState;}
    public LiveData<List<Package>> getAllPackagesSender(){ return packagesList; }
    public LiveData<LoadingState> getPackagesListLoadingState(){ return packagesListLoadingState;}
    public LiveData<List<DeliveryPoint>> getAllDeliveryPoints(){ return deliveryPointsList; }
    public interface logOutUserListener{
        void onComplete();
    }
    public interface addNewDeliveryPointListener{
        void onComplete(boolean ifSuccess);
    }
    public interface addNewRouteListener{
        void onComplete(boolean ifSuccess);
    }
    public interface addNewPackListener{
        void onComplete(boolean ifSuccess);
    }
    public interface addNewPaymentListener{
        void onComplete(boolean ifSuccess);
    }
    public interface updateRateDriverListener{
        void onComplete(boolean ifSuccess);
    }
    public void addNewRoute(FutureRoute route,addNewRouteListener listener){
        modelFirebase.addNewRoute(route, (success)->{
            reloadRoutesList();
            listener.onComplete(success);
        });
    }
    public void addNewPack(Package pack, addNewPackListener listener){
        modelFirebase.addNewPack(pack, (success)->{
            reloadPackagesList();
            listener.onComplete(success);
        });
    }
    public void addNewDeliveryPoint(DeliveryPoint dp,addNewDeliveryPointListener listener){
        modelFirebase.addNewDeliveryPoint(dp, (success)->{
            reloadDeliveryPointsList();
            listener.onComplete(success);
        });
    }
    public void addNewPayment(payment p,addNewPaymentListener listener){
        modelFirebase.addNewPayment(p, (success)->{
            listener.onComplete(success);
        });
    }

    public void updateRateDriver(Driver d,Package p, updateRateDriverListener listener){
        modelFirebase.updateRateDriver(d,p, (success)->{
            listener.onComplete(success);
        });
    }
    public interface GetAllDeliveryPointsListener{
        void onComplete(List<DeliveryPoint> data);
    }
    public interface GetAllDPListener{
        void onComplete(List<String> data);
    }
    public void getDPStringList(GetAllDPListener listener) {
        modelFirebase.getDPStringList(listener);
    }
    public interface GetDPsListener{
        void onComplete(List<String> data);
    }
    public void getDPs(GetDPsListener listener) {
        modelFirebase.getDPs(listener);
    }

    public void reloadDeliveryPointsList(){
        deliveryPointsListLoadingState.setValue(LoadingState.loading); //התחלת הטעינה
        modelFirebase.getDeliveryPointsList((list)->{
            MyApplication.executorService.execute(()->{
                // קוד למחיקת מקומי
                for (DeliveryPoint dp: AppLocalDB.db.deliveryPointDao().getAll()){
                    AppLocalDB.db.deliveryPointDao().delete(dp);
                }
                for(DeliveryPoint dp : list) {
                    AppLocalDB.db.deliveryPointDao().insertAll(dp);
                    if(dp.getIsDeleted())// if the delivery point is deleted in the firebase, delete him from the cache
                        AppLocalDB.db.deliveryPointDao().delete(dp);
                }
                List<DeliveryPoint> dPsList = AppLocalDB.db.deliveryPointDao().getAll();
                deliveryPointsList.postValue(dPsList);
                deliveryPointsListLoadingState.postValue(LoadingState.loaded);// סיום הטעינה
            });
        });
    }
    public void logOutUser(logOutUserListener listener) {
        modelFirebase.logOutUser(listener);
    }
    public interface loginUserListener{
        void onComplete(boolean success);
    }
    public void loginUser(String email, String password, View v, loginUserListener listener) {
        modelFirebase.loginUser(email, password,v, listener);
    }
    public void editDriver(Driver u,editDriverListener listener){
        modelFirebase.editDriver(u, listener);
    }
    public interface editDriverListener{
        void onComplete(boolean ifSuccess);
    }
    public static Model getInstance(){
        return instance;
    }

    public void reloadRoutesList(){
        routesListLoadingState.setValue(LoadingState.loading); //התחלת הטעינה
        modelFirebase.getCurrentDriver(new getCurrentDriverListener() {
            @Override
            public void onComplete(String userEmail) {
                modelFirebase.getRoutesList(userEmail,(list)->{
                    MyApplication.executorService.execute(()-> {
                        for (FutureRoute f: AppLocalDB.db.FutureRouteDao().getAll())
                            AppLocalDB.db.FutureRouteDao().delete(f);
                        for(FutureRoute f:list){
                            AppLocalDB.db.FutureRouteDao().insertAll(f);
                        }
                        List<FutureRoute> routesL= AppLocalDB.db.FutureRouteDao().getMyRoutes(userEmail);
                        Log.d("TAG", "routes list l 000000 "+ routesL);
                        routesList.postValue(routesL);
                        for (FutureRoute f:routesL){
                            Log.d("TAG", "nummmmmmmmmm "+ f.getPackagesNumbers());
                        }
                        routesListLoadingState.postValue(LoadingState.loaded);// סיום הטעינה
                    });
                });
            }
        });
    }
    public void reloadPackagesList(){
        packagesListLoadingState.setValue(LoadingState.loading); //התחלת הטעינה
        modelFirebase.getCurrentSender(new getCurrentSenderListener() {
            @Override
            public void onComplete(String userEmail) {
                modelFirebase.getPackagesList(userEmail,(list)->{
                    MyApplication.executorService.execute(()-> {
                        for (Package p: AppLocalDB.db.PackageDao().getAll())
                            AppLocalDB.db.PackageDao().delete(p);
                        for(Package p:list){
                            AppLocalDB.db.PackageDao().insertAll(p);
                        }
                        List<Package> packageL= AppLocalDB.db.PackageDao().getMyPackages(userEmail);
                        packagesList.postValue(packageL);
                        packagesListLoadingState.postValue(LoadingState.loaded);
                    });
                });
            }
        });
    }
    public interface deleteDeliveryPointListener{
        void onComplete();
    }
    public void deleteDeliveryPoint(DeliveryPoint dp,deleteDeliveryPointListener listener ) {
        dp.setIsDeleted(true);
        modelFirebase.editDeliveryPoint(dp,(success)->{
            if (success) {
                reloadDeliveryPointsList();
                listener.onComplete();
            }
        });
    }
    public interface deletePackageListener{
        void onComplete(boolean ifSuccess);
    }
    public void deletePackage(Package p,deletePackageListener listener ) {
        modelFirebase.deletePackage(p,(success)->{
            if (success) {
                reloadPackagesList();
                listener.onComplete(true);
            }
        });
    }
    public interface deleteFutureRouteListener{
        void onComplete(boolean ifSuccess);
    }
    public void deleteFutureRoute(FutureRoute f,deleteFutureRouteListener listener ) {
        modelFirebase.deleteFutureRoute(f,(success)->{
            if (success) {
                reloadRoutesList();
                listener.onComplete(true);
            }
        });
    }
    public interface editDpListener{
        void onComplete(boolean ifSuccess);
    }
    public interface GetAllRoutesListener{
        void onComplete(List<FutureRoute> data);
    }
    public interface GetAllPackagesListener{
        void onComplete(List<Package> data);
    }
    public void getSenderByEmail(String email, getSenderByEmailListener listener) {
        modelFirebase.getSenderByEmail(email, listener);
    }
    public void getDriverByEmail(String email, getDriverByEmailListener listener) {
        modelFirebase.getDriverByEmail(email, listener);
    }
    public interface getDriverByEmailListener{
        void onComplete(Driver driver);
    }
    public interface getSenderByEmailListener{
        void onComplete(Sender sender);
    }
    public interface getCurrentSenderListener{
        void onComplete(String senderEmail);
    }
    public void getCurrentDriver(getCurrentDriverListener listener) {
        modelFirebase.getCurrentDriver(listener);
    }
    public interface getCurrentDriverListener{
        void onComplete(String driverEmail);
    }
    public void getCurrentSender(getCurrentSenderListener listener) {
        modelFirebase.getCurrentSender(listener);
    }

    public interface getDeliveryPointByIDListener{
        void onComplete(DeliveryPoint dp);
    }

    public void getDeliveryPointByName(String deliveryPointName, getDeliveryPointByIDListener listener) {
        MyApplication.executorService.execute(()-> {
            DeliveryPoint dp = AppLocalDB.db.deliveryPointDao().getDeliveryPointByName(deliveryPointName);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(dp);
            });
        });
    }
    public interface GetAllDriversListener{
        void onComplete(List<Driver> driversData);
    }
//    public void getAllDrivers( GetAllDriversListener listener){
//        modelFirebase.getAllDrivers(listener);
//    }
    public interface GetAllSendersListener{
        void onComplete(List<Sender> sendersData);
    }
//    public void getAllSenders( GetAllSendersListener listener){
//        modelFirebase.getAllSenders(listener);
//    }
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
    public void getRouteByID(String routeID, getRouteByIDListener listener)
    {
        MyApplication.executorService.execute(()-> {
            FutureRoute f = AppLocalDB.db.FutureRouteDao().getRouteByID(routeID);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(f);
            });
        });

    }
    public interface getRouteByIDListener{
        void onComplete(FutureRoute f);
    }
    public void getPackageByID(String packageID, getPackageByIDListener listener) {
        MyApplication.executorService.execute(()-> {
            Package p = AppLocalDB.db.PackageDao().getPackageByID(packageID);
            MyApplication.mainHandler.post(()->{
                listener.onComplete(p);
            });
        });

    }
    public interface getPackageByIDListener{
        void onComplete(Package p);
    }
}
