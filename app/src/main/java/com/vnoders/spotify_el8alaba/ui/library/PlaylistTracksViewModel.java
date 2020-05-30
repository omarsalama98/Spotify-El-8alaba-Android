package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.library.Track;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import java.util.List;


/**
 * This is the ViewModel of {@link PlaylistTracksFragment}. It is responsible for preparing and
 * managing the data for {@link PlaylistTracksFragment}. It handles the communication of {@link
 * PlaylistTracksFragment} with the rest of the application (e.g. calling the business logic
 * classes).
 * <p>
 * The {@link PlaylistTracksFragment} Fragment should get the information from this view model and
 * observe its changing (Using Observer Design Pattern) by getting {@link LiveData} objects and
 * applying {@link LiveData#observe} on them.
 */
public class PlaylistTracksViewModel extends ViewModel {

    private MutableLiveData<List<Track>> tracks = new MutableLiveData<>();
    private MutableLiveData<String> playlistImageUrl;

    private String playlistId;

    public PlaylistTracksViewModel() {
        tracks = new MutableLiveData<>();
        playlistImageUrl = new MutableLiveData<>();
    }

    /**
     * @param tracks The new list of tracks.
     */
    public void setTracks(List<Track> tracks) {
        this.tracks.setValue(tracks);
    }

    /**
     * @return The list of playlist's tracks, wrapped in a {@link LiveData} object.
     */
    public LiveData<List<Track>> getTracks() {
        return tracks;
    }

    /**
     * @param playlistId The id of the playlist which contains these tracks {@link #tracks}.
     */
    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    /**
     * @return The id of the playlist which contains these tracks {@link #tracks}.
     */
    public String getPlaylistId() {
        return playlistId;
    }

    /**
     * @return The URL of the cover image of the playlist, wrapped in a {@link LiveData} object.
     */
    public LiveData<String> getPlaylistImageUrl() {
        return playlistImageUrl;
    }

    /**
     * @param playlistImageUrl The URL of the cover image of the playlist.
     */
    public void setPlaylistImageUrl(String playlistImageUrl) {
        this.playlistImageUrl.setValue(playlistImageUrl);
    }

    /**
     * Make a call to {@link LibraryRepository#updatePlaylistTracks} and {@link
     * LibraryRepository#updatePlaylistCoverImages} to update the list of the tracks in this
     * playlist which apply a level of abstraction between UI and business logic and data.
     */
    public void updatePlaylistTracks() {

        if (playlistId != null) {
            LibraryRepository.updatePlaylistCoverImages(this);
            LibraryRepository.updatePlaylistTracks(this);
        }else{
            // If the playlist has no id therefore it is the list of liked songs
            LibraryRepository.updateLikedTracksList(this);
        }
    }

}