package com.example.packtaxi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.packtaxi.model.Package;
import com.example.packtaxi.model.Model;
import java.util.List;

public class packageListViewModel extends ViewModel {
    private LiveData<List<Package>> packages = Model.getInstance().getAllPackages();
    public LiveData<List<Package>> getPackage(){return packages;}
//    public void setMyRoutes(LiveData<List<FutureRoute>>data){myFutureRoutes = data;}
}
