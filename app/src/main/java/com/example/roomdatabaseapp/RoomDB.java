package com.example.roomdatabaseapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// tambah entitas database
@Database(entities = {MainData.class},version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //membuat instance database
    private static RoomDB database;

    //nama databse
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDB getInstance(Context context){
        //cek kondisi
        if (database == null){
            //jika database null
            //inisiasi database
            database = Room.databaseBuilder(context.getApplicationContext()
            ,RoomDB.class,DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build();
        }
        //return database
        return database;
    }
    //create Dao
    public abstract MainDao mainDao();
}
