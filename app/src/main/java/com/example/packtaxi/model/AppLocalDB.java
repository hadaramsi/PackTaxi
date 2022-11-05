package com.example.packtaxi.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.packtaxi.MyApplication;

@Database(entities = {DeliveryPoint.class}, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract DeliveryPointDao deliveryPointDao();
//    public abstract Object DeliveryPointDao(); עשה בעיות לא להחזיר
}

public class AppLocalDB {
    static public final AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.getContext(),
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
    private AppLocalDB(){}

}
