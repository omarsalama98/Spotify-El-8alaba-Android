package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import java.util.List;

public class ArtistViewModel extends ViewModel {

    private String artistId;
    private MutableLiveData<String> artistName;
    private MutableLiveData<String> tracksSummary;
    private MutableLiveData<String> imageUrl;
    private MutableLiveData<Boolean> isFollowed;
    private MutableLiveData<Boolean> finishedLoading;
    private MutableLiveData<List<Artist>> relatedArtists;

    int numberOfFinishedRequests = 0 ;
    int numberOfRequests;

    public ArtistViewModel() {
        artistName = new MutableLiveData<>();
        tracksSummary = new MutableLiveData<>();
        imageUrl = new MutableLiveData<>();
        isFollowed = new MutableLiveData<>();
        finishedLoading = new MutableLiveData<>(false);
        relatedArtists = new MutableLiveData<>();
    }

    /**
     * @param artistName The name of the artist
     */
    public void setArtistName(String artistName) {
        this.artistName.setValue(artistName);
    }

    /**
     * @param tracksSummary The summary of the tracks in this artist (e.g. Song_Name • Song_Name2 )
     */
    public void setTracksSummary(String tracksSummary) {
        this.tracksSummary.setValue(tracksSummary);
    }

    /**
     * @param imageUrl The URL of the artist's image
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl.setValue(imageUrl);
    }

    /**
     * @return The name of the artist, wrapped in a {@link LiveData} object.
     */
    public LiveData<String> getArtistName() {
        return artistName;
    }

    /**
     * @return The summary of the tracks in this artist (e.g. Song_Name • Song_Name2 ) ,
     * wrapped in a {@link LiveData} object.
     */
    public LiveData<String> getTracksSummary() {
        return tracksSummary;
    }

    /**
     * @return The URL of the artist's image, wrapped in a {@link LiveData} object.
     */
    public LiveData<String> getImageUrl() {
        return imageUrl;
    }

    /**
     * @return The id of the artist
     */
    public String getArtistId() {
        return artistId;
    }

    /**
     * @param artistId The id of the artist
     */
    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }


    public LiveData<Boolean> getFollowedState() {
        return isFollowed;
    }

    public void setFollowedState(boolean isFollowed) {
        this.isFollowed.setValue(isFollowed);
    }

    public void followArtist() {
        LibraryRepository.followArtist(this);
    }

    public void unfollowArtist() {
        LibraryRepository.unfollowArtist(this);
    }

    public LiveData<List<Artist>> getRelatedArtists() {
        return relatedArtists;
    }

    public void setRelatedArtists(List<Artist> relatedArtists) {
        this.relatedArtists.setValue(relatedArtists);
    }

    public LiveData<Boolean> getFinishedLoadingState() {
        return finishedLoading;
    }

    public void finishedRequest(){
        numberOfFinishedRequests++;
        if(numberOfFinishedRequests == numberOfRequests){
            numberOfFinishedRequests = 0;
            finishedLoading.setValue(true);
        }
    }

    /**
     * Make a call to {@link LibraryRepository#updateArtist} to update the info of this artist which
     * apply a level of abstraction between UI and business logic and data.
     */
    public void updateData() {
        LibraryRepository.updateArtistFollowState(this);
        LibraryRepository.updateRelatedArtists(this);
        LibraryRepository.updateArtistTracksSummary(this);
        LibraryRepository.updateArtist(this);
        numberOfRequests = 4; // update this as we add requests
    }

}
