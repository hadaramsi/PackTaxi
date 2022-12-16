package com.example.packtaxi.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface packageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Package... Package);

    @Delete
    void delete(Package p);

    @Query("SELECT * FROM Package")
    List<Package> getAll();
}
