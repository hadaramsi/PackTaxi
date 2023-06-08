package com.example.packtaxi.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.packtaxi.MyApplication;

@Database(entities = {DeliveryPoint.class, Package.class, FutureRoute.class}, version = 11)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract DeliveryPointDao deliveryPointDao();
    public abstract FutureRouteDao FutureRouteDao();
    public abstract PackageDao PackageDao();
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
