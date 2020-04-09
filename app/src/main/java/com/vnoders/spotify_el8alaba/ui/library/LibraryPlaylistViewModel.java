package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.library.UserLibraryPlaylistItem;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import java.util.List;

public class LibraryPlaylistViewModel extends ViewModel {

    private MutableLiveData<List<UserLibraryPlaylistItem>> userPlaylists;

    public LibraryPlaylistViewModel() {
        userPlaylists = new MutableLiveData<>();
    }

    public LiveData<List<UserLibraryPlaylistItem>> getUserPlaylists() {
        return userPlaylists;
    }

    public void requestUserPlaylists() {
        LibraryRepository.updateLibraryPlaylists(userPlaylists);
    }

}
