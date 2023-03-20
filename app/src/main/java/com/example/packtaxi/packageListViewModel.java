package com.example.packtaxi;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.packtaxi.model.Package;
import com.example.packtaxi.model.Model;
import java.util.List;

public class packageListViewModel extends ViewModel {
    private LiveData<List<Package>> packages = Model.getInstance().getAllPackagesSender();

    public LiveData<List<Package>> getPackage(){
        return packages;
    }
}
