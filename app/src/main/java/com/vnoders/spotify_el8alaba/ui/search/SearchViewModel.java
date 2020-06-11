package com.vnoders.spotify_el8alaba.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * A ViewModel class for search fragment.
 */
public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Search fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}