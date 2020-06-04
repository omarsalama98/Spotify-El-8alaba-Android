package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import java.util.ArrayList;
import java.util.List;

public class AddArtistsViewModel extends ViewModel {

    private MutableLiveData<List<SearchArtist>> artists;

    public AddArtistsViewModel() {
        artists = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<SearchArtist>> getArtists() {
        return artists;
    }

    public void setArtists(List<SearchArtist> artists) {
        this.artists.setValue(artists);
    }

    public void requestRelatedArtists(String artistId) {
        LibraryRepository.updateRelatedArtists(artistId, this);
    }

    public void requestRandomArtists() {
        LibraryRepository.getRandomArtists(this);
    }

    public void setRelatedArtists(String artistId, List<SearchArtist> relatedArtists) {

        List<SearchArtist> newArtistList = new ArrayList<>();

        if (artists.getValue() != null) {
            newArtistList = new ArrayList<>(artists.getValue());
        }

        int indexOfArtist = 0;
        for (int i = 0; i < newArtistList.size(); i++) {
            if (newArtistList.get(i).getId().equals(artistId)) {
                indexOfArtist = i;
                break;
            }
        }

        int numberOfRelatedArtists = 5;
        if (relatedArtists.size() > numberOfRelatedArtists) {
            relatedArtists = relatedArtists.subList(0, numberOfRelatedArtists);
        }

        for (SearchArtist artist : newArtistList) {
            int index = relatedArtists.indexOf(artist);
            if(index != -1){
                relatedArtists.remove(index);
            }
        }

        newArtistList.addAll(indexOfArtist, relatedArtists);

        artists.setValue(newArtistList);
    }


}
