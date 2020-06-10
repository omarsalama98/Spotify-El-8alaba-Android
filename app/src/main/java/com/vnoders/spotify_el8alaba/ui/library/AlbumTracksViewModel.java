package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.library.Track;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import java.util.List;

/**
 * This is the ViewModel of {@link AlbumTracksFragment}. It is responsible for preparing and
 * managing the data for {@link AlbumTracksFragment}. It handles the communication of {@link
 * AlbumTracksFragment} with the rest of the application (e.g. calling the business logic classes).
 * <p>
 * The {@link AlbumTracksFragment} Fragment should get the information from this view model and
 * observe its changing (Using Observer Design Pattern) by getting {@link LiveData} objects and
 * applying {@link LiveData#observe} on them.
 */
public class AlbumTracksViewModel extends ViewModel {

    private String albumId;
    private String albumName;
    private String artistName;
    private String albumImageUrl;

    private MutableLiveData<List<Track>> tracks = new MutableLiveData<>();

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumId() {
        return albumId;
    }

    /**
     * @param tracks The new list of tracks.
     */
    public void setTracks(List<Track> tracks) {
        this.tracks.setValue(tracks);
    }

    /**
     * @return The list of album's tracks, wrapped in a {@link LiveData} object.
     */
    public LiveData<List<Track>> getTracks() {
        return tracks;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumImageUrl() {
        return albumImageUrl;
    }

    public void setAlbumImageUrl(String albumImageUrl) {
        this.albumImageUrl = albumImageUrl;
    }

    /**
     * Make a call to {@link LibraryRepository#updateAlbumTracks} to update the list of the tracks
     * of this album which apply a level of abstraction between UI and business logic and data.
     */
    public void updateAlbumTracks() {
        LibraryRepository.updateAlbumTracks(this);
    }

}
