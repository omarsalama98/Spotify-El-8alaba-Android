package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.library.Track;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import java.util.List;

public class PlaylistTracksViewModel extends ViewModel {

    private MutableLiveData<List<Track>> tracks = new MutableLiveData<>();
    private MutableLiveData<String> playlistImageUrl;

    private String playlistId;

    public PlaylistTracksViewModel() {
        tracks = new MutableLiveData<>();
        playlistImageUrl = new MutableLiveData<>();
    }

    public void setTracks(List<Track> tracks) {
        this.tracks.setValue(tracks);
    }

    public LiveData<List<Track>> getTracks() {
        return tracks;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public LiveData<String> getPlaylistImageUrl() {
        return playlistImageUrl;
    }

    public void setPlaylistImageUrl(String playlistImageUrl) {
        this.playlistImageUrl.setValue(playlistImageUrl);
    }

    public void updatePlaylistTracks() {
        LibraryRepository.updatePlaylistTracks(this);
        LibraryRepository.updatePlaylistCoverImages(this);
    }

}