package com.vnoders.spotify_el8alaba.ui.yourLibrary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class YourLibraryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public YourLibraryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Your Library fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}