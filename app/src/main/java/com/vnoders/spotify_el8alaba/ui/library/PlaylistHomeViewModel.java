package com.vnoders.spotify_el8alaba.ui.library;

import android.text.Spanned;
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
    private MutableLiveData<Spanned> tracksSummary;
    private MutableLiveData<String> imageUrl;
    private String playlistId;
    private MutableLiveData<Boolean> isFollowed;
    private MutableLiveData<Boolean> finishedLoading;
    private MutableLiveData<Boolean> isEmpty;
    private MutableLiveData<Boolean> isOwnedByMe;
    private MutableLiveData<Boolean> isCollaborative;

    public PlaylistHomeViewModel() {
        playlistName = new MutableLiveData<>();
        playlistOwnerName = new MutableLiveData<>();
        tracksSummary = new MutableLiveData<>();
        imageUrl = new MutableLiveData<>();
        isFollowed = new MutableLiveData<>();
        isEmpty = new MutableLiveData<>();
        isOwnedByMe = new MutableLiveData<>();
        isCollaborative = new MutableLiveData<>();
        finishedLoading = new MutableLiveData<>(false);
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
    public void setTracksSummary(Spanned tracksSummary) {
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
    public LiveData<Spanned> getTracksSummary() {
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


    public LiveData<Boolean> getFollowedState() {
        return isFollowed;
    }

    public void setFollowedState(boolean isFollowed) {
        this.isFollowed.setValue(isFollowed);
    }

    public void followPlaylist() {
        LibraryRepository.followPlaylist(this);
    }

    public void unfollowPlaylist() {
        LibraryRepository.unfollowPlaylist(this);
    }

    public LiveData<Boolean> getFinishedLoadingState() {
        return finishedLoading;
    }

    public void setFinishedLoading(boolean finishedLoading) {
        this.finishedLoading.setValue(finishedLoading);
    }


    public LiveData<Boolean> isEmptyPlaylist() {
        return isEmpty;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty.setValue(isEmpty);
    }

    public LiveData<Boolean> isOwnedByMe() {
        return isOwnedByMe;
    }

    public void setIsOwnedByMe(boolean isOwnedByMe) {
        this.isOwnedByMe.setValue(isOwnedByMe);
    }

    public LiveData<Boolean> isCollaborative() {
        return isCollaborative;
    }

    public void setIsCollaborative(boolean isCollaborative) {
        this.isCollaborative.setValue(isCollaborative);
    }

    /**
     * Make a call to {@link LibraryRepository#updatePlaylist} to update the info of this playlist
     * which apply a level of abstraction between UI and business logic and data.
     */
    public void updateData() {
        LibraryRepository.updatePlaylistFollowState(this);
        LibraryRepository.updatePlaylist(this);
    }

}