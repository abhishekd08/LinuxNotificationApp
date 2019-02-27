package com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.abhishek.linuxnotificationapp.Model.DataItem;

@Database(entities = {Model.class}, version = 1)
public abstract class NotificationsDB extends RoomDatabase {

    public abstract NotificationsDao notificationsDao();
    public static NotificationsDB instance;

    public static NotificationsDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NotificationsDB.class, "notification-db")
                    //TODO remove .allowMainThreadQueries() and implement concurrancy (do database operations in different thread)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
