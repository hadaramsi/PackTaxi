package com.example.packtaxi.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FutureRouteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(FutureRoute... FutureRoute);

    @Delete
    void delete(FutureRoute r);

    @Query("SELECT * FROM FutureRoute")
    List<FutureRoute> getAll();

    @Query("SELECT * FROM FutureRoute WHERE driver=:d")
    List<FutureRoute> getMyRoutes(String d);

    @Query("SELECT * FROM FutureRoute WHERE futureRouteID=:id")
    FutureRoute getRouteByID(String id);
}
