package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.library.Track;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import java.util.List;


/**
 * This is the ViewModel of {@link ArtistTracksFragment}. It is responsible for preparing and
 * managing the data for {@link ArtistTracksFragment}. It handles the communication of {@link
 * ArtistTracksFragment} with the rest of the application (e.g. calling the business logic
 * classes).
 * <p>
 * The {@link ArtistTracksFragment} Fragment should get the information from this view model and
 * observe its changing (Using Observer Design Pattern) by getting {@link LiveData} objects and
 * applying {@link LiveData#observe} on them.
 */
public class ArtistTracksViewModel extends ViewModel {

    private String artistId;
    private MutableLiveData<List<Track>> tracks = new MutableLiveData<>();

    public ArtistTracksViewModel() {
        tracks = new MutableLiveData<>();
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistId() {
        return artistId;
    }

    /**
     * @param tracks The new list of tracks.
     */
    public void setTracks(List<Track> tracks) {
        this.tracks.setValue(tracks);
    }

    /**
     * @return The list of artist's tracks, wrapped in a {@link LiveData} object.
     */
    public LiveData<List<Track>> getTracks() {
        return tracks;
    }

    /**
     * Make a call to {@link LibraryRepository#updateArtistTopTracks} to update the list of the
     * tracks of this artist which apply a level of abstraction between UI and business logic and
     * data.
     */
    public void updateArtistTracks() {
        LibraryRepository.updateArtistTopTracks(this);
    }

}
