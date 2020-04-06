package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlaylistHomeViewModel extends ViewModel {

    private MutableLiveData<String> playlistName;
    private MutableLiveData<String> playlistOwnerName;
    private MutableLiveData<String> tracksSummary;
    private MutableLiveData<String> imageUrl;

    public PlaylistHomeViewModel() {
        playlistName = new MutableLiveData<>();
        playlistOwnerName = new MutableLiveData<>();
        tracksSummary = new MutableLiveData<>();
        imageUrl = new MutableLiveData<>();
    }

    public LiveData<String> getPlaylistName() {
        return playlistName;
    }

    public LiveData<String> getTracksSummary() {
        return tracksSummary;
    }

    public LiveData<String> getImageUrl() {
        return imageUrl;
    }

    public LiveData<String> getPlaylistOwnerName() {
        return playlistOwnerName;
    }

    public void updateData() {
        //TODO : send request
    }

}