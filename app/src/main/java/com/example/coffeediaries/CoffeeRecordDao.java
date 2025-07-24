package com.example.coffeediaries;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoffeeRecordDao {

//    get all coffee records
    @Query("SELECT * FROM CoffeeRecordsEntity")
    List<CoffeeRecordsEntity> getAll();

//    insert new record
    @Insert
    void insertNewRecord(CoffeeRecordsEntity coffeeRecordsEntity);

//    delete record
    @Delete
    void deleteRecord(CoffeeRecordsEntity coffeeRecordsEntity);

//    update record
    @Update
    void updateRecord(CoffeeRecordsEntity coffeeRecordsEntity);

//    select 1 record by id
}
