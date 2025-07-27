package com.example.coffeediaries.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.coffeediaries.AppDatabase;
import com.example.coffeediaries.CoffeeRecordsEntity;
import com.example.coffeediaries.MainActivity;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final LiveData<List<CoffeeRecordsEntity>> records = null;
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}