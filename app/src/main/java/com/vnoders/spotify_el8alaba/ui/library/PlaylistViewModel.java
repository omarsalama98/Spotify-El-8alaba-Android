package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.library.Track;
import java.util.List;

public class PlaylistViewModel extends ViewModel {

    private MutableLiveData<List<Track>> tracks = new MutableLiveData<>();

    public void setTracks(List<Track> tracks) {
        this.tracks.setValue(tracks);
    }
    
    public LiveData<List<Track>> getTracks() {
        return tracks;
    }
}