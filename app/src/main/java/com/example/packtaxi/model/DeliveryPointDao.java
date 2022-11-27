package com.example.packtaxi.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DeliveryPointDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(DeliveryPoint... DeliveryPoint);

    @Delete
    void delete(DeliveryPoint dp);

    @Query("SELECT * FROM DeliveryPoint")
    List<DeliveryPoint> getAll();


//    @Query("select * from DeliveryPoint")
//    List<DeliveryPoint> getAll();

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertAll(DeliveryPoint... DeliveryPoint);

//    @Delete
//    void delete(DeliveryPoint dp);

    @Query("SELECT * FROM DeliveryPoint WHERE DELIVERYPOINTNAME=:name")
    DeliveryPoint getDeliveryPointByName(String name);

    @Query("SELECT * FROM DeliveryPoint WHERE deliveryPointName=:u")
    List<DeliveryPoint> getMyDeliveryPoints(String u);
}
