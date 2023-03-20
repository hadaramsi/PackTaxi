package com.example.packtaxi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.packtaxi.model.FutureRoute;
import com.example.packtaxi.model.Model;
import java.util.List;

public class FutureRoutesListViewModel extends ViewModel {
    private LiveData<List<FutureRoute>> futureRoute = Model.getInstance().getAllFutureRoutes();
    public LiveData<List<FutureRoute>> getRoutes(){return futureRoute;}

}
