package com.example.coffeediaries.ui.gallery;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeediaries.AppDatabase;
import com.example.coffeediaries.CoffeeRecordsEntity;

import java.util.List;

public class GalleryViewModel extends AndroidViewModel {

    private final LiveData<List<CoffeeRecordsEntity>> records;

    public GalleryViewModel(@NonNull Application application) {
        super(application);

        AppDatabase db = AppDatabase.getInstance(application); // assumes you have a singleton method
        records = db.coffeeRecordDao().getAllLive();

    }

    public LiveData<List<CoffeeRecordsEntity>> getRecords() { return records; }
}