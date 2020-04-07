package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;

public class PlaylistHomeViewModel extends ViewModel {

    private MutableLiveData<String> playlistName;
    private MutableLiveData<String> playlistOwnerName;
    private MutableLiveData<String> tracksSummary;
    private MutableLiveData<String> imageUrl;

    private String playlistId;

    public PlaylistHomeViewModel() {
        playlistName = new MutableLiveData<>();
        playlistOwnerName = new MutableLiveData<>();
        tracksSummary = new MutableLiveData<>();
        imageUrl = new MutableLiveData<>();
    }

    public MutableLiveData<String> getPlaylistName() {
        return playlistName;
    }

    public MutableLiveData<String> getTracksSummary() {
        return tracksSummary;
    }

    public MutableLiveData<String> getImageUrl() {
        return imageUrl;
    }

    public MutableLiveData<String> getPlaylistOwnerName() {
        return playlistOwnerName;
    }

    public void updateData() {
        LibraryRepository.updatePlaylist(this);
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}