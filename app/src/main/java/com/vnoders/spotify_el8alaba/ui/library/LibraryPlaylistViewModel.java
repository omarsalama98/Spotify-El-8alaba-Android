package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.library.UserLibraryPlaylistItem;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import java.util.List;

/**
 * This is the ViewModel of {@link LibraryPlaylistFragment}. It is responsible for preparing and
 * managing the data for {@link LibraryPlaylistFragment}. It handles the communication of {@link
 * LibraryPlaylistFragment} with the rest of the application (e.g. calling the business logic
 * classes).
 * <p>
 * The {@link LibraryPlaylistFragment} Fragment should get the information from this view model and
 * observe its changing (Using Observer Design Pattern) by getting {@link LiveData} objects and
 * applying {@link LiveData#observe} on them.
 */
public class LibraryPlaylistViewModel extends ViewModel {

    private MutableLiveData<List<UserLibraryPlaylistItem>> userPlaylists;

    public LibraryPlaylistViewModel() {
        userPlaylists = new MutableLiveData<>();
    }

    /**
     * @return The list of user playlists, wrapped in a {@link LiveData} object.
     */
    public LiveData<List<UserLibraryPlaylistItem>> getUserPlaylists() {
        return userPlaylists;
    }

    /**
     * Make a call to {@link LibraryRepository#updateLibraryPlaylists} to update the list of the
     * user playlists which apply a level of abstraction between UI and business logic and data.
     */
    public void requestUserPlaylists() {
        LibraryRepository.updateLibraryPlaylists(userPlaylists);
    }

}
