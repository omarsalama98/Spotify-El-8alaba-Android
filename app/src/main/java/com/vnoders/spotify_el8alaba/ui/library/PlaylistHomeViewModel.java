package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;


/**
 * This is the ViewModel of {@link PlaylistHomeFragment}. It is responsible for preparing and
 * managing the data for {@link PlaylistHomeFragment}. It handles the communication of {@link
 * PlaylistHomeFragment} with the rest of the application (e.g. calling the business logic
 * classes).
 * <p>
 * The {@link PlaylistHomeFragment} Fragment should get the information from this view model and
 * observe its changing (Using Observer Design Pattern) by getting {@link LiveData} objects and
 * applying {@link LiveData#observe} on them.
 */
public class PlaylistHomeViewModel extends ViewModel {

    private MutableLiveData<String> playlistName;
    private MutableLiveData<String> playlistOwnerName;
    private MutableLiveData<String> tracksSummary;
    private MutableLiveData<String> imageUrl;
    private String playlistId;

    public PlaylistHomeViewModel() {
        playlistName = new MutableLiveData<>();
        playlistOwnerName = new MutableLiveData<>();
        tracksSummary = new MutableLiveData<>();
        imageUrl = new MutableLiveData<>();
    }

    /**
     * @param playlistName The name of the playlist
     */
    public void setPlaylistName(String playlistName) {
        this.playlistName.setValue(playlistName);
    }

    /**
     * @param playlistOwnerName The name of the owner of the playlist (who created this playlist)
     */
    public void setPlaylistOwnerName(String playlistOwnerName) {
        this.playlistOwnerName.setValue(playlistOwnerName);
    }

    /**
     * @param tracksSummary The summary of the tracks in this playlist (e.g. Song_Name1 Artist_Name1
     *                      • Song_Name2 Artist_Name2 • ...)
     */
    public void setTracksSummary(String tracksSummary) {
        this.tracksSummary.setValue(tracksSummary);
    }

    /**
     * @param imageUrl The URL of the playlist's cover image
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl.setValue(imageUrl);
    }

    /**
     * @return The name of the playlist, wrapped in a {@link LiveData} object.
     */
    public LiveData<String> getPlaylistName() {
        return playlistName;
    }

    /**
     * @return The summary of the tracks in this playlist (e.g. Song_Name Artist_Name •  ... ),
     * wrapped in a {@link LiveData} object.
     */
    public LiveData<String> getTracksSummary() {
        return tracksSummary;
    }

    /**
     * @return The URL of the playlist's cover image, wrapped in a {@link LiveData} object.
     */
    public LiveData<String> getImageUrl() {
        return imageUrl;
    }

    /**
     * @return The name of the owner of the playlist (who created this playlist), wrapped in a
     * {@link LiveData} object.
     */
    public LiveData<String> getPlaylistOwnerName() {
        return playlistOwnerName;
    }

    /**
     * @return The URL of the playlist's cover image
     */
    public String getPlaylistId() {
        return playlistId;
    }

    /**
     * @param playlistId The URL of the playlist's cover image
     */
    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    /**
     * Make a call to {@link LibraryRepository#updatePlaylist} to update the info of this playlist
     * which apply a level of abstraction between UI and business logic and data.
     */
    public void updateData() {
        LibraryRepository.updatePlaylist(this);
    }

}