package com.example.packtaxi.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PackageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Package... Package);

    @Delete
    void delete(Package p);

    @Query("select * from Package")
    List<Package> getAll();

    @Query("SELECT * FROM Package WHERE sender=:s")
    List<Package> getMyPackages(String s);

    @Query("SELECT * FROM Package WHERE packageID=:id")
    Package getPackageByID(String id);
}
