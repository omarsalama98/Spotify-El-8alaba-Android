package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.library.LibraryAlbumItem;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import java.util.List;

/**
 * This is the ViewModel of {@link LibraryAlbumFragment}. It is responsible for preparing and
 * managing the data for {@link LibraryAlbumFragment}. It handles the communication of {@link
 * LibraryAlbumFragment} with the rest of the application (e.g. calling the business logic
 * classes).
 * <p>
 * The {@link LibraryAlbumFragment} Fragment should get the information from this view model and
 * observe its changing (Using Observer Design Pattern) by getting {@link LiveData} objects and
 * applying {@link LiveData#observe} on them.
 */
public class LibraryAlbumViewModel extends ViewModel {

    private MutableLiveData<List<LibraryAlbumItem>> userAlbums;

    public LibraryAlbumViewModel() {
        userAlbums = new MutableLiveData<>();
    }

    /**
     * @return The list of user albums, wrapped in a {@link LiveData} object.
     */
    public LiveData<List<LibraryAlbumItem>> getUserAlbums() {
        return userAlbums;
    }

    /**
     * Make a call to {@link LibraryRepository#updateLibraryAlbums} to update the list of the user
     * albums which apply a level of abstraction between UI and business logic and data.
     */
    public void requestUserAlbums() {
        LibraryRepository.updateLibraryAlbums(userAlbums);
    }

}
