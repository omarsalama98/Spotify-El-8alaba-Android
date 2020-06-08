package com.vnoders.spotify_el8alaba.ui.library;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import com.vnoders.spotify_el8alaba.ui.library.AddArtistAdapter.RelatedArtistsCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class AddArtistsViewModel extends ViewModel {

    private MutableLiveData<List<SearchArtist>> artists;
    private Map<String, RelatedArtistsCallback> relatedArtistsCallbacks;

    public AddArtistsViewModel() {
        artists = new MutableLiveData<>(new ArrayList<>());
        relatedArtistsCallbacks = new HashMap<>();
    }

    public LiveData<List<SearchArtist>> getArtists() {
        return artists;
    }

    public void setArtists(List<SearchArtist> artists) {
        this.artists.setValue(artists);
    }

    public void requestRelatedArtists(String artistId, @NotNull RelatedArtistsCallback callback) {
        if (!relatedArtistsCallbacks.containsKey(artistId)) {
            relatedArtistsCallbacks.put(artistId, callback);
            LibraryRepository.updateRelatedArtists(artistId, this);
        }
    }

    public void requestRandomArtists() {
        LibraryRepository.getRandomArtists(this);
    }

    public void setRelatedArtists(String artistId, List<SearchArtist> relatedArtists) {
        RelatedArtistsCallback callback = relatedArtistsCallbacks.get(artistId);
        callback.onResponse(artistId, relatedArtists);
        relatedArtistsCallbacks.remove(artistId);
    }

}
