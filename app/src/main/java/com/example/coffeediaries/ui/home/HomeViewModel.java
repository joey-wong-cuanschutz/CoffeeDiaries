package com.example.coffeediaries.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.coffeediaries.AppDatabase;
import com.example.coffeediaries.CoffeeRecordsEntity;
import com.example.coffeediaries.MainActivity;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final LiveData<List<CoffeeRecordsEntity>> records;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        AppDatabase db = AppDatabase.getInstance(application); // assumes you have a singleton method
        records = db.coffeeRecordDao().getAllLive();

    }

    public LiveData<List<CoffeeRecordsEntity>> getRecords() { return records; }
}
