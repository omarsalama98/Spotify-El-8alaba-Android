package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;

public class PlaylistHomeViewModel extends ViewModel {

    private MutableLiveData<String> playlistName;
    private MutableLiveData<String> playlistOwnerName;
    private MutableLiveData<String> tracksSummary;
    private MutableLiveData<String> imageUrl;

    public void setPlaylistName(String playlistName) {
        this.playlistName.setValue(playlistName);
    }

    public void setPlaylistOwnerName(String playlistOwnerName) {
        this.playlistOwnerName.setValue(playlistOwnerName);
    }

    public void setTracksSummary(String tracksSummary) {
        this.tracksSummary.setValue(tracksSummary);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.setValue(imageUrl);
    }

    private String playlistId;

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
        LibraryRepository.updatePlaylist(this);
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}