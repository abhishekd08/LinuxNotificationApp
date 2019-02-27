package com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.Model.TABLE_NAME;

@Dao
public interface NotificationsDao {

    @Query("SELECT * FROM " + TABLE_NAME + ";")
    List<Model> getAll();

    @Insert
    void addItem(Model item);

    @Query("DELETE FROM notifications")
    void deleteAll();

    @Query("SELECT * FROM notifications WHERE id=:id")
    DataItem getFromId(int id);

    @Query("DELETE FROM notifications WHERE id=:id")
    void deleteItem(int id);

    //TEMP
    @Insert
    void addAll(ArrayList<Model> list);

}
