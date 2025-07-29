package com.example.coffeediaries.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        // This will be set by the fragment with proper string resource
        mText.setValue("");
    }

    public void setText(String text) {
        mText.setValue(text);
    }

    public LiveData<String> getText() {
        return mText;
    }
}