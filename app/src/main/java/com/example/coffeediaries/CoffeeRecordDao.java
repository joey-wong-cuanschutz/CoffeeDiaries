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

    // select all records but order by the rating
    @Query("SELECT * FROM CoffeeRecordsEntity ORDER BY rating ASC")
    List<CoffeeRecordsEntity> getAllRatingOrder();

//    insert new record
    @Insert
    public long insertRecord(CoffeeRecordsEntity coffeeRecordsEntity);

//    delete record
    @Delete
    public void deleteRecord(CoffeeRecordsEntity coffeeRecordsEntity);

    // Clears the database of all coffee records
    @Query("DELETE FROM CoffeeRecordsEntity")
    public void deleteAll();

//    update record
    @Update
    public void updateRecord(CoffeeRecordsEntity coffeeRecordsEntity);

//    select 1 record by id
    @Query("SELECT * FROM CoffeeRecordsEntity WHERE id = :recordId LIMIT 1" )
    CoffeeRecordsEntity findByRecordId(int recordId);

    // Counts all recoreds for a specific date
    @Query("SELECT COUNT(*) FROM CoffeeRecordsEntity WHERE brewDate = :date")
    int getCountByDate(String date);

    // Counts all records between two dates (including the startDate and endDate)
    @Query("SELECT COUNT(*) FROM CoffeeRecordsEntity WHERE brewDate BETWEEN :startDate AND :endDate")
    int getCountBetweenDates(String startDate, String endDate);
}
