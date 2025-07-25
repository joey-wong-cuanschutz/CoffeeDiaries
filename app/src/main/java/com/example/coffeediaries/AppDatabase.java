package com.example.coffeediaries;

import android.content.Context;

import androidx.room.ConstructedBy;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CoffeeRecordsEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // define the database name
    private static final String DATABASE_NAME = "Coffee-Diaries.db";
    // create an instance of AppDatabase object variable
    private static AppDatabase db;

    public static AppDatabase getInstance(Context context) {
        if (db == null) {
            // create an instance of Room database if it does not exist
            db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return db;
    }


    public abstract CoffeeRecordDao coffeeRecordDao();
}
