package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import java.util.List;


public class LibraryArtistViewModel extends ViewModel {

    private MutableLiveData<List<Artist>> userArtists;

    public LibraryArtistViewModel() {
        userArtists = new MutableLiveData<>();
    }

    public LiveData<List<Artist>> getUserArtists() {
        return userArtists;
    }

    public void setUserArtists(List<Artist> userArtists) {
        this.userArtists.setValue(userArtists);
    }

}
