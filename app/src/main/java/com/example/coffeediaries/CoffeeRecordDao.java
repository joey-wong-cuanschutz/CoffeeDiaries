package com.example.coffeediaries;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM CoffeeRecordsEntity")
    LiveData<List<CoffeeRecordsEntity>> getAllLive();

//    insert new record
    @Insert
    public long insertRecord(CoffeeRecordsEntity coffeeRecordsEntity);

//    delete record
    @Delete
    public void deleteRecord(CoffeeRecordsEntity coffeeRecordsEntity);

//    update record
    @Update
    public void updateRecord(CoffeeRecordsEntity coffeeRecordsEntity);

//    select 1 record by id
    @Query("SELECT * FROM CoffeeRecordsEntity WHERE id = :recordId LIMIT 1" )
    CoffeeRecordsEntity findByRecordId(int recordId);
}
